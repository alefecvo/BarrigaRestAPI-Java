package br.cvo.alefecvo.rest.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import br.cvo.alefecvo.rest.core.BaseTest;
import br.cvo.alefecvo.rest.util.ContaUtils;

public class SaldoTest extends BaseTest{

		//Deve calcular saldo das contas (GET /saldo)
		@Test
		public void deveCalcularSaldoDasContas() {
			//Recuperar Id da Conta pelo nome da conta
			Integer CONTA_ID = ContaUtils.getIdContaPeloNome("Conta para saldo");
			
			//Definindo a estrutura do teste
			given()
			.when()
				.get("/saldo")
			.then()
				.statusCode(200)
				.body("find{it.conta_id == "+CONTA_ID+"}.saldo ", is("534.00")) //validando saldo de contas
			;
		}
			
}
