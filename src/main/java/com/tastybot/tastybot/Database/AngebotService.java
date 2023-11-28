package com.tastybot.tastybot.Database;

import com.tastybot.tastybot.BusinessLogic.AngebotsErsteller;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AngebotService {

    private final AngebotRepository angebotRepository;

    private final AngebotsErsteller angebotsErsteller;

    public Angebot saveOfferInDatabase(String angebotstitel, String preference, String verfuegbarAb, String verfuegbarBis, String anmerkungen, String abholungsort, Long userId, String foto) {

        Angebot angebot = new Angebot();
        angebot.setAngebotstitel(angebotstitel);
        angebot.setPreference(preference);
        angebot.setVerfuegbarAb(verfuegbarAb);
        angebot.setVerfuegbarBis(verfuegbarBis);
        angebot.setAnmerkungen(anmerkungen);
        angebot.setAbholungsort(abholungsort);
        angebot.setUserId(userId);
        angebot.setFoto(foto);

        return angebotsErsteller.sendOfferToChannel(angebotRepository.save(angebot));
    }
}
