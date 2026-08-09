import React from 'react';

const PlayerPanel = ({ player, isActive }) => {
  if (!player) return null;
  
  // Format card name to match image filenames: e.g. "Artemis" -> "artemis_card.png"
  const getGodCardImage = (name) => {
    if (!name) return null;
    const lower = name.toLowerCase();
    if (lower === 'zeus') return '/assets/zues_card.png'; // Note: typo in original filename
    return `/assets/${lower}_card.png`;
  };

  const godCardImg = getGodCardImage(player.godCardName);
  const playerIcon = `/assets/player_${player.playerIndex + 1}.png`;

  return (
    <div className={`player-panel glass-panel ${isActive ? 'active' : ''}`}>
      <div className="player-header">
        <div className="player-icon" style={{ backgroundImage: `url(${playerIcon})` }}></div>
        <div className="player-name">{player.name} {isActive && <span style={{fontSize: '0.8rem', color: 'var(--accent-blue)'}}>(Current Turn)</span>}</div>
      </div>

      <div className="card-container">
        {player.godCardName && (
          <div className="god-card">
            {godCardImg && <img src={godCardImg} alt={player.godCardName} className="god-card-img" />}
            <div className="card-title">{player.godCardName}</div>
            <div className="card-desc">{player.godCardDescription}</div>
          </div>
        )}

        {player.hasSkipCard && (
          <div className="function-card" style={{ opacity: player.skipCardUsed ? 0.5 : 1 }}>
            <div className="card-title">Skip Card {player.skipCardUsed && '(Used)'}</div>
            <div className="card-desc">You can skip your next player's turn.</div>
          </div>
        )}
      </div>
    </div>
  );
};

export default PlayerPanel;
