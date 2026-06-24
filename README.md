# Interface_Pagamentos
Interface de pagamentos para sistema de restaurante

Execução da Tabela SQL:

CREATE TABLE pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    forma_pagamento VARCHAR(30) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    codigo_comprovante VARCHAR(100),

    FOREIGN KEY (pedido_id)
        REFERENCES pedido(id)
);

Atribuição dos Métodos:

public double calcularTotalPago(int pedidoId)
  SELECT SUM(valor)
  FROM pagamento
  WHERE pedido_id = ?;

public double calcularValorRestante(int pedidoId)

  SELECT
    p.valor_total -
    COALESCE(SUM(pg.valor),0) AS restante
  FROM pedido p
  LEFT JOIN pagamento pg
  ON p.id = pg.pedido_id
  WHERE p.id = ?
  GROUP BY p.id;


public double calcularFaturamentoDia()

  SELECT SUM(valor)
  FROM pagamento
  WHERE DATE(data_hora) = CURDATE();


Banco
│
├── CREATE TABLE pagamento
├── CREATE TABLE pedido
└── demais tabelas

Java
│
├── PagamentoDAO.java
│   ├── salvar()
│   ├── calcularTotalPago()
│   ├── calcularValorRestante()
│   └── calcularFaturamentoDia()
│
├── Pagamento.java
│
└── TelaCaixa.java

PagamentoDAO.java

package dao;

import connection.Conexao;
import model.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagamentoDAO {

    // Salvar pagamento
    public boolean salvar(Pagamento pagamento, int pedidoId) {

        String sql =
            "INSERT INTO pagamento " +
            "(pedido_id, forma_pagamento, valor, data_hora, codigo_comprovante) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, pedidoId);

            stmt.setString(
                2,
                pagamento.getMetodoPagamento().getDescricao()
            );

            stmt.setDouble(
                3,
                pagamento.getValor()
            );

            stmt.setTimestamp(
                4,
                java.sql.Timestamp.valueOf(
                    pagamento.getDataHora()
                )
            );

            stmt.setString(
                5,
                pagamento.getCodigoComprovante()
            );

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // Total pago de um pedido
    public double calcularTotalPago(int pedidoId) {

        String sql =
            "SELECT SUM(valor) AS total_pago " +
            "FROM pagamento " +
            "WHERE pedido_id = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, pedidoId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total_pago");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }

    // Valor restante para quitar o pedido
    public double calcularValorRestante(int pedidoId) {

        String sql =
            "SELECT " +
            "p.valor_total - " +
            "COALESCE(SUM(pg.valor),0) AS restante " +
            "FROM pedido p " +
            "LEFT JOIN pagamento pg " +
            "ON p.id = pg.pedido_id " +
            "WHERE p.id = ? " +
            "GROUP BY p.id";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, pedidoId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("restante");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }

    // Total arrecadado hoje
    public double calcularFaturamentoDia() {

        String sql =
            "SELECT SUM(valor) AS total " +
            "FROM pagamento " +
            "WHERE DATE(data_hora) = CURDATE()";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }

    // Verifica se pedido está quitado
    public boolean pedidoQuitado(int pedidoId) {

        return calcularValorRestante(pedidoId) <= 0;
    }

    // Calcula troco
    public double calcularTroco(
            double valorRecebido,
            double valorTotal) {

        return valorRecebido - valorTotal;
    }
}
