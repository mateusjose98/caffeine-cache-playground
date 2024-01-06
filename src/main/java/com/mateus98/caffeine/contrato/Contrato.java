package com.mateus98.caffeine.contrato;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Contrato {

    private Long id;
    private String tipo;
    private LocalDate assinadoEm;

    public Contrato(String tipo, LocalDate assinadoEm) {
        this.tipo = tipo;
        this.assinadoEm = assinadoEm;
    }
}
