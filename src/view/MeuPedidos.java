/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dao.PedidoDAO;
import dao.PedidoItemDAO;
import dao.Conexao;
import model.Pedido;
import model.Usuario;
import model.Alimento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.PedidoItem;
import model.PedidoAtual;

/**
 *
 * @author Escudeiro
 */
public class MeuPedidos extends javax.swing.JFrame {
    
    private Usuario usuarioLogado;
    private PedidoDAO pedidoDAO;
    private PedidoItemDAO pedidoItemDAO;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MeuPedidos.class.getName());

    /**
     * Creates new form MeuPedidos
     */
    public MeuPedidos(Usuario usuario) {
        initComponents();
        this.usuarioLogado = usuario;
    
        try {
            Connection conn = Conexao.getConexao();
            this.pedidoDAO = new PedidoDAO(conn);
            this.pedidoItemDAO = new PedidoItemDAO(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco!", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        configurarComboBoxAvaliacao();
        
        carregarPedidos();
        
        setLocationRelativeTo(null);
        setTitle("Meus pedidos");
    }
    
    private void carregarPedidos() {
        DefaultTableModel model = (DefaultTableModel) tabelaPedidos.getModel();
        model.setRowCount(0);

        if (PedidoAtual.temItens()) {
            Pedido pedidoAtual = PedidoAtual.getPedidoEmAndamento();
            List<PedidoItem> itens = PedidoAtual.getItensDoPedido();
            
            StringBuilder nomes = new StringBuilder();
            for (PedidoItem item : itens) {
                Alimento a = item.getAlimento();
                if (a != null) {
                    nomes.append(a.getNome())
                         .append(" (x")
                         .append(item.getQuantidade())
                         .append("), ");
                } else {
                    nomes.append("[Alimento Inválido] (x")
                         .append(item.getQuantidade())
                         .append("), ");
                }
            }
            if (!itens.isEmpty()) {
                nomes.setLength(nomes.length() - 2);
            }
            
            model.addRow(new Object[]{
                "CARRINHO",
                String.format("R$ %.2f", pedidoAtual.getTotal()),
                "EM ANDAMENTO",
                nomes.toString()
            });
        }
        
        List<Pedido> pedidos = pedidoDAO.listarPedidosPorUsuario(usuarioLogado);

        for (Pedido p : pedidos) {
            List<PedidoItem> itens = pedidoItemDAO.listarItensPorPedido(p.getId());
            StringBuilder nomes = new StringBuilder();

            for (PedidoItem item : itens) {
                Alimento a = item.getAlimento();
                if (a != null) {
                    nomes.append(a.getNome())
                         .append(" (x")
                         .append(item.getQuantidade())
                         .append("), ");
                } else {
                    nomes.append("[Alimento Removido] (x")
                         .append(item.getQuantidade())
                         .append("), ");
                }
            }

            if (!itens.isEmpty()) {
                nomes.setLength(nomes.length() - 2);
            }

            model.addRow(new Object[]{
                p.getId(),
                String.format("R$ %.2f", p.getTotal()),
                p.getAvaliacao() == 0 ? "—" : p.getAvaliacao() + "★",
                nomes.toString()
            });
        }
    }
    
    private void configurarComboBoxAvaliacao() {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("1 Estrela");
        jComboBox1.addItem("2 Estrelas");
        jComboBox1.addItem("3 Estrelas");
        jComboBox1.addItem("4 Estrelas");
        jComboBox1.addItem("5 Estrelas");
    }
    
    private void excluirPedido() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para excluir.");
            return;
        }
        
        Object idObj = tabelaPedidos.getValueAt(selectedRow, 0);
        
        if ("CARRINHO".equals(idObj)) {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja esvaziar o carrinho?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                PedidoAtual.limparItens();
                JOptionPane.showMessageDialog(this, "Carrinho esvaziado com sucesso!");
                carregarPedidos();
            }
            return;
        }
        
        try {
            int idPedido = (int) idObj;
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o pedido ID: " + idPedido + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                pedidoDAO.excluirPedido(idPedido);
                JOptionPane.showMessageDialog(this, "Pedido ID: " + idPedido + " excluído com sucesso!");
                carregarPedidos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void avaliarPedido() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para avaliar.");
            return;
        }
        
        Object idObj = tabelaPedidos.getValueAt(selectedRow, 0);
        
        if ("CARRINHO".equals(idObj)) {
            JOptionPane.showMessageDialog(this, "O pedido no carrinho não pode ser avaliado. Finalize-o primeiro.");
            return;
        }
        
        try {
            int idPedido = (int) idObj;
            int avaliacao = jComboBox1.getSelectedIndex() + 1;
            
            pedidoDAO.atualizarAvaliacao(idPedido, avaliacao);
            JOptionPane.showMessageDialog(this, "Pedido ID: " + idPedido + " avaliado com " + avaliacao + " estrelas!");
            carregarPedidos();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao avaliar pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPedidos = new javax.swing.JTable();
        btnExcluir = new javax.swing.JButton();
        btnAvaliar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Meus pedidos");

        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        tabelaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Total", "Avaliação", "Itens"
            }
        ));
        jScrollPane1.setViewportView(tabelaPedidos);

        btnExcluir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExcluir.setText("Excluir pedido selecionado");

        btnAvaliar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAvaliar.setText("Avaliar");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Avaliar com:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(260, 260, 260))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAvaliar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(158, 158, 158))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAvaliar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

	    }//GEN-LAST:event_jComboBox1ActionPerformed
	
	    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
	        excluirPedido();
	    }//GEN-LAST:event_btnExcluirActionPerformed
	
	    private void btnAvaliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarActionPerformed
	        avaliarPedido();
	    }//GEN-LAST:event_btnAvaliarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        this.dispose();
        Menu m = new Menu(usuarioLogado);
        m.setVisible(true);
    }//GEN-LAST:event_btnVoltarActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {;
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> new MeuPedidos().setVisible(true));
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvaliar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaPedidos;
    // End of variables declaration//GEN-END:variables
}
