package org.donntu.ai.backend.repository;

import org.donntu.ai.backend.entity.Animal;
import org.donntu.ai.backend.entity.Diagnosis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnimalsRepository extends CrudRepository<Animal, Long> {
    Optional<Animal> findByName(String name);
}
