package org.donntu.ai.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "diagnoses_symptoms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosesSymptoms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH}
    )
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH}
    )
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;

    private Float confidence;
    private Float mistrust;

    public DiagnosesSymptoms(Diagnosis diagnosis, Symptom symptom, Float confidence, Float mistrust) {
        this.diagnosis = diagnosis;
        this.symptom = symptom;
        this.confidence = confidence;
        this.mistrust = mistrust;
    }

    @Override
    public String toString() {
        return "DiagnosesSymptoms{" +
                "id=" + id +
                ", confidence=" + confidence +
                ", mistrust=" + mistrust +
                '}';
    }
}
