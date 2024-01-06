package com.mateus98.caffeine;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mateus98.caffeine.Utils.espera;

@RestController
@RequestMapping("outro")
public class OutroCacheController {


    @Cacheable(value = "OutroCache")
    @GetMapping
    public String outroCache() {
        espera(2);
        return "Algum Valor";
    }
}
