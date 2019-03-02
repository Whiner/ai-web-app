package org.donntu.ai.backend.utils;

import org.donntu.ai.backend.entity.AnimalSign;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class RepositoryUtil {
    public static <T> boolean deleteFromRepository(CrudRepository<T, Long> repository, Long id) {
        Optional<T> entity = repository.findById(id);
        if(entity.isPresent()) {
            repository.delete(entity.get());
            return true;
        } else {
            return false;
        }
    }
}
