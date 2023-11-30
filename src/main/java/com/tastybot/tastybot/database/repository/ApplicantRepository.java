package com.tastybot.tastybot.database.repository;

import com.tastybot.tastybot.database.entity.Applicant;
import com.tastybot.tastybot.database.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    Applicant findByUserIdAndOffer(Long userId, Offer offer);
}
