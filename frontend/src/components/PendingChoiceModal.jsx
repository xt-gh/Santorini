import React from 'react';

const PendingChoiceModal = ({ show, title, message, onResolve }) => {
  if (!show) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content glass-panel">
        <h2 className="modal-title">{title}</h2>
        <div className="modal-body">
          {message.split('\n').map((line, i) => (
            <p key={i} style={{marginBottom: '0.5rem'}}>{line}</p>
          ))}
        </div>
        <div className="modal-actions">
          <button className="btn btn-primary" onClick={() => onResolve(true)}>
            Yes
          </button>
          <button className="btn btn-secondary" onClick={() => onResolve(false)}>
            No
          </button>
        </div>
      </div>
    </div>
  );
};

export default PendingChoiceModal;
