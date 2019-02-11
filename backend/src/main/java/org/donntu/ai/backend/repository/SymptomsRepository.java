package org.donntu.ai.backend.repository;

import org.donntu.ai.backend.entity.Symptom;
import org.springframework.data.repository.CrudRepository;

public interface SymptomsRepository extends CrudRepository<Symptom, Long> {
}
