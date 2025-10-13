package com.MultiSoluciones.optica.controller;

import com.MultiSoluciones.optica.model.FormaPago;
import com.MultiSoluciones.optica.model.Marca;
import com.MultiSoluciones.optica.service.FormaPagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/forma-pagos")
public class FormaPagoController {
    private final FormaPagoService formapagoService;

    public FormaPagoController(FormaPagoService formapagoService) {
        this.formapagoService = formapagoService;
    }

    @GetMapping("/listar")
    public String mostrarPaginaFormaPago() {
        return "formapagos";
    }

    @GetMapping("/api/listar")
    @ResponseBody
    public ResponseEntity<?> listarFormaPagosApi() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", formapagoService.listarFormaPagosActivos());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerFormaPago(@PathVariable Long id) {
        return formapagoService.buscarFormaPagoPorId(id)
                .map(formaPago -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("data", formaPago);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarFormaPago(@RequestBody FormaPago formaPago) {
        Map<String, Object> response = new HashMap<>();
        try {
            FormaPago formaGuardada = formapagoService.guardarFormaPago(formaPago);
            response.put("success", true);
            response.put("marca", formaGuardada);
            response.put("message", formaPago.getId() != null ? "Forma de pago actualizada" : "Forma de pago creada");
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
    public ResponseEntity<?> cambiarEstadoFormaPago(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        boolean exito = formapagoService.cambiarEstadoFormaPago(id);
        if (exito) {
            response.put("success", true);
            response.put("message", "Estado de la forma de pago actualizado");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Formado de pago no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/api/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarFormaPago(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            formapagoService.eliminarFormaPago(id);
            response.put("success", true);
            response.put("message", "Forma de pago eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar la forma de pago: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
