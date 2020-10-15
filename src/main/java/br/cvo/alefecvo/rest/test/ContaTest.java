package br.cvo.alefecvo.rest.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import br.cvo.alefecvo.rest.core.BaseTest;
import br.cvo.alefecvo.rest.dto.ContaDto;
import br.cvo.alefecvo.rest.util.ContaUtils;

public class ContaTest extends BaseTest{

		//Deve incluir uma conta com sucesso (POST /signin) e (POST /contas)
		@Test
		public void deveIncluirContaComSucesso() {	
			//Declarando DTO e setando valor
			ContaDto conta = new ContaDto();
			conta.setNome("Conta inserida");
			
			//Recuperando valor
			String nome =  conta.getNome();
			
			//Definindo a estrutura do teste
			given()
				.body("{\"nome\":\"" + nome +"\"}")
			.when()
				.post("/contas")
			.then()
				.statusCode(201)
			;
		}
		
		//Deve alterar conta com sucesso (PUT /contas/:id)
		@Test
		public void deveAlterarContaComSucesso() {	
			//Declarando DTO e setando valor
			ContaDto conta = new ContaDto();
			conta.setNome("Conta alterada");
			
			//Recuperando valor
			String nome =  conta.getNome();
			
			//Recuperar Id da conta que será alterada
			Integer CONTA_ID = ContaUtils.getIdContaPeloNome("Conta para alterar");
			
			//Definindo a estrutura do teste
			given()
				.body("{\"nome\":\"" + nome + "\"}")
				.pathParam("id", CONTA_ID)
			.when()
				.put("/contas/{id}")
			.then()
				.statusCode(200)
				.body("nome", is("Conta alterada")) //validando registro alterado
			;
		}
		
		//Não deve incluir conta com nome repetido (POST /contas)
		@Test
		public void naoDeveIncluirContaComNomeRepetido() {
			//Declarando DTO e setando valor
			ContaDto conta = new ContaDto();
			conta.setNome("Conta mesmo nome");
			
			//Recuperando valor
			String nome =  conta.getNome();
			
			//Definindo a estrutura do teste
			given()
				.body("{\"nome\":\"" + nome + "\"}")
			.when()
				.post("/contas")
			.then()
				.statusCode(400)
				.body("error", is("Já existe uma conta com esse nome!")) //validando registro já criado
			;
		}
		
		//Não deve remover conta com movimentação (DELETE /contas/:id)
		@Test
		public void naoDeveRemoverContaComMovimentacao() {
			//Recuperar Id da Conta atravês do nome da conta
			Integer CONTA_ID = ContaUtils.getIdContaPeloNome("Conta com movimentacao");
			
			//Definindo a estrutura do teste
			given()
				.pathParam("id", CONTA_ID)
			.when()
				.delete("/contas/{id}")
			.then()
				.statusCode(500)
				.body("constraint", is("transacoes_conta_id_foreign")) //validando erro me banco de dados
			;
		}
		
}
