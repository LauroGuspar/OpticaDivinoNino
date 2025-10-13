package com.MultiSoluciones.optica.controller;

import com.MultiSoluciones.optica.model.Inventario;
import com.MultiSoluciones.optica.repository.ProductoRepository;
import com.MultiSoluciones.optica.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/inventarios")
public class InventarioController {
    private final InventarioService inventarioService;
    private final ProductoRepository productoRepository;

    public InventarioController(InventarioService inventarioService, ProductoRepository productoRepository) {
        this.inventarioService = inventarioService;
        this.productoRepository = productoRepository;
    }

    @GetMapping("/listar")
    public String mostrarPaginaInventarios() {
        return "inventarios";
    }

    @GetMapping("/api/listar")
    @ResponseBody
    public ResponseEntity<?> listarInventarios() {
        return ResponseEntity.ok(Map.of("success", true, "data", inventarioService.listarInventarios()));
    }

    @GetMapping("/api/productos")
    @ResponseBody
    public ResponseEntity<?> listarProductos() {
        return ResponseEntity.ok(productoRepository.findAllByEstadoNot(2));
    }

    @PostMapping(value = "/api/guardar", consumes = {"multipart/form-data"})
    @ResponseBody
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<?> guardarInventario(@RequestParam("inventario") String inventarioJson) {

        Map<String, Object> response = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Inventario inventario = objectMapper.readValue(inventarioJson, Inventario.class);
            Inventario inventarioGuardado = inventarioService.guardarInventario(inventario);

            response.put("success", true);
            response.put("message", "Producto guardado correctamente");
            response.put("producto", inventarioGuardado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al guardar el inventario: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerInventario(@PathVariable Long id) {
        return inventarioService.obtenerInventarioPorId(id)
                .map(inventario -> ResponseEntity.ok(Map.of("success", true, "data", inventario)))
                .orElse(ResponseEntity.status(404).body(Map.of("success", false, "message", "Inventario no encontrado")));
    }
}
