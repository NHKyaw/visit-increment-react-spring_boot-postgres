package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** Unit tests for VisitorController. */
@ExtendWith(MockitoExtension.class)
class VisitorControllerTest {

    /** Mocked visitor repository. */
    @Mock
    private VisitorRepo repo;

    /** VisitorController instance with mocked dependencies. */
    @InjectMocks
    private VisitorController controller;

    /** Tests that addVisit saves a visitor and returns confirmation. */
    @Test
    void testAddVisit() {
        when(repo.save(any(Visitor.class))).thenReturn(new Visitor("2024-01-01"));
        String result = controller.addVisit();
        assertEquals("Visit Recorded!", result);
    }

    /** Tests that getCount returns the visit count. */
    @Test
    void testGetCount() {
        when(repo.count()).thenReturn(42L);
        Map<String, Long> result = controller.getCount();
        assertEquals(42L, result.get("count"));
    }

    /** Tests that getCount returns zero when no visits exist. */
    @Test
    void testGetCountZero() {
        when(repo.count()).thenReturn(0L);
        Map<String, Long> result = controller.getCount();
        assertEquals(0L, result.get("count"));
    }
}
