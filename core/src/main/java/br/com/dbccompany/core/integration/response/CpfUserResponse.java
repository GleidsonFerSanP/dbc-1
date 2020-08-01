package br.com.dbccompany.core.integration.response;

import lombok.Data;

@Data
public class CpfUserResponse {

    private boolean status;

    private Result result;

    @Data
    public class Result {

        private String numero_de_cpf;

        private String nome_da_pf;

        private String data_nascimento;

        private String situacao_cadastral;

        private String data_inscricao;

        private String digito_verificador;

        private String comprovante_emitido;

        private String comprovante_emitido_data;
    }

}
