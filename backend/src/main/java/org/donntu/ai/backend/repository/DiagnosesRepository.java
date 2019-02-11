package org.donntu.ai.backend.repository;

import org.donntu.ai.backend.entity.Diagnosis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DiagnosesRepository extends CrudRepository<Diagnosis, Long> {
    Optional<Diagnosis> findByName(String name);
}
