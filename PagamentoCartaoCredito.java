package model.pagamento;

public class PagamentoCartaoCredito implements MetodoPagamento {

    @Override
    public boolean processar(double valor) {

        System.out.println("Pagamento via cartão de crédito aprovado.");
        System.out.println("Valor: R$ " + valor);

        return true;
    }

    @Override
    public String getDescricao() {
        return "CARTAO_CREDITO";
    }
}
