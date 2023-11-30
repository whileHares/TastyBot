package com.tastybot.tastybot.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(force = true)
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicantId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;
}
