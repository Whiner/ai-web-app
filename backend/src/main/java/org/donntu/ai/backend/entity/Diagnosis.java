package org.donntu.ai.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH})
    @JoinTable(name = "diagnoses_symptoms",
            joinColumns = {@JoinColumn(name = "diagnoses_id")},
            inverseJoinColumns = {@JoinColumn(name = "symptoms_id")})
    private Set<Symptom> symptoms = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Diagnosis diagnosis = (Diagnosis) o;
        return Objects.equals(id, diagnosis.id) &&
                name.toLowerCase().trim().equals(diagnosis.name.toLowerCase().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Diagnosis(String name, Set<Symptom> symptoms) {
        this.name = name;
        this.symptoms = symptoms;
    }
}
