package org.donntu.ai.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "animal_signs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalSign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH},
            mappedBy = "signs")
    @JsonIgnore
    private Set<Animal> animals = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AnimalSign symptom = (AnimalSign) o;

        return Objects.equals(name.toLowerCase().trim(), symptom.name.toLowerCase().trim())
                && Objects.equals(id, symptom.id);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public AnimalSign(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sign {" +
                "id=" + id +
                ", name='" + name + "'" +
                ", signs='" + animals + "'";
    }
}
