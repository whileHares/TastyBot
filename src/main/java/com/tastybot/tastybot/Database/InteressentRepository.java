package com.tastybot.tastybot.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteressentRepository extends JpaRepository<Interessent, Integer> {
    Interessent findByUserIdAndAngebot(Long userId, Angebot angebot);
}
