/* eslint-env es2020 */
import React from 'react';
import { render, screen, fireEvent, act, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import App from './App';

beforeEach(() => {
  jest.spyOn(console, 'error').mockImplementation(() => {});
  globalThis.fetch = jest.fn();
  jest.useFakeTimers();
});

afterEach(() => {
  console.error.mockRestore();
  jest.clearAllMocks();
  jest.useRealTimers();
});

describe('Initial Render', () => {
  test('shows Loading... on first render', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 5 }),
    });
    render(<App />);
    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  test('renders the app title', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });
    render(<App />);
    expect(screen.getByText('CI/CD Test App:UAT:v1')).toBeInTheDocument();
  });

  test('renders Log New Visit button', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });
    render(<App />);
    expect(screen.getByText('Log New Visit')).toBeInTheDocument();
  });
});

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

  test('hides Loading after fetch completes', async () => {
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

describe('Log New Visit Button', () => {
  test('calls POST then refreshes count after 200ms delay', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });
    fetch.mockResolvedValueOnce({ ok: true });
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 1 }),
    });

    render(<App />);
    expect(await screen.findByText('0')).toBeInTheDocument();

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

  test('handles POST failure gracefully', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });
    fetch.mockResolvedValueOnce({ ok: false });

    render(<App />);
    expect(await screen.findByText('0')).toBeInTheDocument();

    fireEvent.click(screen.getByText('Log New Visit'));
    await act(async () => {
      jest.advanceTimersByTime(200);
    });

    expect(screen.getByText('Log New Visit')).toBeInTheDocument();
  });

  test('handles POST network error gracefully', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ count: 0 }),
    });
    fetch.mockRejectedValueOnce(new Error('Network Error'));

    render(<App />);
    expect(await screen.findByText('0')).toBeInTheDocument();

    fireEvent.click(screen.getByText('Log New Visit'));
    await act(async () => {
      jest.advanceTimersByTime(200);
    });

    expect(screen.getByText('Log New Visit')).toBeInTheDocument();
  });
});
