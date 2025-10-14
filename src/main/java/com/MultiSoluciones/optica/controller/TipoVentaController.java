package com.MultiSoluciones.optica.controller;

import com.MultiSoluciones.optica.model.Marca;
import com.MultiSoluciones.optica.model.TipoVenta;
import com.MultiSoluciones.optica.service.TipoVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tipo-ventas")
public class TipoVentaController {
    private final TipoVentaService tipoVentaService;

    public TipoVentaController(TipoVentaService tipoVentaService) {
        this.tipoVentaService = tipoVentaService;
    }

    @GetMapping("/listar")
    public String mostrarPagina() {
        return "tipoventas";
    }

    @GetMapping("/api/listar")
    @ResponseBody
    public ResponseEntity<?> listarApi() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", tipoVentaService.listarTodos());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return tipoVentaService.buscarPorId(id)
                .map(tipoVenta -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("data", tipoVenta);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/guardar")
    @ResponseBody
    public ResponseEntity<?> guardar(@RequestBody TipoVenta tipoVenta) {
        Map<String, Object> response = new HashMap<>();
        try {
            TipoVenta tVentaGuardada = tipoVentaService.guardar(tipoVenta);
            response.put("success", true);
            response.put("tipoVenta", tVentaGuardada);
            response.put("message", tipoVenta.getId() != null ? "Tipo de venta actualizado" : "Tipo de venta creado");
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

    @PostMapping("/api/cambiar-estado/{id}")
    @ResponseBody
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        boolean exito = tipoVentaService.cambiarEstado(id);
        if (exito) {
            response.put("success", true);
            response.put("message", "Estado del tipo actualizado");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Tipo no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/api/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            tipoVentaService.eliminar(id);
            response.put("success", true);
            response.put("message", "Tipo de venta eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar la marca: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
