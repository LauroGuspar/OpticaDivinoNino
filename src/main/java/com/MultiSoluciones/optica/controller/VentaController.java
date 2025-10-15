package com.MultiSoluciones.optica.controller;

import com.MultiSoluciones.optica.model.Detalle;
import com.MultiSoluciones.optica.model.Venta;
import com.MultiSoluciones.optica.repository.VentaRepository;
import com.MultiSoluciones.optica.service.ClienteService;
import com.MultiSoluciones.optica.service.Impl.DetalleServiceImpl;
import com.MultiSoluciones.optica.service.Impl.FormaPagoServiceImpl;
import com.MultiSoluciones.optica.service.Impl.TipoVentaServiceImpl;
import com.MultiSoluciones.optica.service.Impl.VentaServiceImpl;
import com.MultiSoluciones.optica.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ventas")
public class VentaController {
    private final DetalleServiceImpl detalleServiceImpl;
    private final VentaServiceImpl ventaServiceImpl;
    private final TipoVentaServiceImpl tipoVentaServiceImpl;
    private final FormaPagoServiceImpl formaPagoServiceImpl;
    private final UsuarioService usuarioService;
    private final ClienteService clienteService;

    public VentaController(DetalleServiceImpl detalleServiceImpl, VentaServiceImpl ventaServiceImpl, TipoVentaServiceImpl tipoVentaServiceImpl, FormaPagoServiceImpl formaPagoServiceImpl, UsuarioService usuarioService, ClienteService clienteService) {
        this.detalleServiceImpl = detalleServiceImpl;
        this.ventaServiceImpl = ventaServiceImpl;
        this.tipoVentaServiceImpl = tipoVentaServiceImpl;
        this.formaPagoServiceImpl = formaPagoServiceImpl;
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }

    @GetMapping("/listar")
    public String mostrarPagina() {
        return "ventas";
    }

    @GetMapping("/api/listar")
    @ResponseBody
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(Map.of("success", true, "data", ventaServiceImpl.listar()));
    }

    @GetMapping("/api/detalles")
    @ResponseBody
    public ResponseEntity<?> listarDetalles() {
        return ResponseEntity.ok(detalleServiceImpl.listarTodo());
    }

    @GetMapping("/api/tipoventas")
    @ResponseBody
    public ResponseEntity<?> listarTipoVentas() {
        return ResponseEntity.ok(tipoVentaServiceImpl.listarActivos());
    }

    @GetMapping("/api/formapagos")
    @ResponseBody
    public ResponseEntity<?> listarFormaPagos() {
        return ResponseEntity.ok(formaPagoServiceImpl.listarFormaPagosActivos());
    }

    @GetMapping("/api/usuarios")
    @ResponseBody
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PostMapping(value = "/api/guardar", consumes = {"multipart/form-data"})
    @ResponseBody
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<?> guardar(
            @RequestParam("venta") String ventaJson) {

        Map<String, Object> response = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Venta venta = objectMapper.readValue(ventaJson, Venta.class);
            Venta guardado = ventaServiceImpl.guardar(venta);

            response.put("success", true);
            response.put("message", "Venta guardada correctamente");
            response.put("venta", guardado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al guardar la venta: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return ventaServiceImpl.obtenerPorId(id)
                .map(venta -> ResponseEntity.ok(Map.of("success", true, "data", venta)))
                .orElse(ResponseEntity.status(404).body(
                        Map.of("success", false, "message", "Venta no encontrada")));
    }

    @PostMapping("/api/cambiar-estado/{id}")
    @ResponseBody
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        boolean exito = ventaServiceImpl.cambiarEstado(id);
        if (exito) {
            response.put("success", true);
            response.put("message", "Estado de la venta actualizado");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Venta no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/api/eliminar/{id}")
    @ResponseBody
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ventaServiceImpl.eliminar(id);
            response.put("success", true);
            response.put("message", "Venta eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar venta: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
