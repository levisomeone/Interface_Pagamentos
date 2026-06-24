package model.pagamento;

public interface MetodoPagamento {

    boolean processar(double valor);

    String getDescricao();
}
