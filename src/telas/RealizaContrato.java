/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.*;
import enums.Botao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ionaa
 */
public class RealizaContrato extends javax.swing.JFrame {

    Botao botao = Botao.NONE;
    ArrayList<Empresa> empresas;
    Map<String, Integer> servicos_index;

    Filme filme;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private void novoContrato() {
        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaValor()) {
            JOptionPane.showMessageDialog(null, "Valor invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaFormatoCnpj()) {
            JOptionPane.showMessageDialog(null, "Formato cnpj invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaCnpjEmpresa() == null) {
            JOptionPane.showMessageDialog(null, "Empresa não cadastrada", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!orcamentoSuficiente()) {
            JOptionPane.showMessageDialog(null, "Orçamento insuficiente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        try {
            Date inicio = sdf.parse(txtDataInicio.getText());
            Date fim = sdf.parse(txtDataFim.getText());
            Contrato contrato = new Contrato(txtCodigo.getText(), txtServico.getSelectedItem().toString(), inicio, fim, Double.parseDouble(txtValor.getText()),
                    filme, validaCnpjEmpresa());

            if (filme.addContrato(contrato) > 0) {
                System.out.println("show");

                validaCnpjEmpresa().addContrato(contrato);

            } else {

                JOptionPane.showMessageDialog(null, "Codigo contrato já existente", "erro", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data invalida", "erro", JOptionPane.PLAIN_MESSAGE);
        }

    }

    private void editaContrato() {
        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaValor()) {
            JOptionPane.showMessageDialog(null, "Valor invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaFormatoCnpj()) {
            JOptionPane.showMessageDialog(null, "Formato cnpj invalido", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaCnpjEmpresa() == null) {
            JOptionPane.showMessageDialog(null, "Empresa não cadastrada", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!orcamentoSuficiente()) {
            JOptionPane.showMessageDialog(null, "Orçamento insuficiente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        Contrato c = validaContrato();
        if (c != null) {
            try {
                Date inicio = sdf.parse(txtDataInicio.getText());
                Date fim = sdf.parse(txtDataFim.getText());

                c.setValor(Double.parseDouble(txtValor.getText()));
                c.setDataInicio(inicio);
                c.setDataTermino(fim);
            } catch (Exception e) {
                System.out.println("Data invalida");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Contrato inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void excluirContrato() {
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o codigo", "erro", JOptionPane.PLAIN_MESSAGE);
        }

        Contrato c = validaContrato();
        if (c != null) {
            c.getEmpresa().removeContrato(txtCodigo.getText());
            filme.removeContrato(txtCodigo.getText());
            return;
        }
        JOptionPane.showMessageDialog(null, "Contrato inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
    }

    private void procuraContrato() {
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o codigo", "erro", JOptionPane.PLAIN_MESSAGE);
        }

        for (Contrato c : filme.getContratos()) {
            if (c.getIdContrato().equals(txtCodigo.getText())) {
                txtCnpj.setText(c.getEmpresa().getCnpj());
                txtServico.setSelectedIndex(0);
                txtValor.setText(Double.toString(c.getValor()));
                String inicio = sdf.format(c.getDataInicio());
                String fim = sdf.format(c.getDataTermino());
                txtDataInicio.setText(inicio);
                txtDataFim.setText(fim);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Contrato inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
    }

    private boolean validaCampos() {
        if (txtCodigo.equals("") || txtCnpj.equals("") || txtServico.getSelectedItem().toString().equals("") || txtValor.equals("")
                || txtDataInicio.equals("") || txtDataFim.equals("")) {

            return false;
        }
        return true;
    }

    private boolean validaValor() {
        try {
            Double.parseDouble(txtValor.getText());
            return true;
        } catch (NumberFormatException e) {

            return false;
        }

    }

    private Contrato validaContrato() {
        for (int i = 0; i < filme.getContratos().size(); i++) {
            if (filme.getContratos().get(i).getIdContrato().equals(txtCodigo.getText())) {
                return filme.getContratos().get(i);
            }
        }

        return null;
    }

    private Empresa validaCnpjEmpresa() {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getCnpj().equals(txtCnpj.getText())) {
                return empresas.get(i);
            }
        }

        return null;
    }

    private boolean orcamentoSuficiente() {
        double valor = Double.parseDouble(txtValor.getText());
        if (botao.equals(Botao.EDITAR)) {
            Contrato c = validaContrato();
            if (valor <= c.getValor()) {
                return true;
            } else {
                valor = c.getValor() - valor;
            }
        }
        if (valor <= filme.getRestante_orcamento()) {
            return true;
        }
        
        return false;
    }

    //IMPLEMENTAR DEPOIS
    private boolean validaFormatoCnpj() {
        return true;
    }

    private void desabilitaBotoes() {
        txtCodigo.setEnabled(false);
        txtCnpj.setEnabled(false);
        txtServico.setEnabled(false);
        txtValor.setEnabled(false);
        txtDataInicio.setEnabled(false);
        txtDataFim.setEnabled(false);
        btnOk.setEnabled(false);
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnProcurar.setEnabled(true);
        btnCancelar.setEnabled(false);
    }

    private void limpaCampos() {
        txtCodigo.setText("");
        txtCnpj.setText("");
        txtServico.setSelectedIndex(0);
        txtValor.setText("");
        txtDataInicio.setText("");
        txtDataFim.setText("");
    }

    private void habilitaBotoesNovo() {
        txtCodigo.setEnabled(true);
        txtCnpj.setEnabled(true);
        txtServico.setEnabled(true);
        txtValor.setEnabled(true);
        txtDataInicio.setEnabled(true);
        txtDataFim.setEnabled(true);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void habilitaBotoesEditar() {
        txtCodigo.setEnabled(true);
        txtCnpj.setEnabled(true);
        txtServico.setEnabled(true);
        txtValor.setEnabled(true);
        txtDataInicio.setEnabled(true);
        txtDataFim.setEnabled(true);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void habilitaBotaoProcurar() {
        txtCodigo.setEnabled(true);
        txtCnpj.setEnabled(false);
        txtServico.setEnabled(false);
        txtValor.setEnabled(false);
        txtDataInicio.setEnabled(false);
        txtDataFim.setEnabled(false);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void habilitaBotaoExcluir() {
        txtCodigo.setEnabled(true);
        txtCnpj.setEnabled(false);
        txtServico.setEnabled(false);
        txtValor.setEnabled(false);
        txtDataInicio.setEnabled(false);
        txtDataFim.setEnabled(false);
        btnOk.setEnabled(true);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnProcurar.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void carregaTabelaServicos() {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Codigo", "CNPJ", "Serviço", "Valor", "Data inicio", "Data fim"}, 0);
        for (Contrato c : this.filme.getContratos()) {
            Object linha[] = new Object[]{c.getIdContrato(), c.getEmpresa().getCnpj(), c.getServico(), Double.toString(c.getValor()), sdf.format(c.getDataInicio()), sdf.format(c.getDataTermino())};
            modelo.addRow(linha);
        }
        tblServicos.setModel(modelo);
    }

    private void carregaTabelaEmpresas() {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "CNPJ"}, 0);
        for (Empresa e : empresas) {
            Object linha[] = new Object[]{e.getNome(), e.getCnpj()};
            modelo.addRow(linha);
        }
        tblEmpresas.setModel(modelo);
    }

    public RealizaContrato(ArrayList<Empresa> empresas, Filme filme) {
        initComponents();
        desabilitaBotoes();

        this.empresas = empresas;
        this.filme = filme;
        carregaTabelaServicos();
        carregaTabelaEmpresas();

        servicos_index = new HashMap<>();

        servicos_index.put("", 0);
        servicos_index.put("Filmagem", 1);
        servicos_index.put("Edição", 2);
        servicos_index.put("Sonoplastia", 3);
        servicos_index.put("Figurinos", 4);
        servicos_index.put("Maquiagem", 5);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblDataInicio = new javax.swing.JLabel();
        lblDataFim = new javax.swing.JLabel();
        txtServico = new javax.swing.JComboBox<>();
        txtCodigo = new javax.swing.JTextField();
        txtCnpj = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        txtDataInicio = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtDataFim = new javax.swing.JTextField();
        lblServico = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        lblCnpj = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnProcurar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        scrlEmpresas = new javax.swing.JScrollPane();
        tblEmpresas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        scrlServicos = new javax.swing.JScrollPane();
        tblServicos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblDataInicio.setText("Data de Inicio");

        lblDataFim.setText("Data de fim");

        txtServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Filmagem", "Edição", "Sonoplastia","Figurinos","Maquiagem" }));

        lblCodigo.setText("Codigo");

        lblServico.setText("Serviço");

        lblValor.setText("Valor");

        lblCnpj.setText("CNPJ empresa");

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnExcluir.setText("Exluir");
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

        tblEmpresas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "CNPJ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpresasMouseClicked(evt);
            }
        });
        scrlEmpresas.setViewportView(tblEmpresas);

        jLabel7.setText("Empresas disponiveis");

        tblServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "CNPJ", "Serviço", "Valor", "Data inicio", "Data fim"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblServicosMouseClicked(evt);
            }
        });
        scrlServicos.setViewportView(tblServicos);

        jLabel8.setText("Serviços crontratados");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnVoltar.setText("voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblValor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCnpj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnOk))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(btnProcurar)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnExcluir)
                                    .addGap(35, 35, 35)
                                    .addComponent(btnCancelar)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDataFim, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDataInicio, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtServico, javax.swing.GroupLayout.Alignment.LEADING, 0, 161, Short.MAX_VALUE)
                                .addComponent(txtValor, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCnpj, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrlEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrlServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 24, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnVoltar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnVoltar)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOk))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCnpj)
                    .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServico)
                    .addComponent(txtServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValor)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataInicio)
                    .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataFim)
                    .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnEditar)
                    .addComponent(btnProcurar)
                    .addComponent(btnExcluir)
                    .addComponent(btnCancelar))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrlEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrlServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarActionPerformed
        botao = Botao.PROCURAR;
        habilitaBotaoProcurar();
        limpaCampos();
    }//GEN-LAST:event_btnProcurarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        botao = Botao.NOVO;
        habilitaBotoesNovo();
        limpaCampos();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        botao = Botao.EDITAR;
        habilitaBotoesEditar();
        limpaCampos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        botao = Botao.NONE;
        desabilitaBotoes();
        limpaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        botao = Botao.EXCLUIR;
        habilitaBotaoExcluir();
        limpaCampos();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (botao.equals(Botao.NOVO)) {

            novoContrato();

        } else if (botao.equals(Botao.EDITAR)) {

            editaContrato();

        } else if (botao.equals(Botao.EXCLUIR)) {

            excluirContrato();

        } else if (botao.equals(Botao.PROCURAR)) {

            procuraContrato();

        }
        carregaTabelaServicos();
        desabilitaBotoes();
        limpaCampos();
    }//GEN-LAST:event_btnOkActionPerformed

    private void tblEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpresasMouseClicked
        int pos = tblEmpresas.getSelectedRow();
        txtCnpj.setText((String) (tblEmpresas.getValueAt(pos, 1)));
    }//GEN-LAST:event_tblEmpresasMouseClicked

    private void tblServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblServicosMouseClicked
        int pos = tblServicos.getSelectedRow();

        Contrato c = validaContrato();

        txtCodigo.setText((String) tblServicos.getValueAt(pos, 0));
        txtCnpj.setText((String) tblServicos.getValueAt(pos, 1));
        txtServico.setSelectedIndex(servicos_index.get((String) tblServicos.getValueAt(pos, 2)));
        txtValor.setText((String) tblServicos.getValueAt(pos, 3));
        txtDataInicio.setText((String) tblServicos.getValueAt(pos, 4));
        txtDataFim.setText((String) tblServicos.getValueAt(pos, 5));


    }//GEN-LAST:event_tblServicosMouseClicked

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
            java.util.logging.Logger.getLogger(RealizaContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RealizaContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RealizaContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RealizaContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(new Runnable() {
        //    public void run() {
        //        new RealizaContrato().setVisible(true);
        //    }
        //});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnProcurar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCnpj;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDataFim;
    private javax.swing.JLabel lblDataInicio;
    private javax.swing.JLabel lblServico;
    private javax.swing.JLabel lblValor;
    private javax.swing.JScrollPane scrlEmpresas;
    private javax.swing.JScrollPane scrlServicos;
    private javax.swing.JTable tblEmpresas;
    private javax.swing.JTable tblServicos;
    private javax.swing.JTextField txtCnpj;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDataFim;
    private javax.swing.JTextField txtDataInicio;
    private javax.swing.JComboBox<String> txtServico;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
