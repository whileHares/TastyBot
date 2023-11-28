package com.tastybot.tastybot.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(force = true)
public class Angebot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer angebotId;

    @NonNull
    private String angebotstitel;

    private String preference;

    private String verfuegbarAb;

    private String verfuegbarBis;

    private String anmerkungen;
    @NonNull
    private String abholungsort;
    @NonNull
    private Long userId;
    private Long angebotsNachrichtenId;
    private Long angebotLoeschenNachrichtenId;
    private String foto;

    @OneToMany(mappedBy = "angebot", fetch = FetchType.EAGER)
    private List<Interessent> interessenten = new ArrayList<>();
}
