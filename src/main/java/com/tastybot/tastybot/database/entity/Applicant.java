package com.tastybot.tastybot.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an applicant in the database.
 */
@Entity
@Data
@NoArgsConstructor(force = true)
public class Applicant {

    /**
     * The unique identifier for the applicant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicantId;

    /**
     * The ID of the user associated with the applicant.
     */
    private Long userId;

    /**
     * The offer associated with the applicant.
     */
    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;
}

