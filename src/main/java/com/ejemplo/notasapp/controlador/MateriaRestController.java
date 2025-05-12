package com.ejemplo.notasapp.controlador;

import com.ejemplo.notasapp.modelo.Materia;
import com.ejemplo.notasapp.repositorio.RepositorioMateria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materias")
public class MateriaRestController {

    @Autowired
    private RepositorioMateria materiaRepo;

    @GetMapping
    public List<Materia> listar() {
        return materiaRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Materia> obtener(@PathVariable Long id) {
        return materiaRepo.findById(id);
    }

    @PostMapping
    public Materia crear(@RequestBody Materia materia) {
        return materiaRepo.save(materia);
    }

    @PutMapping("/{id}")
    public Materia actualizar(@PathVariable Long id, @RequestBody Materia materia) {
        materia.setId(id);
        return materiaRepo.save(materia);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        materiaRepo.deleteById(id);
    }
}