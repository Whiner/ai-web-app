package org.donntu.ai.backend.repository;

import org.donntu.ai.backend.entity.AnimalSign;
import org.donntu.ai.backend.entity.Symptom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnimalSignRepository extends CrudRepository<AnimalSign, Long> {
    Optional<AnimalSign> findByName(String name);
}
