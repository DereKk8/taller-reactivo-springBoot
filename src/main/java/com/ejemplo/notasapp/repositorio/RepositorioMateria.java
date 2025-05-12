package com.ejemplo.notasapp.repositorio;

import com.ejemplo.notasapp.modelo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMateria extends JpaRepository<Materia, Long> {
}