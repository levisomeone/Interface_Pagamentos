package model.pagamento;

public class PagamentoPix implements MetodoPagamento {

    @Override
    public boolean processar(double valor) {

        System.out.println("PIX processado com sucesso.");
        System.out.println("Valor: R$ " + valor);

        return true;
    }

    @Override
    public String getDescricao() {
        return "PIX";
    }
}
