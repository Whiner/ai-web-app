package org.donntu.ai.backend.repository;

import org.donntu.ai.backend.entity.DiagnosesSymptoms;
import org.donntu.ai.backend.entity.Diagnosis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DiagnosesSymptomsRepository extends CrudRepository<DiagnosesSymptoms, Long> {
}
