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
    public void testAddVisitReturnsConfirmationMessage() { // ✅ removed underscores
        when(repo.save(any(Visitor.class)))
                .thenReturn(new Visitor("2024-01-01"));

        final String result = controller.addVisit();

        assertEquals("Visit Recorded!", result);
        verify(repo, times(1)).save(any(Visitor.class));
    }

    @Test
    public void testGetCountReturnsCountFromRepo() { // ✅ already fine, but consistent
        when(repo.count()).thenReturn(5L);

        final Map<String, Long> result = controller.getCount();

        assertEquals(5L, result.get("count"));
    }

    @Test
    public void testGetCountReturnsZeroWhenEmpty() { // ✅ already fine, but consistent
        when(repo.count()).thenReturn(0L);

        final Map<String, Long> result = controller.getCount();

        assertEquals(0L, result.get("count"));
    }
}
