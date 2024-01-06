package com.mateus98.caffeine.contrato;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contrato")
@RequiredArgsConstructor
public class ContratoController {

    final ContratoService contratoService;

    @GetMapping
    public List<Contrato> getAllContratos() {
        return contratoService.getAllContratos();
    }

    @GetMapping("/{id}")
    public Contrato getContratoById(@PathVariable Long id) {
        return contratoService.getContratoById(id);
    }

    @PostMapping
    public Contrato saveContrato(@RequestBody Contrato contrato) {
        return contratoService.saveContrato(contrato);
    }

    @PutMapping("/{id}")
    public void updateContrato(@PathVariable Long id, @RequestBody Contrato contrato) {
        contrato.setId(id);
        contratoService.updateContrato(contrato);
    }

    @DeleteMapping("/{id}")
    public void deleteContrato(@PathVariable Long id) {
        contratoService.deleteContrato(id);
    }
}
