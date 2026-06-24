package model.pagamento;

public class PagamentoDinheiro implements MetodoPagamento {

    @Override
    public boolean processar(double valor) {

        System.out.println("Pagamento em dinheiro recebido.");
        System.out.println("Valor: R$ " + valor);

        return true;
    }

    @Override
    public String getDescricao() {
        return "DINHEIRO";
    }
}
