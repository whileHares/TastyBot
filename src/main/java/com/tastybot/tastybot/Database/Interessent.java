package com.tastybot.tastybot.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(force = true)
public class Interessent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer interessentId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name="angebot_id")
    private Angebot angebot;
}
