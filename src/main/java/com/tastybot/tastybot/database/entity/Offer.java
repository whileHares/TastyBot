package com.tastybot.tastybot.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing an offer in the database.
 */
@Entity
@Data
@NoArgsConstructor(force = true)
public class Offer {

    /**
     * The unique identifier for the offer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer offerId;

    /**
     * The title of the offer.
     */
    @NonNull
    private String offerTitle;

    /**
     * The preference associated with the offer.
     */
    private String preference;

    /**
     * The date/time the offer is available from.
     */
    private String availableFrom;

    /**
     * The date/time the offer is available until.
     */
    private String availableUntil;

    /**
     * Additional notes for the offer.
     */
    private String notes;

    /**
     * The pickup location for the offer.
     */
    @NonNull
    private String pickupLocation;

    /**
     * The ID of the user associated with the offer.
     */
    @NonNull
    private Long userId;

    /**
     * The message ID associated with the offer.
     */
    private Long offerMessageId;

    /**
     * The message ID associated with the offer delete action.
     */
    private Long offerDeleteMessageId;

    /**
     * The URL of the photo for the offer.
     */
    private String photoUrl;

    /**
     * List of applicants associated with the offer.
     */
    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER)
    private List<Applicant> applicants = new ArrayList<>();
}
