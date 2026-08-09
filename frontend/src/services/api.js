import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getGameState = async () => {
  const response = await apiClient.get('/api/game');
  return response.data;
};

export const createGame = async () => {
  const response = await apiClient.post('/api/game');
  return response.data;
};

export const clickTile = async (row, col) => {
  const response = await apiClient.post('/api/game/click', { row, col });
  return response.data;
};

export const resolveChoice = async (accepted) => {
  const response = await apiClient.post('/api/game/choice', { accepted });
  return response.data;
};

export const restartGame = async () => {
  const response = await apiClient.post('/api/game/restart');
  return response.data;
};
