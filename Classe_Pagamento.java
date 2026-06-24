package model;

import java.time.LocalDateTime;
import model.pagamento.MetodoPagamento;

public class Pagamento {

    private int id;
    private double valor;
    private LocalDateTime dataHora;
    private String codigoComprovante;
    private MetodoPagamento metodoPagamento;

    public Pagamento() {
    }

    public Pagamento(int id,
                     double valor,
                     LocalDateTime dataHora,
                     String codigoComprovante,
                     MetodoPagamento metodoPagamento) {

        this.id = id;
        this.valor = valor;
        this.dataHora = dataHora;
        this.codigoComprovante = codigoComprovante;
        this.metodoPagamento = metodoPagamento;
    }

    public boolean confirmarPagamento() {
        return metodoPagamento.processar(valor);
    }

    public boolean estornar() {

        System.out.println(
            "Pagamento estornado: " + codigoComprovante
        );

        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }


    public String getCodigoComprovante() {
        return codigoComprovante;
    }

    public void setCodigoComprovante(String codigoComprovante) {
        this.codigoComprovante = codigoComprovante;
    }


    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(
            MetodoPagamento metodoPagamento) {

        this.metodoPagamento = metodoPagamento;
    }
}
