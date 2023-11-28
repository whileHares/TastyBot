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

    public void saveOfferInDatabase(String angebotstitel, Boolean vegan, String verfuegbarAb, String verfuegbarBis, String anmerkungen, String abholungsort, Long userId) {

        Angebot angebot = new Angebot();
        angebot.setAngebotstitel(angebotstitel);
        angebot.setVegan(vegan);
        angebot.setVerfuegbarAb(verfuegbarAb);
        angebot.setVerfuegbarBis(verfuegbarBis);
        angebot.setAnmerkungen(anmerkungen);
        angebot.setAbholungsort(abholungsort);
        angebot.setUserId(userId);

        angebotRepository.save(angebot);

        angebotsErsteller.sendOfferToChannel(angebot);

    }
}
