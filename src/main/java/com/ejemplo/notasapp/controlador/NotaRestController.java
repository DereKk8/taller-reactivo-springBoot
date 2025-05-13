package com.ejemplo.notasapp.controlador;

import com.ejemplo.notasapp.modelo.Nota;
import com.ejemplo.notasapp.modelo.Estudiante;
import com.ejemplo.notasapp.modelo.Materia;
import com.ejemplo.notasapp.repositorio.RepositorioNota;
import com.ejemplo.notasapp.repositorio.RepositorioEstudiante;
import com.ejemplo.notasapp.repositorio.RepositorioMateria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.ejemplo.notasapp.servicio.ServicioNota;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notas")
public class NotaRestController {

    @Autowired
    private RepositorioNota notaRepo;

    @Autowired
    private RepositorioEstudiante estudianteRepo;

    @Autowired
    private RepositorioMateria materiaRepo;

    @Autowired
    private ServicioNota servicioNota;

    @GetMapping
    public List<Nota> listar() {
        return notaRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Nota> obtener(@PathVariable Long id) {
        return notaRepo.findById(id);
    }

    @PostMapping
    @Transactional
    public Nota crear(@RequestBody Nota nota) {
        if (nota.getEstudiante() != null && nota.getEstudiante().getId() != null) {
            Estudiante estudiante = estudianteRepo.findById(nota.getEstudiante().getId()).orElseThrow();
            nota.setEstudiante(estudiante);
        }
        if (nota.getMateria() != null && nota.getMateria().getId() != null) {
            Materia materia = materiaRepo.findById(nota.getMateria().getId()).orElseThrow();
            nota.setMateria(materia);
        }
        return notaRepo.save(nota);
    }

    @PutMapping("/{id}")
    public Nota actualizar(@PathVariable Long id, @RequestBody Nota nota) {
        nota.setId(id);
        if (nota.getEstudiante() != null && nota.getEstudiante().getId() != null) {
            Estudiante estudiante = estudianteRepo.findById(nota.getEstudiante().getId()).orElseThrow();
            nota.setEstudiante(estudiante);
        }
        if (nota.getMateria() != null && nota.getMateria().getId() != null) {
            Materia materia = materiaRepo.findById(nota.getMateria().getId()).orElseThrow();
            nota.setMateria(materia);
        }
        return notaRepo.save(nota);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        notaRepo.deleteById(id);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<Nota> listarPorEstudiante(@PathVariable Long estudianteId) {
        return notaRepo.findByEstudianteId(estudianteId);
    }

    @GetMapping("/estudiante/{estudianteId}/materia/{materiaId}")
    public List<Nota> listarPorEstudianteYMateria(@PathVariable Long estudianteId, @PathVariable Long materiaId) {
        Materia materia = materiaRepo.findById(materiaId).orElseThrow();
        return notaRepo.findByEstudianteIdAndMateria(estudianteId, materia);
    }

    @GetMapping("/nota-final/{estudianteId}/materia/{materiaId}")
    public ResponseEntity<Double> getNotaFinal(@PathVariable Long estudianteId, @PathVariable Long materiaId) {
        Materia materia = materiaRepo.findById(materiaId).orElseThrow();
        Double notaFinal = servicioNota.calcularNotaFinal(estudianteId, materia);
        return ResponseEntity.ok(notaFinal);
    }
}