package org.donntu.ai.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH})
    @JoinTable(name = "animals_signs",
            joinColumns = {@JoinColumn(name = "animal_id")},
            inverseJoinColumns = {@JoinColumn(name = "sign_id")})
    private Set<AnimalSign> signs = new HashSet<>();

    public Animal(String name, Set<AnimalSign> signs) {
        this.name = name;
        this.signs = signs;
    }
    public Animal(Long id, String name, Set<AnimalSign> signs) {
        this.id = id;
        this.name = name;
        this.signs = signs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Animal symptom = (Animal) o;

        return Objects.equals(name.toLowerCase().trim(), symptom.name.toLowerCase().trim())
                && Objects.equals(id, symptom.id);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", signs='" + signs + "'";
    }
}
