package com.MultiSoluciones.optica.controller;

import com.MultiSoluciones.optica.model.Detalle;
import com.MultiSoluciones.optica.model.Producto;
import com.MultiSoluciones.optica.service.Impl.DetalleServiceImpl;
import com.MultiSoluciones.optica.service.Impl.ProductoServiceImpl;
import com.MultiSoluciones.optica.service.Impl.VentaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/detalles")
public class DetalleController {
    private final DetalleServiceImpl detalleServiceImpl;
    private final ProductoServiceImpl productoServiceImpl;
    private final VentaServiceImpl ventaServiceImpl;

    public DetalleController(DetalleServiceImpl detalleServiceImpl, ProductoServiceImpl productoServiceImpl, VentaServiceImpl ventaServiceImpl) {
        this.detalleServiceImpl = detalleServiceImpl;
        this.productoServiceImpl = productoServiceImpl;
        this.ventaServiceImpl = ventaServiceImpl;
    }

    @GetMapping("/api/listar")
    @ResponseBody
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(Map.of("success", true, "data", detalleServiceImpl.listarTodo()));
    }

    @GetMapping("/api/productos")
    @ResponseBody
    public ResponseEntity<?> listarProductos() {
        return ResponseEntity.ok(productoServiceImpl.listarTodosLosProductos());
    }

    @PostMapping(value = "/api/guardar", consumes = {"multipart/form-data"})
    @ResponseBody
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<?> guardar(
            @RequestParam("detalle") String detalleJson) {

        Map<String, Object> response = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Detalle detalle = objectMapper.readValue(detalleJson, Detalle.class);
            Detalle guardado = detalleServiceImpl.guardar(detalle);

            response.put("success", true);
            response.put("message", "Detalle guardado correctamente");
            response.put("detalle", guardado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al guardar el detalle: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return detalleServiceImpl.buscarPorId(id)
                .map(detalle -> ResponseEntity.ok(Map.of("success", true, "data", detalle)))
                .orElse(ResponseEntity.status(404).body(
                        Map.of("success", false, "message", "Detalle no encontrado")));
    }

    @PostMapping("/api/cambiar-estado/{id}")
    @ResponseBody
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        boolean exito = detalleServiceImpl.cambiarEstado(id);
        if (exito) {
            response.put("success", true);
            response.put("message", "Estado del detalle actualizado");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Detalle no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/api/eliminar/{id}")
    @ResponseBody
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            detalleServiceImpl.eliminar(id);
            response.put("success", true);
            response.put("message", "Detalle eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar detalle: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
