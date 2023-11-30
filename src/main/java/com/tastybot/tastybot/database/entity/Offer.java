package com.tastybot.tastybot.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(force = true)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer offerId;

    @NonNull
    private String offerTitle;

    private String preference;

    private String availableFrom;

    private String availableUntil;

    private String notes;
    @NonNull
    private String pickupLocation;
    @NonNull
    private Long userId;
    private Long offerMessageId;
    private Long offerDeleteMessageId;
    private String photoUrl;

    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER)
    private List<Applicant> applicants = new ArrayList<>();
}
