package com.tastybot.tastybot.Database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Angebot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private String angebotstitel;
    @NonNull
    private Boolean vegan;
    @NonNull
    private String verfuegbarAb;
    @NonNull
    private String verfuegbarBis;
    @NonNull
    private String anmerkungen;
    @NonNull
    private String abholungsort;
    @NonNull
    private Long userId;
    private Long angebotsNachrichtenId;
}
