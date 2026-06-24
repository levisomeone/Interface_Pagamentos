package model.pagamento;

public class PagamentoCartaoDebito implements MetodoPagamento {

    @Override
    public boolean processar(double valor) {

        System.out.println("Pagamento via cartão de débito aprovado.");
        System.out.println("Valor: R$ " + valor);

        return true;
    }

    @Override
    public String getDescricao() {
        return "CARTAO_DEBITO";
    }
}
