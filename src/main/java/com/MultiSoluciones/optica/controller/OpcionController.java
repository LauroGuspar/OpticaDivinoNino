package com.MultiSoluciones.optica.controller;

import com.MultiSoluciones.optica.model.Opcion;
import com.MultiSoluciones.optica.model.TipoVenta;
import com.MultiSoluciones.optica.repository.OpcionRepository;
import com.MultiSoluciones.optica.service.Impl.OpcionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/opciones")
public class OpcionController {
    private final OpcionServiceImpl opcionService;

    public OpcionController(OpcionServiceImpl opcionService) {
        this.opcionService = opcionService;
    }

    @GetMapping("/listar")
    public String mostrarPagina() {
        return "opciones";
    }

    @GetMapping("/api/listar")
    @ResponseBody
    public ResponseEntity<?> listarApi() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", opcionService.listarOpciones());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return opcionService.buscarOpcionPorId(id)
                .map(opcion -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("data", opcion);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/guardar")
    @ResponseBody
    public ResponseEntity<?> guardar(@RequestBody Opcion opcion) {
        Map<String, Object> response = new HashMap<>();
        try {
            Opcion opcionGuardada = opcionService.guardar(opcion);
            response.put("success", true);
            response.put("tipoVenta", opcionGuardada);
            response.put("message", opcion.getId() != null ? "Opcion actualizada" : "Opcion creada");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error interno del servidor.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
