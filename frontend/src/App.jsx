import React, { useState, useEffect, useCallback } from 'react';
import * as api from './services/api';
import GameBoard from './components/GameBoard';
import PlayerPanel from './components/PlayerPanel';
import GameStatus from './components/GameStatus';
import PendingChoiceModal from './components/PendingChoiceModal';
import TimerDisplay from './components/TimerDisplay';
import './index.css';

function App() {
  const [gameState, setGameState] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchGameState = useCallback(async () => {
    try {
      setLoading(true);
      const state = await api.getGameState();
      setGameState(state);
      setError(null);
    } catch (err) {
      if (err.response && err.response.status === 404) {
        // No active game
        setGameState(null);
      } else {
        console.error("Failed to fetch game state:", err);
        setError("Could not connect to the server.");
      }
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchGameState();
  }, [fetchGameState]);

  const handleCreateGame = async () => {
    try {
      setLoading(true);
      const state = await api.createGame();
      setGameState(state);
      setError(null);
    } catch (err) {
      console.error("Failed to create game:", err);
      setError("Failed to create game.");
    } finally {
      setLoading(false);
    }
  };

  const handleTileClick = async (row, col) => {
    if (!gameState || gameState.gameOver || gameState.pendingChoice) return;
    
    try {
      const state = await api.clickTile(row, col);
      setGameState(state);
    } catch (err) {
      console.error("Failed to click tile:", err);
    }
  };

  const handleResolveChoice = async (accepted) => {
    try {
      const state = await api.resolveChoice(accepted);
      setGameState(state);
    } catch (err) {
      console.error("Failed to resolve choice:", err);
    }
  };

  const handleTimeout = () => {
    // If the timer expires locally, we can trigger a click on an invalid tile
    // just to force the server to evaluate the timeout condition and update state.
    // Or we could have a specific timeout endpoint, but any action evaluates timer.
    handleTileClick(-1, -1);
  };

  if (loading && !gameState) {
    return <div className="app-container"><div style={{margin: 'auto', textAlign: 'center'}}>Loading...</div></div>;
  }

  if (error) {
    return (
      <div className="app-container">
        <div style={{margin: 'auto', textAlign: 'center'}}>
          <h2 style={{color: 'var(--accent-red)'}}>{error}</h2>
          <button className="btn btn-primary" onClick={fetchGameState} style={{marginTop: '1rem'}}>Retry</button>
        </div>
      </div>
    );
  }

  if (!gameState) {
    return (
      <div className="app-container">
        <div style={{gridColumn: '1 / -1', textAlign: 'center', marginTop: '4rem'}}>
          <h1 className="game-title" style={{fontSize: '4rem'}}>Santorini</h1>
          <p style={{marginBottom: '2rem', fontSize: '1.2rem', color: 'var(--text-muted)'}}>
            Build like a mortal, win like a god.
          </p>
          <button className="btn btn-primary" style={{fontSize: '1.25rem', padding: '1rem 2rem'}} onClick={handleCreateGame}>
            Start New Game
          </button>
        </div>
      </div>
    );
  }

  const {
    players,
    currentPlayerIndex,
    message,
    gameOver,
    winner,
    tiles,
    boardRows,
    boardColumns,
    selectedWorkerRow,
    selectedWorkerCol,
    pendingChoice,
    choiceType,
    choiceMessage,
    turnStartTimeMs,
    turnTimeLimitSeconds
  } = gameState;

  return (
    <div className="app-container">
      <GameStatus message={message} isGameOver={gameOver} winner={winner} />

      {players && players[0] && (
        <div>
          <PlayerPanel player={players[0]} isActive={currentPlayerIndex === 0 && !gameOver} />
          {currentPlayerIndex === 0 && !gameOver && (
            <TimerDisplay 
              startTimeMs={turnStartTimeMs} 
              timeLimitSeconds={turnTimeLimitSeconds} 
              onTimeout={handleTimeout} 
            />
          )}
        </div>
      )}

      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <GameBoard 
          tiles={tiles}
          boardRows={boardRows}
          boardColumns={boardColumns}
          selectedWorkerRow={selectedWorkerRow}
          selectedWorkerCol={selectedWorkerCol}
          onTileClick={handleTileClick}
        />
        
        {gameOver && (
          <div style={{marginTop: '2rem'}}>
            <button className="btn btn-primary" onClick={handleCreateGame}>Play Again</button>
          </div>
        )}
      </div>

      {players && players[1] && (
        <div>
          <PlayerPanel player={players[1]} isActive={currentPlayerIndex === 1 && !gameOver} />
          {currentPlayerIndex === 1 && !gameOver && (
             <TimerDisplay 
              startTimeMs={turnStartTimeMs} 
              timeLimitSeconds={turnTimeLimitSeconds} 
              onTimeout={handleTimeout} 
            />
          )}
        </div>
      )}

      <PendingChoiceModal 
        show={pendingChoice}
        title={choiceType === 'USE_SKIP_CARD' ? 'Skip Card' : 'God Power'}
        message={choiceMessage || 'Make your choice.'}
        onResolve={handleResolveChoice}
      />
    </div>
  );
}

export default App;
