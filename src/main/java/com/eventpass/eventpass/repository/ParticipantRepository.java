package com.eventpass.eventpass.repository;

import com.eventpass.eventpass.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
