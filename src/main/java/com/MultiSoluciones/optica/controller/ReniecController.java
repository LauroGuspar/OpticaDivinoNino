package com.MultiSoluciones.optica.controller;

import com.MultiSoluciones.optica.service.ReniecService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/reniec")
public class ReniecController {
    private final ReniecService reniecService;

    public ReniecController(ReniecService reniecService) {
        this.reniecService = reniecService;
    }

    @GetMapping("/api/buscar/{dni}")
    @ResponseBody
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        Map<String, Object> result = reniecService.buscarPorDni(dni);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}
