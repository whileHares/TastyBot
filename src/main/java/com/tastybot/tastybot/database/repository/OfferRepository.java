package com.tastybot.tastybot.database.repository;

import com.tastybot.tastybot.database.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    Offer findByOfferMessageId(Long offerMessageId);
    Offer findByOfferDeleteMessageId(Long offerDeleteMessageId);
}
