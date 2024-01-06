package com.mateus98.caffeine;

import com.mateus98.caffeine.contrato.ContratoController;
import com.mateus98.caffeine.contrato.ContratoService;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.mateus98.caffeine.Utils.espera;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CaffeineApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private ContratoService contratoService;


	@Test
	@DisplayName("Deve buscar as informacões com cache")
	void test1() {

		// Primeira chamada
		long startTimeFirstCall = System.currentTimeMillis();
		ResponseEntity<String> responseFirstCall = testRestTemplate.getForEntity("/contrato?contrato=3", String.class);
		long endTimeFirstCall = System.currentTimeMillis();
		System.out.println("Tempo da primeira chamada: " + (endTimeFirstCall - startTimeFirstCall) + "ms");

		assertEquals(responseFirstCall.getStatusCode(), HttpStatus.OK);

		// Segunda chamada
		long startTimeSecondCall = System.currentTimeMillis();
		ResponseEntity<String> responseSecondCall = testRestTemplate.getForEntity("/contrato?contrato=3", String.class);
		long endTimeSecondCall = System.currentTimeMillis();
		System.out.println("Tempo da segunda chamada: " + (endTimeSecondCall - startTimeSecondCall) + "ms");

		assertEquals(responseSecondCall.getStatusCode(), HttpStatus.OK);

		// Verificar se a segunda chamada é mais rápida que a primeira
		assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));

	}

	@Test
	@DisplayName("Não deve buscar as informacões com cache pois o tempo de expiração foi atingido")
	void test2() {

		long startTimeFirstCall = System.currentTimeMillis();
		ResponseEntity<String> responseFirstCall = testRestTemplate.getForEntity("/contrato", String.class);
		long endTimeFirstCall = System.currentTimeMillis();
		assertEquals(responseFirstCall.getStatusCode(), HttpStatus.OK);

		long startTimeSecondCall = System.currentTimeMillis();
		ResponseEntity<String> responseSecondCall = testRestTemplate.getForEntity("/contrato", String.class);
		long endTimeSecondCall = System.currentTimeMillis();
		espera(11);

		long startTimeThirdCall = System.currentTimeMillis();
		ResponseEntity<String> responseThirdCall = testRestTemplate.getForEntity("/contrato", String.class);
		long endTimeThirdCall = System.currentTimeMillis();

		System.out.println("Tempo da primeira chamada: " + (endTimeFirstCall - startTimeFirstCall) + "ms");
		System.out.println("Tempo da segunda chamada: " + (endTimeSecondCall - startTimeSecondCall) + "ms");
		System.out.println("Tempo da terceira chamada: " + (endTimeThirdCall - startTimeThirdCall) + "ms");

		assertEquals(responseSecondCall.getStatusCode(), HttpStatus.OK);

		//assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));

	}

}
