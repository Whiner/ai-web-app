package org.donntu.ai.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "symptoms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "symptom",
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<DiagnosesSymptoms> symptomsDiagnoses = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Symptom symptom = (Symptom) o;

        return Objects.equals(name.toLowerCase().trim(), symptom.name.toLowerCase().trim())
                && Objects.equals(id, symptom.id);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public Symptom(String name) {
        this.name = name;
    }

    public Symptom(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "id=" + id +
                ", name='" + name + "'";
    }

    @JsonIgnore
    public Set<Diagnosis> getDiagnoses() {
        return symptomsDiagnoses.stream().map(DiagnosesSymptoms::getDiagnosis).collect(Collectors.toSet());
    }
}
