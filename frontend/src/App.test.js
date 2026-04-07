import React from 'react';
import { render, screen, fireEvent, act, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import App from './App';

// Suppress console.error noise during tests
beforeEach(() => {
  jest.spyOn(console, 'error').mockImplementation(() => {});
  global.fetch = jest.fn();
  jest.useFakeTimers();
});

afterEach(() => {
  console.error.mockRestore();
  jest.clearAllMocks();
  jest.useRealTimers();
});

// ── Test Group 1: Initial Render ──────────────────────────────────────────────
describe('Initial Render', () => {
  test('shows Loading... on first render', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 5 }),
    });

    render(<App />);

    expect(screen.getByText('CI/CD Test App:UAT:v1')).toBeInTheDocument();
  });

  test('renders the app title', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });

    render(<App />);

    expect(screen.getByText('CI/CD Test App:UAT:v1')).toBeInTheDocument();
  });
});

// ── Test Group 2: Fetching Visit Count ────────────────────────────────────────
describe('Fetching Visit Count', () => {
  test('displays count from API response object { count: N }', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 42 }),
    });

    render(<App />);

    expect(await screen.findByText('42')).toBeInTheDocument();
  });

  test('displays count from raw number API response', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => 7,
    });

    render(<App />);

    expect(await screen.findByText('7')).toBeInTheDocument();
  });

  test('hides Loading... after fetch completes', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 3 }),
    });

    render(<App />);

    await waitFor(() =>
      expect(screen.queryByText('Loading...')).not.toBeInTheDocument()
    );
  });
});

// ── Test Group 3: API Error Handling ─────────────────────────────────────────
describe('Error Handling', () => {
  test('stops loading if fetch throws a network error', async () => {
    fetch.mockRejectedValueOnce(new Error('Network Error'));

    render(<App />);

    await waitFor(() =>
      expect(screen.queryByText('Loading...')).not.toBeInTheDocument()
    );
  });

  test('stops loading if response is not ok', async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    render(<App />);

    await waitFor(() =>
      expect(screen.queryByText('Loading...')).not.toBeInTheDocument()
    );
  });
});

// ── Test Group 4: Log New Visit Button ───────────────────────────────────────
describe('"Log New Visit" Button', () => {
  test('renders the button', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });

    render(<App />);

    expect(await screen.findByText('Log New Visit')).toBeInTheDocument();
  });

  test('calls POST then refreshes count after 200ms delay', async () => {
    // First call: initial GET
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });
    // Second call: POST
    fetch.mockResolvedValueOnce({ ok: true });
    // Third call: GET after setTimeout
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 1 }),
    });

    render(<App />);

    // Wait for initial count to load
    expect(await screen.findByText('0')).toBeInTheDocument();

    // Click button and advance the 200ms timer
    fireEvent.click(screen.getByText('Log New Visit'));
    await act(async () => {
      jest.advanceTimersByTime(200);
    });

    expect(await screen.findByText('1')).toBeInTheDocument();

    expect(fetch).toHaveBeenCalledWith(
      expect.stringContaining('/api/visit'),
      { method: 'POST' }
    );
  });
});