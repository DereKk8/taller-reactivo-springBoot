package com.ejemplo.notasapp.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observacion;
    private Double valor;
    private Double porcentaje;

    @OneToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
}