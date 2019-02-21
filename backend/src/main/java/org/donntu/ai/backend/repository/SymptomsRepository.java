package org.donntu.ai.backend.repository;

import org.donntu.ai.backend.entity.Symptom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SymptomsRepository extends CrudRepository<Symptom, Long> {
    Optional<Symptom> findByName(String name);
}
