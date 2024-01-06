package com.mateus98.caffeine.contrato;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static com.mateus98.caffeine.Utils.espera;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContratoService {

    final ContratoRepository contratoRepository;

    @Transactional(readOnly = false)
    @CachePut(value = "ContratoCache", key = "#result.id")
    public Contrato saveContrato(Contrato contrato) {
        Long id = contratoRepository.save(contrato);
        contrato.setId(id);
        return contrato;
    }

    public List<Contrato> getAllContratos() {
        return contratoRepository.readAll();
    }

    @Cacheable(value = "ContratoCache", key = "#id")
    public Contrato getContratoById(Long id) {
        espera(2);
        return contratoRepository.read(id);
    }

    @Transactional(readOnly = false)
    @CachePut(value = "ContratoCache",key="#contrato.id")
    public void updateContrato(Contrato contrato) {
        contratoRepository.update(contrato);
    }

    @Transactional(readOnly = false)
    @CacheEvict(cacheNames="ContratoCache", key = "#id")
    public void deleteContrato(Long id) {
        contratoRepository.delete(id);
    }


}
