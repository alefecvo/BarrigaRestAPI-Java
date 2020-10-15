package br.cvo.alefecvo.rest.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import br.cvo.alefecvo.rest.core.BaseTest;
import br.cvo.alefecvo.rest.dto.MovimentacaoDto;
import br.cvo.alefecvo.rest.util.DataUtils;
import br.cvo.alefecvo.rest.util.MovimentacaoUtils;

public class MovimentacaoTest extends BaseTest{

		//Deve inserir movimenta��o com sucesso (POST /transacoes)
		@Test
		public void deveInserirMovimenacaoComSucesso() {
			//Passando dados da requisi��o para objeto movimenta��o
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
		
		//Deve validar campos obrigat�rios na movimenta��o (POST /transacoes)
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
						"Data da Movimenta��o � obrigat�rio",
						"Data do pagamento � obrigat�rio",
						"Descri��o � obrigat�rio",
						"Interessado � obrigat�rio",
						"Valor � obrigat�rio",
						"Valor deve ser um n�mero",
						"Conta � obrigat�rio",
						"Situa��o � obrigat�rio"	
						)) //validando campos obrigat�rios
			;
		}

		//N�o deve cadastrar movimenta��o futura (POST /transacoes)
		@Test
		public void naoDeveCadastrarMovimentacaoFutura() {
			//Passando dados da requisi��o para objeto movimenta��o
			MovimentacaoDto mov = MovimentacaoUtils.getMovimentacaoValida();
			mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
			
			//Definindo a estrutura do teste
			given() 
				.body(mov)
			.when()
				.post("/transacoes")
			.then()
				.statusCode(400)
				.body("msg", hasItem("Data da Movimenta��o deve ser menor ou igual � data atual")) //validando campos tentativa de criar registro com 'data_transacao' futura
			;
		}
		
		//Deve remover movimenta��o (DELETE /transacoes/:id)
		@Test
		public void deveRemoverMovimentacao() {
			//Recuperar Id da Movimentacao atrav�s da descricao
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
