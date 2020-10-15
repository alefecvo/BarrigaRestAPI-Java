package br.cvo.alefecvo.rest.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import br.cvo.alefecvo.rest.core.BaseTest;
import br.cvo.alefecvo.rest.dto.MovimentacaoDto;
import br.cvo.alefecvo.rest.util.DataUtils;
import br.cvo.alefecvo.rest.util.MovimentacaoUtils;

public class MovimentacaoTest extends BaseTest{

		//Deve inserir movimentação com sucesso (POST /transacoes)
		@Test
		public void deveInserirMovimenacaoComSucesso() {
			//Passando dados da requisição para objeto movimentação
			MovimentacaoDto mov = MovimentacaoUtils.getMovimentacaoValida();
					
			//Definindo a estrutura do teste
			given()
				.body(mov)
			.when()
				.post("/transacoes")
			.then()
				.statusCode(201)
			;
		}
		
		//Deve validar campos obrigatórios na movimentação (POST /transacoes)
		@Test
		public void deveValidarCamposObrigatoriosNaMovimentacao() {			
			//Definindo a estrutura do teste
			given()
				.body("{}")
			.when()
				.post("/transacoes")
			.then()
				.statusCode(400)
				.body("$", hasSize(8))
				.body("msg", hasItems(
						"Data da Movimentação é obrigatório",
						"Data do pagamento é obrigatório",
						"Descrição é obrigatório",
						"Interessado é obrigatório",
						"Valor é obrigatório",
						"Valor deve ser um número",
						"Conta é obrigatório",
						"Situação é obrigatório"	
						)) //validando campos obrigatórios
			;
		}

		//Não deve cadastrar movimentação futura (POST /transacoes)
		@Test
		public void naoDeveCadastrarMovimentacaoFutura() {
			//Passando dados da requisição para objeto movimentação
			MovimentacaoDto mov = MovimentacaoUtils.getMovimentacaoValida();
			mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
			
			//Definindo a estrutura do teste
			given() 
				.body(mov)
			.when()
				.post("/transacoes")
			.then()
				.statusCode(400)
				.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual")) //validando campos tentativa de criar registro com 'data_transacao' futura
			;
		}
		
		//Deve remover movimentação (DELETE /transacoes/:id)
		@Test
		public void deveRemoverMovimentacao() {
			//Recuperar Id da Movimentacao atravês da descricao
			Integer MOVIMENTACAO_ID = MovimentacaoUtils.getIdMovimentacaoPelaDescriacao("Movimentacao para exclusao");
			
			//Definindo a estrutura do teste
			given()
				.pathParam("id", MOVIMENTACAO_ID)
			.when()
				.delete("/transacoes/{id}")
			.then()
			.log().all()
				.statusCode(204)
			;
		}	

}
