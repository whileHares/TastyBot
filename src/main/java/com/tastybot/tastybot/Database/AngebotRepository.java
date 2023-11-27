package com.tastybot.tastybot.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AngebotRepository extends JpaRepository<Angebot, Integer> {
}
