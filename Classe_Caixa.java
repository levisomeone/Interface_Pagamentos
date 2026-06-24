package model;

import java.util.List;

public class Caixa extends Funcionario {

    public boolean processarPagamento(
            Pedido pedido,
            List<Pagamento> pagamentos) {

        double totalPago = 0;

        for (Pagamento pagamento : pagamentos) {

            pagamento.confirmarPagamento();

            totalPago += pagamento.getValor();
        }

        double totalPedido = pedido.calcularTotal();

        if (totalPago >= totalPedido) {

            pedido.fechar();

            System.out.println("Pedido fechado.");

            return true;
        }

        System.out.println("Pagamento insuficiente.");

        return false;
    }

    public void emitirCupomFiscal(Pedido pedido) {

        System.out.println("----------------------");
        System.out.println("CUPOM FISCAL");
        System.out.println("----------------------");

        System.out.println(
            "Pedido: " + pedido.getId()
        );

        System.out.println(
            "Total: R$ " + pedido.calcularTotal()
        );

        System.out.println("----------------------");
    }
}
