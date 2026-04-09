package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "visitors")
public final class Visitor {
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** The visit time. */
    private String visitTime;

    /**
     * Default constructor.
     */
    public Visitor() {
    }

    /**
     * Constructor with visit time.
     *
     * @param time the visit time
     */
    public Visitor(final String time) {
        this.visitTime = time;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
}
