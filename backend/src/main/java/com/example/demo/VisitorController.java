package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public final class VisitorController {

    /** The visitor repository. */
    private final VisitorRepo repo;

    /**
     * Constructor.
     *
     * @param visitorRepo the visitor repo
     */
    public VisitorController(final VisitorRepo visitorRepo) {
        this.repo = visitorRepo;
    }

    /**
     * Adds a visit.
     *
     * @return confirmation message
     */
    @PostMapping("/api/visit")
    public String addVisit() {
        repo.save(new Visitor(LocalDateTime.now().toString()));
        return "Visit Recorded!";
    }

    /**
     * Gets the count of visits.
     *
     * @return map with count
     */
    @GetMapping("/api/visit/count")
    public Map<String, Long> getCount() {
        return Map.of("count", repo.count());
    }
}
