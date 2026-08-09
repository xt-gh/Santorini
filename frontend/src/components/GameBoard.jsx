import React from 'react';
import BoardCell from './BoardCell';

const GameBoard = ({ tiles, boardRows, boardColumns, selectedWorkerRow, selectedWorkerCol, onTileClick }) => {
  if (!tiles || tiles.length === 0) return null;

  return (
    <div className="board-container">
      <div 
        className="game-board glass-panel" 
        style={{ 
          gridTemplateColumns: `repeat(${boardColumns}, 1fr)`,
          gridTemplateRows: `repeat(${boardRows}, 1fr)`
        }}
      >
        {tiles.map((tile, index) => {
          const isSelected = selectedWorkerRow === tile.row && selectedWorkerCol === tile.col;
          return (
            <BoardCell 
              key={`${tile.row}-${tile.col}`}
              tile={tile}
              isSelected={isSelected}
              onClick={onTileClick}
            />
          );
        })}
      </div>
    </div>
  );
};

export default GameBoard;
