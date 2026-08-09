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
    setTimeLeft(Math.max(0, timeLimitSeconds - elapsed));

    return () => clearInterval(intervalId);
  }, [startTimeMs, timeLimitSeconds, onTimeout]);

  const minutes = Math.floor(timeLeft / 60);
  const seconds = timeLeft % 60;
  
  const isWarning = timeLeft <= 60;
  const isDanger = timeLeft <= 10;

  let color = 'var(--text-main)';
  if (isDanger) color = 'var(--accent-red)';
  else if (isWarning) color = 'var(--accent-gold)';

  return (
    <div style={{ textAlign: 'center', marginTop: '1rem', fontSize: '1.25rem', fontWeight: '600', color }}>
      Time Left: {minutes}:{seconds.toString().padStart(2, '0')}
    </div>
  );
};

export default TimerDisplay;
