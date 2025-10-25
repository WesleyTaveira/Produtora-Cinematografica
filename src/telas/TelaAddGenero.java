
package telas;

import classes.*;
import enums.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class TelaAddGenero extends javax.swing.JFrame {
    
    private ArrayList<Genero> generos;
    private Filme filme;
    private Botao botao;
    
    public void limpaCampos(){
        jtxCodigo.setText("");
        jtxNome_genero.setText("");
        
    }
    
    public void desabilitaCampos(){
        jbtOK.setEnabled(false);
        jbtAddGenero.setEnabled(true);
        jbtRemover.setEnabled(true);
        jtxCodigo.setEnabled(false);
        jtxNome_genero.setEnabled(false);
    }
    
    public void habilitaBotoesExcluir(){
        jbtOK.setEnabled(true);
        jbtAddGenero.setEnabled(false);
        jbtRemover.setEnabled(false);
        jtxCodigo.setEnabled(true);
        jtxNome_genero.setEnabled(false);
    }
    
    
    public void habilitaBotoesNovo(){
        jbtOK.setEnabled(true);
        jbtAddGenero.setEnabled(false);
        jbtRemover.setEnabled(false);
        jtxCodigo.setEnabled(true);
        jtxNome_genero.setEnabled(true);
    }
    
    public boolean validaCampos(){
        if(jtxCodigo.getText().equals("") || jtxNome_genero.getText().equals("")){
            return false;
        }
        return true;
    }
    
    public Genero validaGenero(){
        for(int i = 0;i<generos.size();i++){
            if(generos.get(i).getIdGenero().equals(jtxCodigo.getText())){
                return generos.get(i);
            }
        }
        return null;
    }
    
    
    public void adicionaGenero(){
        if(!validaCampos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Genero g = validaGenero();
        
        if(g == null){
            JOptionPane.showMessageDialog(null, "Genero não existe!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(filme.addGenero(g)){
            JOptionPane.showMessageDialog(null, "Genero adicionado com sucesso!", "Aviso!", JOptionPane.WARNING_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Genero ja contido no filme!", "Aviso!", JOptionPane.WARNING_MESSAGE);
        }
        
        
        
        
    }
    public void removeGenero(){
        if(jtxCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Preencha o codigo!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Genero g = validaGenero();
        
        if(g == null){
            JOptionPane.showMessageDialog(null, "Genero não existe!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(filme.removeGenero(g.getIdGenero())){
            JOptionPane.showMessageDialog(null, "Genero adicionado com sucesso!", "Aviso!", JOptionPane.WARNING_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Genero não contido no filme!", "Aviso!", JOptionPane.WARNING_MESSAGE);
        }
        
        
        
        
    }
    
    
    
    public TelaAddGenero(Filme filme,ArrayList<Genero> listagenero) {
        initComponents();
        botao = Botao.NONE;
        
        
        jbtOK.setEnabled(false);
        jbtAddGenero.setEnabled(true);
        jbtRemover.setEnabled(true);
        jtxCodigo.setEnabled(false);
        jtxNome_genero.setEnabled(false);
        
        
        this.filme = filme;
         
        
        this.generos = listagenero;

        // atualização da tabela
        carregarTabelaGenero();
        carregarTabelaGenerosFilme();
        
        
         //this.filme = filme; 
         
       
    }
    
    public void carregarTabelaGenero(){
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome do Gênero", "Código"}, 0);
        
        for(int i=0;i<generos.size();i++){
            Object linha[] = new Object[] {generos.get(i).getNomeGenero(),generos.get(i).getIdGenero()};
            
            modelo.addRow(linha);
            
        }
        
        jtbGenero.setModel(modelo);
        
        jtbGenero.getColumnModel().getColumn(0).setPreferredWidth(10);
        jtbGenero.getColumnModel().getColumn(1).setPreferredWidth(15);
        
        
        
    }
    
    public void carregarTabelaGenerosFilme(){
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome","Codigo"},0);
        for(int i =0;i<filme.getGeneros().size();i++){
            Object linha[] = new Object[]{filme.getGeneros().get(i).getNomeGenero(),filme.getGeneros().get(i).getIdGenero()};
            modelo.addRow(linha);
        }
        
        tblGenerosFilme.setModel(modelo);
        
    }
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jpnGenero = new javax.swing.JPanel();
        jlbNome_genero = new javax.swing.JLabel();
        jtxNome_genero = new javax.swing.JTextField();
        jlbCodigo = new javax.swing.JLabel();
        jtxCodigo = new javax.swing.JTextField();
        jbtAddGenero = new javax.swing.JButton();
        jbtOK = new javax.swing.JButton();
        jbtRemover = new javax.swing.JButton();
        jpnVerGeneros = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbGenero = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGenerosFilme = new javax.swing.JTable();
        lblGenerosFilme = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(114, 134, 211));

        jPanel1.setBackground(new java.awt.Color(114, 134, 211));

        jpnGenero.setBackground(new java.awt.Color(114, 134, 211));
        jpnGenero.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Adicionar Gênero", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 242, 242))); // NOI18N

        jlbNome_genero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbNome_genero.setForeground(new java.awt.Color(255, 242, 242));
        jlbNome_genero.setText("Nome do Gênero:");

        jlbCodigo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbCodigo.setForeground(new java.awt.Color(255, 242, 242));
        jlbCodigo.setText("Código:");

        jbtAddGenero.setBackground(new java.awt.Color(142, 167, 233));
        jbtAddGenero.setForeground(new java.awt.Color(255, 242, 242));
        jbtAddGenero.setText("Adicionar Gênero");
        jbtAddGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAddGeneroActionPerformed(evt);
            }
        });

        jbtOK.setBackground(new java.awt.Color(142, 167, 233));
        jbtOK.setForeground(new java.awt.Color(255, 242, 242));
        jbtOK.setText("OK");
        jbtOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtOKActionPerformed(evt);
            }
        });

        jbtRemover.setBackground(new java.awt.Color(142, 167, 233));
        jbtRemover.setForeground(new java.awt.Color(255, 242, 242));
        jbtRemover.setText("Remover");
        jbtRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnGeneroLayout = new javax.swing.GroupLayout(jpnGenero);
        jpnGenero.setLayout(jpnGeneroLayout);
        jpnGeneroLayout.setHorizontalGroup(
            jpnGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnGeneroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnGeneroLayout.createSequentialGroup()
                        .addGroup(jpnGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpnGeneroLayout.createSequentialGroup()
                                .addComponent(jlbNome_genero, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxNome_genero, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnGeneroLayout.createSequentialGroup()
                                .addComponent(jlbCodigo)
                                .addGap(68, 68, 68)
                                .addComponent(jtxCodigo)))
                        .addContainerGap(106, Short.MAX_VALUE))
                    .addGroup(jpnGeneroLayout.createSequentialGroup()
                        .addComponent(jbtAddGenero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnGeneroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtOK, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jpnGeneroLayout.setVerticalGroup(
            jpnGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnGeneroLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jpnGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbNome_genero)
                    .addComponent(jtxNome_genero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jbtOK)
                .addGap(1, 1, 1)
                .addGroup(jpnGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbCodigo)
                    .addComponent(jtxCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(jpnGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtAddGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jpnVerGeneros.setBackground(new java.awt.Color(114, 134, 211));
        jpnVerGeneros.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gêneros Disponiveis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 242, 242))); // NOI18N

        jtbGenero.setBackground(new java.awt.Color(229, 224, 255));
        jtbGenero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome do Gênero", "Código"
            }
        ));
        jtbGenero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbGeneroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbGenero);

        tblGenerosFilme.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Codigo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblGenerosFilme);

        lblGenerosFilme.setText("Generos filme");

        btnVoltar.setText("voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnVerGenerosLayout = new javax.swing.GroupLayout(jpnVerGeneros);
        jpnVerGeneros.setLayout(jpnVerGenerosLayout);
        jpnVerGenerosLayout.setHorizontalGroup(
            jpnVerGenerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpnVerGenerosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpnVerGenerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnVerGenerosLayout.createSequentialGroup()
                        .addComponent(lblGenerosFilme, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jpnVerGenerosLayout.createSequentialGroup()
                        .addGroup(jpnVerGenerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(btnVoltar))))
        );
        jpnVerGenerosLayout.setVerticalGroup(
            jpnVerGenerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnVerGenerosLayout.createSequentialGroup()
                .addGroup(jpnVerGenerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnVerGenerosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnVoltar))
                .addGap(18, 18, 18)
                .addComponent(lblGenerosFilme)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnVerGeneros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnVerGeneros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jpnGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtAddGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAddGeneroActionPerformed
        //desabilita os campos dos dados
        habilitaBotoesNovo();
        botao = Botao.NOVO;
    }//GEN-LAST:event_jbtAddGeneroActionPerformed

    private void jbtOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtOKActionPerformed
        if(botao.equals(Botao.NOVO)){
            adicionaGenero();
        }else if(botao.equals(Botao.EXCLUIR)){
            removeGenero();
        }
        
        
        
        //carregar os dados da tabela
        carregarTabelaGenerosFilme();
        desabilitaCampos();
        limpaCampos();
        botao = Botao.NONE;
        //Limpar os campos
        
    }//GEN-LAST:event_jbtOKActionPerformed

    private void jbtRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtRemoverActionPerformed
        habilitaBotoesExcluir();
        botao = Botao.EXCLUIR;
       
        
       
    }//GEN-LAST:event_jbtRemoverActionPerformed

    private void jtbGeneroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbGeneroMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtbGeneroMouseClicked

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAddGenero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAddGenero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAddGenero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAddGenero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                
                //new TelaAddGenero(filme,listageneros).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtAddGenero;
    private javax.swing.JButton jbtOK;
    private javax.swing.JButton jbtRemover;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JLabel jlbNome_genero;
    private javax.swing.JPanel jpnGenero;
    private javax.swing.JPanel jpnVerGeneros;
    private javax.swing.JTable jtbGenero;
    private javax.swing.JTextField jtxCodigo;
    private javax.swing.JTextField jtxNome_genero;
    private javax.swing.JLabel lblGenerosFilme;
    private javax.swing.JTable tblGenerosFilme;
    // End of variables declaration//GEN-END:variables
}
