package com.ejemplo.notasapp.controlador;

import com.ejemplo.notasapp.modelo.Materia;
import com.ejemplo.notasapp.modelo.Nota;
import com.ejemplo.notasapp.repositorio.RepositorioNota;
import com.ejemplo.notasapp.repositorio.RepositorioEstudiante;
import com.ejemplo.notasapp.repositorio.RepositorioMateria;
import com.ejemplo.notasapp.servicio.ServicioNota;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private RepositorioNota notaRepo;

    @Autowired
    private RepositorioEstudiante estudianteRepo;

    @Autowired
    private RepositorioMateria materiaRepo;

    @Autowired
    private ServicioNota servicioNota;

    @GetMapping("/{estudianteId}")
    public String listar(@PathVariable Long estudianteId, Model model) {
        model.addAttribute("notas", notaRepo.findByEstudianteId(estudianteId));
        model.addAttribute("estudianteId", estudianteId);
        val est = estudianteRepo.findById(estudianteId).orElseThrow(null);
        model.addAttribute("estudianteNombre", est.getNombre());
        model.addAttribute("estudianteApellido", est.getApellido());
        return "notas";
    }

    @GetMapping("/{estudianteId}/nueva")
    public String nueva(@PathVariable Long estudianteId, Model model) {
        Nota nota = new Nota();
        nota.setEstudiante(estudianteRepo.findById(estudianteId).orElseThrow());
        model.addAttribute("nota", nota);
        return "editar-nota";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Nota nota, @RequestParam(required = false) Long materiaId) {
        if (materiaId != null) {
            nota.setMateria(materiaRepo.findById(materiaId).orElse(null));
        }
        notaRepo.save(nota);
        return "redirect:/notas/" + nota.getEstudiante().getId();
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("nota", notaRepo.findById(id).orElseThrow());
        return "editar-nota";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        Nota nota = notaRepo.findById(id).orElseThrow();
        Long estudianteId = nota.getEstudiante().getId();
        notaRepo.delete(nota);
        return "redirect:/notas/" + estudianteId;
    }

    @GetMapping("/{estudianteId}/promedio")
    public String mostrarFormularioPromedio(@PathVariable Long estudianteId, Model model) {
        model.addAttribute("estudianteId", estudianteId);
        model.addAttribute("materia", "");
        model.addAttribute("promedio", null);
        return "nota-promedio";
    }

    @PostMapping("/promedio")
    public String calcularPromedio(@RequestParam Long estudianteId,
            @RequestParam String materia,
            Model model) {
        Materia materiaObj = materiaRepo.findAll().stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(materia))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Materia no encontrada: " + materia));

        double promedio = servicioNota.calcularPromedioPorMateria(estudianteId, materiaObj);
        model.addAttribute("promedio", promedio);
        model.addAttribute("estudianteId", estudianteId);
        model.addAttribute("materia", materia);
        return "nota-promedio";
    }

    @GetMapping("/{estudianteId}/promedio/{materiaId}")
    public String calcularPromedioPorMateriaId(@PathVariable Long estudianteId,
            @PathVariable Long materiaId,
            Model model) {
        Materia materia = materiaRepo.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + materiaId));

        double promedio = servicioNota.calcularPromedioPorMateria(estudianteId, materia);
        model.addAttribute("promedio", promedio);
        model.addAttribute("estudianteId", estudianteId);
        model.addAttribute("materia", materia.getNombre());
        return "nota-promedio";
    }

    @GetMapping("/{estudianteId}/materia/{materiaId}")
    public String notasPorMateria(@PathVariable("estudianteId") Long estudianteId,
            @PathVariable("materiaId") Long materiaId,
            Model model) {
        var notas = notaRepo.findAll().stream()
                .filter(n -> n.getEstudiante() != null && n.getMateria() != null)
                .filter(n -> n.getEstudiante().getId().equals(estudianteId) && n.getMateria().getId().equals(materiaId))
                .toList();
        var estudiante = estudianteRepo.findById(estudianteId).orElseThrow();
        var materia = materiaRepo.findById(materiaId).orElseThrow();
        Double notaFinal = servicioNota.calcularNotaFinal(estudianteId, materia);
        model.addAttribute("notas", notas);
        model.addAttribute("estudianteNombre", estudiante.getNombre() + " " + estudiante.getApellido());
        model.addAttribute("materiaNombre", materia.getNombre());
        model.addAttribute("notaFinal", notaFinal != null ? String.format("%.2f", notaFinal) : "--");
        return "notas-materia";
    }

}