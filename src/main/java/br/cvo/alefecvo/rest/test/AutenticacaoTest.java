package br.cvo.alefecvo.rest.test;

import static io.restassured.RestAssured.*;
import org.junit.Test;
import br.cvo.alefecvo.rest.core.BaseTest;
import io.restassured.specification.FilterableRequestSpecification;

public class AutenticacaoTest extends BaseTest{
	
		//Cenário 11 - Não deve acessar API sem token (GET /contas)
		@Test
		public void naoDeveAcessarAPISemToken() {
			//Remover token do head
			FilterableRequestSpecification req = (FilterableRequestSpecification) requestSpecification;
			req.removeHeader("Authorization");
			
			//Definindo a estrutura do teste
			given()
			.when()
				.get("/contas")
			.then()
				.statusCode(401)
			;
		}
}

