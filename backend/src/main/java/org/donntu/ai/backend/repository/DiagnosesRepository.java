package org.donntu.ai.backend.repository;

import org.donntu.ai.backend.entity.Diagnosis;
import org.springframework.data.repository.CrudRepository;

public interface DiagnosesRepository extends CrudRepository<Diagnosis, Long> {
}
