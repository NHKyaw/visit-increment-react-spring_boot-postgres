package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class VisitorTest {

    @Test
    void testDefaultConstructor() {
        Visitor visitor = new Visitor();
        assertNull(visitor.getId());
    }

    @Test
    void testConstructorWithTime() {
        Visitor visitor = new Visitor("2024-01-01T00:00:00");
        assertEquals("2024-01-01T00:00:00", visitor.getVisitTime());
    }

    @Test
    void testGetId() {
        Visitor visitor = new Visitor();
        assertNull(visitor.getId());
    }

    @Test
    void testGetVisitTime() {
        Visitor visitor = new Visitor("2024-06-15T10:30:00");
        assertEquals("2024-06-15T10:30:00", visitor.getVisitTime());
    }
}
