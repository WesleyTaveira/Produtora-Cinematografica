/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import enums.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author ionaa
 */
public class AdicionaPersonagem extends javax.swing.JFrame {

    Botao botao = Botao.NONE;
    ArrayList<Funcionario> funcionarios;
    Filme filme;
    BancoDeDados bd;
    Map<String, Integer> sexos;

    private void criaPersonagem() {
        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaCpf()) {
            JOptionPane.showMessageDialog(null, "CPF invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaCpfAtor() == null) {
            JOptionPane.showMessageDialog(null, "Não exite ator com esse CPF", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!orcamentoSuficiente()) {
            JOptionPane.showMessageDialog(null, "Orçamento insuficiente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        try {
            Personagem personagem = new Personagem(Integer.parseInt(txtIdade.getText()), txtNome.getText(), txtSexo.getSelectedItem().toString(), txtDescricao.getText(),
                    Double.parseDouble(txtCache.getText()), validaCpfAtor(), filme);
            filme.addPersonagem(personagem);
            validaCpfAtor().getPersonagens().add(personagem);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Idade invalida", "erro", JOptionPane.PLAIN_MESSAGE);
        }

    }

    private void editaPersonagem(String nome) {
        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaCpf()) {
            JOptionPane.showMessageDialog(null, "CPF invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaCpfAtor() == null) {
            JOptionPane.showMessageDialog(null, "Não exite ator com esse CPF", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!orcamentoSuficiente()) {
            JOptionPane.showMessageDialog(null, "Orçamento insuficiente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (filme.posicaoPersonagem(txtNome.getText()) < 0) {
            JOptionPane.showMessageDialog(null, "Não exite personagem", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int pos = filme.posicaoPersonagem(nome);

        try {
            double cache = Double.parseDouble(txtCache.getText());
            filme.getPersonagens().get(pos).getAtor().removePersonagem(nome, filme);
            validaCpfAtor().getPersonagens().add(filme.getPersonagens().get(pos));

            filme.getPersonagens().get(pos).setNome(txtNome.getText());
            filme.getPersonagens().get(pos).setIdade(Integer.parseInt(txtIdade.getText()));
            filme.getPersonagens().get(pos).setSexo(txtSexo.getSelectedItem().toString());
            filme.getPersonagens().get(pos).setDescricao(txtDescricao.getText());

            filme.getPersonagens().get(pos).setDescricao(txtDescricao.getText());
            filme.getPersonagens().get(pos).setCache(cache);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Idade invalida", "erro", JOptionPane.PLAIN_MESSAGE);
        }

    }

    private void excluiPersonagem() {

        if (txtNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o codigo", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int pos = filme.posicaoPersonagem(txtNome.getText());
        if (pos >= 0) {

            Personagem p = filme.getPersonagens().get(pos);
            p.getAtor().removePersonagem(txtNome.getText(), filme);

            filme.removePersonagem(txtNome.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Não exite esse Personagem", "erro", JOptionPane.PLAIN_MESSAGE);
        }

    }

    private int sexo(String s) {
        if (s.equals("Masculino")) {
            return 0;
        }
        if (s.equals("Feminino")) {
            return 1;
        }
        return 2;

    }

    private void procuraPersonagem() {

        if (txtNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o codigo", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        for (Personagem p : filme.getPersonagens()) {
            if (p.getNome().equals(txtNome.getText())) {

                txtIdade.setText(Integer.toString(p.getIdade()));
                txtSexo.setSelectedIndex(sexo(p.getSexo()));
                txtDescricao.setText(p.getDescricao());
                txtCpfAtor.setText(p.getAtor().getCpf());
                txtCache.setText(Double.toString(p.getCache()));
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Personagem inexistente", "erro", JOptionPane.PLAIN_MESSAGE);

    }

    private boolean orcamentoSuficiente() {
        try {
            double cache = Double.parseDouble(txtCache.getText());
            if (cache <= filme.getRestante_orcamento()) {
                return true;
            }

            return false;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cache invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }

    private boolean validaCampos() {

        if (txtNome.getText().equals("") || txtIdade.getText().equals("") || txtSexo.getSelectedItem().toString().equals("")
                || txtDescricao.getText().equals("") || txtCpfAtor.getText().equals("") || txtCache.getText().equals("")) {

            return false;
        }

        return true;
    }

    /*
    private boolean validaCache() {
        try {
            Double.parseDouble(txtCache.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cache invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return false;
        }

    }
     */
    private Ator validaCpfAtor() {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getCpf().equals(txtCpfAtor.getText())) {
                return ((Ator) funcionarios.get(i));
            }
        }

        return null;
    }

    //IMPLEMENTAR
    private boolean validaCpf() {
        return true;
    }

    private void limpaConteudos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtSexo.setSelectedIndex(0);
        txtDescricao.setText("");
        txtCpfAtor.setText("");
        txtCache.setText("");

    }

    private void desabilitaBotoes() {
        txtNome.setEnabled(false);
        txtIdade.setEnabled(false);
        txtSexo.setEnabled(false);
        txtDescricao.setEnabled(false);
        txtCpfAtor.setEnabled(false);
        txtCache.setEnabled(false);
        btnOk.setEnabled(false);
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(true);
        btnProcurar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnCancelar.setEnabled(false);

    }

    private void habilitaBotoesNovo() {
        txtNome.setEnabled(true);
        txtIdade.setEnabled(true);
        txtSexo.setEnabled(true);
        txtDescricao.setEnabled(true);
        txtCpfAtor.setEnabled(true);
        txtCache.setEnabled(true);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void habilitaBotoesEditar() {
        txtNome.setEnabled(true);
        txtIdade.setEnabled(true);
        txtSexo.setEnabled(true);
        txtDescricao.setEnabled(true);
        txtCpfAtor.setEnabled(true);
        txtCache.setEnabled(true);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void habilitaBotoesProcurar() {
        txtNome.setEnabled(true);
        txtIdade.setEnabled(false);
        txtSexo.setEnabled(false);
        txtDescricao.setEnabled(false);
        txtCpfAtor.setEnabled(false);
        txtCache.setEnabled(false);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void habilitaBotoesExcluir() {
        txtNome.setEnabled(true);
        txtIdade.setEnabled(false);
        txtSexo.setEnabled(false);
        txtDescricao.setEnabled(false);
        txtCpfAtor.setEnabled(false);
        txtCache.setEnabled(false);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void carregaTabelaPersonagens() {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "Idade", "Sexo", "Cache"}, 0);
        for (Personagem p : this.filme.getPersonagens()) {
            Object[] linha = {p.getNome(), p.getIdade(), p.getSexo(), p.getCache()};
            modelo.addRow(linha);
        }
        tblPersonagens.setModel(modelo);
    }

    private void carregaTabelaAtores() {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "CPF"}, 0);
        for (Funcionario f : funcionarios) {
            if (f instanceof Ator) {
                Object[] linha = {f.getNome(), f.getCpf()};
                modelo.addRow(linha);
            }
        }
        tblAtores.setModel(modelo);
    }

    public AdicionaPersonagem(ArrayList<Funcionario> funcionarios, Filme filme, BancoDeDados bd) {
        initComponents();
        desabilitaBotoes();

        this.funcionarios = funcionarios;
        this.filme = filme;
        this.bd = bd;

        sexos = new HashMap<>();

        sexos.put("Masculino", 1);
        sexos.put("Feminino", 2);
        sexos.put("Outros", 3);

        carregaTabelaPersonagens();
        carregaTabelaAtores();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        lblIdade = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtIdade = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        lblCache = new javax.swing.JLabel();
        txtCache = new javax.swing.JTextField();
        btnNovo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnProcurar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        scrlPersonagens = new javax.swing.JScrollPane();
        tblPersonagens = new javax.swing.JTable();
        btnVoltar = new javax.swing.JButton();
        txtSexo = new javax.swing.JComboBox<>();
        lblPersonagens = new javax.swing.JLabel();
        lblAtores = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblAtores = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCpfAtor = new javax.swing.JTextField();

        jScrollPane1.setViewportView(jTextPane1);

        jTextField4.setText("jTextField4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNome.setText("Nome");

        lblIdade.setText("Idade");

        lblSexo.setText("Sexo");

        lblDescricao.setText("Descrição");

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane2.setViewportView(txtDescricao);

        lblCache.setText("Cache");

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnProcurar.setText("Procurar");
        btnProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        tblPersonagens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome", "Idade", "Sexo", "Cache"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPersonagens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPersonagensMouseClicked(evt);
            }
        });
        scrlPersonagens.setViewportView(tblPersonagens);

        btnVoltar.setText("voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        txtSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "","Masculino","Feminino","Outro" }));

        lblPersonagens.setText("Personagens");

        lblAtores.setText("Atores Disponiveis");

        jScrollPane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane5MouseClicked(evt);
            }
        });

        tblAtores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "CPF"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblAtores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAtoresMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblAtores);

        jLabel1.setText("CPF Ator");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnVoltar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCache, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(208, 208, 208))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(scrlPersonagens, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPersonagens, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnNovo)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEditar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnProcurar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnExcluir)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCancelar)))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAtores, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                        .addComponent(lblIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSexo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnOk))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCache, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCpfAtor))))
                        .addContainerGap(40, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnVoltar)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOk))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdade)
                    .addComponent(txtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSexo)
                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescricao)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCpfAtor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCache)
                    .addComponent(txtCache, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnEditar)
                    .addComponent(btnProcurar)
                    .addComponent(btnExcluir)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPersonagens)
                    .addComponent(lblAtores))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(scrlPersonagens, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        habilitaBotoesEditar();
        botao = Botao.EDITAR;
        limpaConteudos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarActionPerformed
        habilitaBotoesProcurar();
        botao = Botao.PROCURAR;
        limpaConteudos();
    }//GEN-LAST:event_btnProcurarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        habilitaBotoesExcluir();
        botao = Botao.EXCLUIR;
        limpaConteudos();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        botao = Botao.NONE;
        limpaConteudos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        habilitaBotoesNovo();
        botao = Botao.NOVO;
        limpaConteudos();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (botao.equals(Botao.NOVO)) {

            criaPersonagem();

        } else if (botao.equals(Botao.EDITAR)) {

            editaPersonagem(txtNome.getText());

        } else if (botao.equals(Botao.EXCLUIR)) {

            excluiPersonagem();

        } else if (botao.equals(Botao.PROCURAR)) {

            procuraPersonagem();

        }

        desabilitaBotoes();
        if (!botao.equals(Botao.PROCURAR)) {
            limpaConteudos();
        }
        //bd.writeBancoDeDados(bd);

        botao = Botao.NONE;
        carregaTabelaPersonagens();
    }//GEN-LAST:event_btnOkActionPerformed

    private void tblPersonagensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPersonagensMouseClicked
        int pos = tblPersonagens.getSelectedRow();
        int pos_p = filme.posicaoPersonagem((String) tblPersonagens.getValueAt(pos, 0));
        Personagem p = filme.getPersonagens().get(pos_p);

        txtNome.setText(p.getNome());
        txtIdade.setText(Integer.toString(p.getIdade()));
        //System.out.println(p.getSexo());
        txtSexo.setSelectedIndex(sexos.get(p.getSexo()));
        txtDescricao.setText(p.getDescricao());
        txtCpfAtor.setText(p.getAtor().getCpf());
        txtCache.setText(Double.toString(p.getCache()));

    }//GEN-LAST:event_tblPersonagensMouseClicked

    private void jScrollPane5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane5MouseClicked

    private void tblAtoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAtoresMouseClicked
        int pos = tblAtores.getSelectedRow();
        txtCpfAtor.setText((String) tblAtores.getValueAt(pos, 1));

    }//GEN-LAST:event_tblAtoresMouseClicked

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
            java.util.logging.Logger.getLogger(AdicionaPersonagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdicionaPersonagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdicionaPersonagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdicionaPersonagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AdicionaPersonagem().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnProcurar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblAtores;
    private javax.swing.JLabel lblCache;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblIdade;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPersonagens;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JScrollPane scrlPersonagens;
    private javax.swing.JTable tblAtores;
    private javax.swing.JTable tblPersonagens;
    private javax.swing.JTextField txtCache;
    private javax.swing.JTextField txtCpfAtor;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtIdade;
    private javax.swing.JTextField txtNome;
    private javax.swing.JComboBox<String> txtSexo;
    // End of variables declaration//GEN-END:variables
}