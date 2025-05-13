package com.ejemplo.notasapp.controlador;

import com.ejemplo.notasapp.modelo.Estudiante;
import com.ejemplo.notasapp.repositorio.RepositorioEstudiante;

import jakarta.transaction.Transactional;

import org.hibernate.annotations.CollectionTypeRegistrations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteRestController {

    @Autowired
    private RepositorioEstudiante estudianteRepo;

    @GetMapping
    public List<Estudiante> listar() {
        return estudianteRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Estudiante> obtener(@PathVariable Long id) {
        return estudianteRepo.findById(id);
    }

    @PostMapping
    @Transactional
    public Estudiante crear(@RequestBody Estudiante estudiante) {
        return estudianteRepo.save(estudiante);
    }

    @PutMapping("/{id}")
    public Estudiante actualizar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        estudiante.setId(id);
        return estudianteRepo.save(estudiante);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estudianteRepo.deleteById(id);
    }
}