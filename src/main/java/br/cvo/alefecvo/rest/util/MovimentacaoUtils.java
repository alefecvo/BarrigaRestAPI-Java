package br.cvo.alefecvo.rest.util;

import static io.restassured.RestAssured.get;

import br.cvo.alefecvo.rest.dto.MovimentacaoDto;

public class MovimentacaoUtils {

	//Recuperar código de movimento pela descrição
	public static Integer getIdMovimentacaoPelaDescriacao(String descricao) {
		return get("/transacoes?descricao=" + descricao).then().extract().path("id[0]");
	}
	
	public static MovimentacaoDto getMovimentacaoValida() {
		//Passando dados da requisição para objeto movimentação
		MovimentacaoDto mov = new MovimentacaoDto();
		mov.setConta_id(ContaUtils.getIdContaPeloNome("Conta para movimentacoes"));
		mov.setDescricao("Descrição da movimentação");
		mov.setEnvolvido("Envolvido na movimentação");
		mov.setTipo("REC");
		mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
		mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
		mov.setValor(100f);
		mov.setStatus(true);
				
		return mov;
	}
}

