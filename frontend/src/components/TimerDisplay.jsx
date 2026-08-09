import React, { useState, useEffect } from 'react';

const TimerDisplay = ({ startTimeMs, timeLimitSeconds, onTimeout }) => {
  const [timeLeft, setTimeLeft] = useState(timeLimitSeconds);

  useEffect(() => {
    if (!startTimeMs) return;

    const intervalId = setInterval(() => {
      const elapsed = Math.floor((Date.now() - startTimeMs) / 1000);
      const remaining = Math.max(0, timeLimitSeconds - elapsed);
      setTimeLeft(remaining);

      if (remaining === 0 && onTimeout) {
        clearInterval(intervalId);
        onTimeout();
      }
    }, 1000);

    // Initial calculation to prevent 1-second delay
    const elapsed = Math.floor((Date.now() - startTimeMs) / 1000);
    setTimeLeft(Math.min(timeLimitSeconds, Math.max(0, timeLimitSeconds - elapsed)));

    return () => clearInterval(intervalId);
  }, [startTimeMs, timeLimitSeconds, onTimeout]);

  const minutes = Math.floor(timeLeft / 60);
  const seconds = timeLeft % 60;
  
  return (
    <div style={{ textAlign: 'center', margin: '0.5rem 0', fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--accent-red)' }}>
      Time Left: {minutes}:{seconds.toString().padStart(2, '0')}
    </div>
  );
};

export default TimerDisplay;
