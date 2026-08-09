import React from 'react';

/**
 * Determines the correct background image for a tile based on its tower level,
 * dome presence, and whether it has a worker.
 */
const getTileImage = (tile) => {
  if (tile.hasWorker && tile.workerOwnerIndex !== -1) {
    const playerNum = tile.workerOwnerIndex + 1; // 1 or 2
    if (tile.towerLevel === 1) return `url('/assets/Lvl_1_player_${playerNum}.png')`;
    if (tile.towerLevel === 2) return `url('/assets/Lvl_2_player_${playerNum}.png')`;
    if (tile.towerLevel === 3) return `url('/assets/Lvl_3_player_${playerNum}.png')`;
    return `url('/assets/player_${playerNum}.png')`;
  }

  if (tile.hasDome) {
    if (tile.towerLevel === 1) return `url('/assets/Lvl_1_dome.png')`;
    if (tile.towerLevel === 2) return `url('/assets/Lvl_2_dome.png')`;
    if (tile.towerLevel === 3) return `url('/assets/Lvl_complete.png')`;
    return `url('/assets/Lvl_complete.png')`; // Fallback for dome on level 0 (shouldn't happen but just in case)
  }

  if (tile.towerLevel === 1) return `url('/assets/Lvl_1.png')`;
  if (tile.towerLevel === 2) return `url('/assets/Lvl_2.png')`;
  if (tile.towerLevel === 3) return `url('/assets/Lvl_3.png')`;

  return `url('/assets/Empty_tile.png')`;
};

const BoardCell = ({ tile, isSelected, onClick }) => {
  const bgImage = getTileImage(tile);
  
  return (
    <div 
      className={`board-cell ${isSelected ? 'selected' : ''}`}
      style={{ backgroundImage: bgImage }}
      onClick={() => onClick(tile.row, tile.col)}
      title={`Row: ${tile.row}, Col: ${tile.col} | Level: ${tile.towerLevel} ${tile.hasDome ? '(Dome)' : ''}`}
    />
  );
};

export default BoardCell;
