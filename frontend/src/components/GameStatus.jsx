import React from 'react';

const GameStatus = ({ message, isGameOver, winner }) => {
  let statusClass = "status-message";
  
  if (isGameOver) {
    statusClass += winner ? " status-win" : " status-error";
  } else if (message && (message.toLowerCase().includes('invalid') || message.toLowerCase().includes('cannot'))) {
    statusClass += " status-error";
  }

  return (
    <div className="game-header">
      <h1 className="game-title">Santorini</h1>
      {message && (
        <div className={statusClass}>
          {message}
        </div>
      )}
    </div>
  );
};

export default GameStatus;
