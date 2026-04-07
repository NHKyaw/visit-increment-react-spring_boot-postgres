package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitorControllerTest {

    @Mock
    private VisitorRepo repo;

    @InjectMocks
    private VisitorController controller;

    @Test
    public void testAddVisit_returnsConfirmationMessage() {
        when(repo.save(any(Visitor.class))).thenReturn(new Visitor("2024-01-01"));

        String result = controller.addVisit();

        assertEquals("Visit Recorded!", result);
        verify(repo, times(1)).save(any(Visitor.class));
    }

    @Test
    public void testGetCount_returnsCountFromRepo() {
        when(repo.count()).thenReturn(5L);

        Map<String, Long> result = controller.getCount();

        assertEquals(5L, result.get("count"));
    }

    @Test
    public void testGetCount_returnsZeroWhenEmpty() {
        when(repo.count()).thenReturn(0L);

        Map<String, Long> result = controller.getCount();

        assertEquals(0L, result.get("count"));
    }
}