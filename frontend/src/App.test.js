import React from 'react';
import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
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

    await act(async () => {
      render(<App />);
    });

    // Loading shows before fetch resolves on mount
    // Title should always be present
    expect(screen.getByText('CI/CD Test App:UAT:v1')).toBeInTheDocument();
  });

  test('renders the app title', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });

    await act(async () => {
      render(<App />);
    });

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

    await act(async () => {
      render(<App />);
    });

    expect(screen.getByText('42')).toBeInTheDocument();
  });

  test('displays count from raw number API response', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => 7,
    });

    await act(async () => {
      render(<App />);
    });

    expect(screen.getByText('7')).toBeInTheDocument();
  });

  test('hides Loading... after fetch completes', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 3 }),
    });

    await act(async () => {
      render(<App />);
    });

    expect(screen.queryByText('Loading...')).not.toBeInTheDocument();
  });
});

// ── Test Group 3: API Error Handling ─────────────────────────────────────────
describe('Error Handling', () => {
  test('stops loading if fetch throws a network error', async () => {
    fetch.mockRejectedValueOnce(new Error('Network Error'));

    await act(async () => {
      render(<App />);
    });

    expect(screen.queryByText('Loading...')).not.toBeInTheDocument();
  });

  test('stops loading if response is not ok', async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await act(async () => {
      render(<App />);
    });

    expect(screen.queryByText('Loading...')).not.toBeInTheDocument();
  });
});

// ── Test Group 4: Log New Visit Button ───────────────────────────────────────
describe('"Log New Visit" Button', () => {
  test('renders the button', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });

    await act(async () => {
      render(<App />);
    });

    expect(screen.getByText('Log New Visit')).toBeInTheDocument();
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

    await act(async () => {
      render(<App />);
    });

    expect(screen.getByText('0')).toBeInTheDocument();

    // Click button and advance the 200ms timer together inside act()
    await act(async () => {
      fireEvent.click(screen.getByText('Log New Visit'));
      jest.advanceTimersByTime(200);
    });

    await waitFor(() => expect(screen.getByText('1')).toBeInTheDocument());

    expect(fetch).toHaveBeenCalledWith(
      expect.stringContaining('/api/visit'),
      { method: 'POST' }
    );
  });
});