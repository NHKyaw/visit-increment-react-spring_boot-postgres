package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/** Unit tests for the Visitor class. */
class VisitorTest {

    /** Reusable timestamp constant for tests. */
    private static final String VISIT_TIME = "2024-01-01T00:00:00";

    /** Tests that the default constructor creates a visitor with null id. */
    @Test
    void testDefaultConstructor() {
        Visitor visitor = new Visitor();
        assertNull(visitor.getId());
    }

    /** Tests that the constructor correctly sets the visit time. */
    @Test
    void testConstructorWithTime() {
        Visitor visitor = new Visitor(VISIT_TIME);
        assertEquals(VISIT_TIME, visitor.getVisitTime());
    }

    /** Tests that a visitor created with a time has a non-null visit time. */
    @Test
    void testGetId() {
        Visitor visitor = new Visitor(VISIT_TIME);
        assertNotNull(visitor.getVisitTime());
    }

    /** Tests that getVisitTime returns the correct time. */
    @Test
    void testGetVisitTime() {
        Visitor visitor = new Visitor("2024-06-15T10:30:00");
        assertEquals("2024-06-15T10:30:00", visitor.getVisitTime());
    }
}
