package com.tastybot.tastybot.database.repository;

import com.tastybot.tastybot.database.entity.Applicant;
import com.tastybot.tastybot.database.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Applicant entities in the database.
 */
@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

    /**
     * Retrieves an applicant by user ID and associated offer.
     *
     * @param userId The ID of the user associated with the applicant.
     * @param offer The offer associated with the applicant.
     * @return The applicant with the specified user ID and offer.
     */
    Applicant findByUserIdAndOffer(Long userId, Offer offer);
}
