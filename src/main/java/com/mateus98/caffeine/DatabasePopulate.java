package com.mateus98.caffeine;

import com.mateus98.caffeine.contrato.Contrato;
import com.mateus98.caffeine.contrato.ContratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatabasePopulate implements ApplicationRunner {

    private final ContratoRepository contratoRepository;

    List<String> tiposContratos = Arrays.asList(
            "Contrato de Prestação de Serviços",
            "Contrato de Compra e Venda",
            "Contrato de Locação Residencial",
            "Contrato de Trabalho por Prazo Determinado",
            "Contrato de Empréstimo Bancário",
            "Contrato de Consultoria Técnica",
            "Contrato de Licenciamento de Software",
            "Contrato de Parceria Comercial",
            "Contrato de Seguro de Vida",
            "Contrato de Fornecimento de Produtos",
            "Contrato de Arrendamento Mercantil (Leasing)",
            "Contrato de Serviços de TI",
            "Contrato de Distribuição",
            "Contrato de Cessão de Direitos Autorais",
            "Contrato de Manutenção Predial",
            "Contrato de Colaboração Científica",
            "Contrato de Parceria Publicitária",
            "Contrato de Financiamento Imobiliário",
            "Contrato de Locação de Equipamentos",
            "Contrato de Prestação de Serviços de Marketing Digital"
    );

    @Override
    public void run(ApplicationArguments args) throws Exception {

        for (int i = 0; i < 100; i++) {
            contratoRepository.save(
                    new Contrato(tiposContratos.get(
                            new Random().nextInt(tiposContratos.size())),
                            LocalDate.now().plusDays(new Random().nextInt(200))
                    )
            );
        }

    }
}
