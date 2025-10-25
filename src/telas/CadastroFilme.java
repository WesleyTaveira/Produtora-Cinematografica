/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.*;
import java.io.File;
import java.util.ArrayList;
import enums.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CadastroFilme extends javax.swing.JFrame {

    private Botao botao = Botao.NONE;
    private ArrayList<Personagem> personagens = new ArrayList();
    private ArrayList<Contrato> contratos = new ArrayList();
    private ArrayList<Estudio> estudios = new ArrayList();
    private ArrayList<Genero> generos = new ArrayList();
    private BancoDeDados bd;
    private Filme filme;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Map<String, Integer> classificacao;

    private void novoFilme() {

        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaIdFilme() != null) {
            JOptionPane.showMessageDialog(null, "Codigo já existente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaDiretor() == null) {
            JOptionPane.showMessageDialog(null, "Diretor inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaRelacionamentos()) {
            JOptionPane.showMessageDialog(null, "O filme deve ter pelo menos um personagem, estudio e genero", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        try {
            filme.setAno(Integer.parseInt(txtAno.getText()));
            filme.setNome(txtNome.getText());
            filme.setIdFilme(txtCodigo.getText());
            filme.setIdioma(txtIdioma.getText());
            filme.setDuracao(Integer.parseInt(txtDuracao.getText()));
            filme.setClassificacao(txtClassificacao.getSelectedItem().toString());
            filme.setSinopse(txtSinopse.getText());
            filme.setTempoProducao(Integer.parseInt(txtTempoProducao.getText()));
            filme.setOrcamento(Double.parseDouble(txtOrcamento.getText()));
            filme.setFaturamento(Double.parseDouble(txtFaturamento.getText()));
            filme.setDiretor(validaDiretor());
            validaDiretor().getFilmes().add(filme);

            bd.getFilmes().add(filme);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor invalido", "erro", JOptionPane.PLAIN_MESSAGE);
        }

    }

    private void procurarFilme() {
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o codigo", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaIdFilme() == null) {
            JOptionPane.showMessageDialog(null, "Filme inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        filme = validaIdFilme();

    }

    private void editarFilme() {
        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaIdFilme() == null) {
            JOptionPane.showMessageDialog(null, "Filme inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        filme = validaIdFilme();

        if (validaDiretor() == null) {
            JOptionPane.showMessageDialog(null, "Diretor inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (!validaRelacionamentos()) {
            JOptionPane.showMessageDialog(null, "O filme deve ter pelo menos um personagem, estudio e genero", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        try {
            filme.setAno(Integer.parseInt(txtAno.getText()));
            filme.setNome(txtNome.getText());

            filme.setIdioma(txtIdioma.getText());
            filme.setDuracao(Integer.parseInt(txtDuracao.getText()));
            filme.setClassificacao(txtClassificacao.getSelectedItem().toString());
            filme.setSinopse(txtSinopse.getText());
            filme.setFaturamento(Double.parseDouble(txtFaturamento.getText()));
            filme.setTempoProducao(Integer.parseInt(txtTempoProducao.getText()));
            filme.setOrcamento(Double.parseDouble(txtOrcamento.getText()));
            filme.getDiretor().getFilmes().remove(filme);
            filme.setDiretor(validaDiretor());
            validaDiretor().getFilmes().add(filme);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor invalido", "erro", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void excluirFilme() {
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o codigo", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (validaIdFilme() == null) {
            JOptionPane.showMessageDialog(null, "Filme inexistente", "erro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        filme = validaIdFilme();

        bd.getFilmes().remove(filme);
        for (int i = 0; i < filme.getPersonagens().size(); i++) {
            Ator ator = filme.getPersonagens().get(i).getAtor();
            ator.removePersonagem(filme.getPersonagens().get(i).getNome(), filme);
        }

        Diretor diretor = filme.getDiretor();
        if (diretor != null) {
            diretor.getFilmes().remove(filme);
        }

        for (int i = 0; i < filme.getEstudios().size(); i++) {
            Estudio estudio = filme.getEstudios().get(i);
            estudio.removeFilme(filme.getIdFilme());
        }

        for (int i = 0; i < bd.getContratos().size(); i++) {

            Contrato c = bd.getContratos().get(i);
            if (c.getFilme().getIdFilme().equals(filme.getIdFilme())) {
                c.getEmpresa().removeContrato(c.getIdContrato());
                bd.getContratos().remove(c);

            }

        }

    }

    private Diretor validaDiretor() {
        for (int i = 0; i < bd.getFuncionarios().size(); i++) {
            if (bd.getFuncionarios().get(i) instanceof Diretor) {
                if (txtCpfDiretor.getText().equals(bd.getFuncionarios().get(i).getCpf())) {
                    return ((Diretor) bd.getFuncionarios().get(i));
                }
            }
        }

        return null;
    }

    private Filme validaIdFilme() {
        for (int i = 0; i < bd.getFilmes().size(); i++) {
            if (bd.getFilmes().get(i).getIdFilme().equals(txtCodigo.getText())) {
                return bd.getFilmes().get(i);
            }
        }

        return null;
    }

    private boolean validaRelacionamentos() {
        if (filme.getPersonagens().isEmpty() || filme.getGeneros().isEmpty() || filme.getEstudios().isEmpty()) {

            return false;
        }
        return true;
    }

    private boolean validaCampos() {
        if (txtNome.getText().equals("") || txtCodigo.getText().equals("") || txtAno.getText().equals("") || txtIdioma.getText().equals("")
                || txtDuracao.getText().equals("") || txtClassificacao.getSelectedItem().toString().equals("") || txtSinopse.equals("")
                || txtTempoProducao.equals("") || txtFaturamento.equals("") || txtOrcamento.equals("")) {

            return false;
        }
        return true;
    }

    private void limpaCampos() {
        this.txtNome.setText("");
        this.txtCodigo.setText("");
        this.txtAno.setText("");
        this.txtIdioma.setText("");
        this.txtDuracao.setText("");
        this.txtClassificacao.setSelectedIndex(0);
        this.txtSinopse.setText("");
        this.txtTempoProducao.setText("");
        this.txtFaturamento.setText("");
        this.txtOrcamento.setText("");
        this.txtRestoOrcamento.setText("");
        this.txtCpfDiretor.setText("");
    }

    private void desabilitaBotoes() {
        this.txtNome.setEnabled(false);
        this.txtCodigo.setEnabled(false);
        this.txtAno.setEnabled(false);
        this.txtIdioma.setEnabled(false);
        this.txtDuracao.setEnabled(false);
        this.txtClassificacao.setEnabled(false);
        this.txtSinopse.setEnabled(false);
        this.txtTempoProducao.setEnabled(false);
        this.txtFaturamento.setEnabled(false);
        this.txtOrcamento.setEnabled(false);
        this.txtRestoOrcamento.setEnabled(false);
        this.txtCpfDiretor.setEnabled(false);

        this.btnNovo.setEnabled(true);
        this.btnEditar.setEnabled(true);
        this.btnProcurar.setEnabled(true);
        this.btnExcluir.setEnabled(true);

        this.btnPersonagens.setEnabled(false);
        this.btnContratos.setEnabled(false);
        this.btnEstudios.setEnabled(false);
        this.btnGeneros.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnOk.setEnabled(false);
    }

    private void habilitaBotoesNovo() {
        this.txtNome.setEnabled(true);
        this.txtCodigo.setEnabled(true);
        this.txtAno.setEnabled(true);
        this.txtIdioma.setEnabled(true);
        this.txtDuracao.setEnabled(true);
        this.txtClassificacao.setEnabled(true);
        this.txtSinopse.setEnabled(true);
        this.txtTempoProducao.setEnabled(true);
        this.txtFaturamento.setEnabled(true);
        this.txtOrcamento.setEnabled(true);
        this.txtRestoOrcamento.setEnabled(false);

        this.txtCpfDiretor.setEnabled(true);

        this.btnNovo.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnProcurar.setEnabled(false);
        this.btnExcluir.setEnabled(false);

        this.btnPersonagens.setEnabled(true);
        this.btnContratos.setEnabled(true);
        this.btnEstudios.setEnabled(true);
        this.btnGeneros.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        this.btnOk.setEnabled(true);
    }

    private void habilitaBotoesExluir() {
        this.txtNome.setEnabled(false);
        this.txtCodigo.setEnabled(true);
        this.txtAno.setEnabled(false);
        this.txtIdioma.setEnabled(false);
        this.txtDuracao.setEnabled(false);
        this.txtClassificacao.setEnabled(false);
        this.txtSinopse.setEnabled(false);
        this.txtTempoProducao.setEnabled(false);
        this.txtFaturamento.setEnabled(false);
        this.txtOrcamento.setEnabled(false);
        this.txtRestoOrcamento.setEnabled(false);
        this.txtCpfDiretor.setEnabled(false);

        this.btnNovo.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnProcurar.setEnabled(false);
        this.btnExcluir.setEnabled(false);

        this.btnPersonagens.setEnabled(false);
        this.btnContratos.setEnabled(false);
        this.btnEstudios.setEnabled(false);
        this.btnGeneros.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        this.btnOk.setEnabled(true);
    }

    private void habilitaBotoesProcurar() {
        this.txtNome.setEnabled(false);
        this.txtCodigo.setEnabled(true);
        this.txtAno.setEnabled(false);
        this.txtIdioma.setEnabled(false);
        this.txtDuracao.setEnabled(false);
        this.txtClassificacao.setEnabled(false);
        this.txtSinopse.setEnabled(false);
        this.txtTempoProducao.setEnabled(false);
        this.txtFaturamento.setEnabled(false);
        this.txtOrcamento.setEnabled(false);
        this.txtRestoOrcamento.setEnabled(false);
        this.txtCpfDiretor.setEnabled(false);

        this.btnNovo.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnProcurar.setEnabled(false);
        this.btnExcluir.setEnabled(false);

        this.btnPersonagens.setEnabled(false);
        this.btnContratos.setEnabled(false);
        this.btnEstudios.setEnabled(false);
        this.btnGeneros.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        this.btnOk.setEnabled(true);
    }

    private void habilitaBotoesEditar() {
        this.txtNome.setEnabled(true);
        this.txtCodigo.setEnabled(false);
        this.txtAno.setEnabled(true);
        this.txtIdioma.setEnabled(true);
        this.txtDuracao.setEnabled(true);
        this.txtClassificacao.setEnabled(true);
        this.txtSinopse.setEnabled(true);
        this.txtTempoProducao.setEnabled(true);
        this.txtFaturamento.setEnabled(true);
        this.txtOrcamento.setEnabled(true);
        this.txtRestoOrcamento.setEnabled(false);
        this.txtCpfDiretor.setEnabled(true);

        this.btnNovo.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnProcurar.setEnabled(false);
        this.btnExcluir.setEnabled(false);

        this.btnPersonagens.setEnabled(true);
        this.btnContratos.setEnabled(true);
        this.btnEstudios.setEnabled(true);
        this.btnGeneros.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        this.btnOk.setEnabled(true);
    }

    private void carregaTabela() {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "Codigo", "Ano", "Idioma", "Duracao", "Classificacao", "Faturamento", "Resto Orcamento"}, 0);
        for (Filme f : bd.getFilmes()) {
            Object[] linha = {f.getNome(), f.getIdFilme(), f.getAno(), f.getIdioma(), f.getDuracao(), f.getClassificacao(), f.getFaturamento(), f.getRestante_orcamento()};
            modelo.addRow(linha);
        }
        tblFilmes.setModel(modelo);
    }

    /**
     * Creates new form CadastroFilme
     */
    public CadastroFilme() {
        initComponents();

        File fileBd = new File(BancoDeDados.getFilePath());
        if (fileBd.exists()) {
            bd = BancoDeDados.readBancoDeDados();
        } else {
            bd = new BancoDeDados();
            BancoDeDados.writeBancoDeDados(bd);
        }

        classificacao = new HashMap<>();
        classificacao.put("", 0);
        classificacao.put("Livre", 1);
        classificacao.put("+12", 2);
        classificacao.put("+16", 3);
        classificacao.put("+18", 4);

        carregaTabela();
        this.jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        desabilitaBotoes();
        BancoDeDados.writeBancoDeDados(bd);

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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblAno = new javax.swing.JLabel();
        lblIdioma = new javax.swing.JLabel();
        lblClassificacao = new javax.swing.JLabel();
        lblSinopse = new javax.swing.JLabel();
        lblDuracao = new javax.swing.JLabel();
        lblTempoProducao = new javax.swing.JLabel();
        lblFaturamento = new javax.swing.JLabel();
        lblOrcamento = new javax.swing.JLabel();
        lblRestoOrcamento = new javax.swing.JLabel();
        txtTempoProducao = new javax.swing.JTextField();
        txtDuracao = new javax.swing.JTextField();
        txtIdioma = new javax.swing.JTextField();
        txtAno = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtFaturamento = new javax.swing.JTextField();
        txtOrcamento = new javax.swing.JTextField();
        txtRestoOrcamento = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSinopse = new javax.swing.JTextArea();
        btnOk = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnContratos = new javax.swing.JButton();
        btnPersonagens = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnProcurar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEstudios = new javax.swing.JButton();
        btnGeneros = new javax.swing.JButton();
        scrlFilmes = new javax.swing.JScrollPane();
        tblFilmes = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        txtClassificacao = new javax.swing.JComboBox<>();
        lblCpfDiretor = new javax.swing.JLabel();
        txtCpfDiretor = new javax.swing.JTextField();
        btnEmpresas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setBackground(new java.awt.Color(114, 134, 211));
        jScrollPane1.setForeground(new java.awt.Color(114, 134, 211));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setForeground(new java.awt.Color(114, 134, 211));

        jLabel1.setText("Nome");

        lblCodigo.setText("Codigo");

        lblAno.setText("Ano de lançamento");

        lblIdioma.setText("Idioma");

        lblClassificacao.setText("Classificação");

        lblSinopse.setText("Sinopse");

        lblDuracao.setText("Duração");

        lblTempoProducao.setText("Tempo de Producao");

        lblFaturamento.setText("Faturamento");

        lblOrcamento.setText("Orçamento");

        lblRestoOrcamento.setText("Resto orçamento");

        txtIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdiomaActionPerformed(evt);
            }
        });

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        txtSinopse.setColumns(20);
        txtSinopse.setLineWrap(true);
        txtSinopse.setRows(5);
        jScrollPane2.setViewportView(txtSinopse);

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnContratos.setText("Contratos");
        btnContratos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContratosActionPerformed(evt);
            }
        });

        btnPersonagens.setText("Personagens");
        btnPersonagens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonagensActionPerformed(evt);
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

        btnEstudios.setText("Estudios");
        btnEstudios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstudiosActionPerformed(evt);
            }
        });

        btnGeneros.setText("Generos");
        btnGeneros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerosActionPerformed(evt);
            }
        });

        scrlFilmes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrlFilmesMouseClicked(evt);
            }
        });

        tblFilmes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "Codigo", "Ano", "Idioma", "Duracao", "Classificação", "Faturamento", "Resto orçamento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblFilmes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFilmesMouseClicked(evt);
            }
        });
        scrlFilmes.setViewportView(tblFilmes);
        if (tblFilmes.getColumnModel().getColumnCount() > 0) {
            tblFilmes.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblFilmes.getColumnModel().getColumn(1).setPreferredWidth(7);
            tblFilmes.getColumnModel().getColumn(2).setPreferredWidth(7);
            tblFilmes.getColumnModel().getColumn(4).setPreferredWidth(7);
            tblFilmes.getColumnModel().getColumn(5).setPreferredWidth(17);
        }

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtClassificacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "","Livre", "+12", "+16", "+18" }));

        lblCpfDiretor.setText("CPF Diretor");

        btnEmpresas.setText("empresas");
        btnEmpresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblIdioma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDuracao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblAno, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblClassificacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSinopse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNome)
                            .addComponent(txtDuracao)
                            .addComponent(txtIdioma)
                            .addComponent(txtAno)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                            .addComponent(txtClassificacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(130, 130, 130)
                        .addComponent(btnOk)
                        .addGap(266, 266, 266))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(scrlFilmes)
                        .addGap(35, 35, 35))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addGap(29, 29, 29)
                                .addComponent(btnEditar)
                                .addGap(18, 18, 18)
                                .addComponent(btnProcurar)
                                .addGap(18, 18, 18)
                                .addComponent(btnExcluir)
                                .addGap(18, 18, 18)
                                .addComponent(btnPersonagens)
                                .addGap(18, 18, 18)
                                .addComponent(btnContratos)
                                .addGap(18, 18, 18)
                                .addComponent(btnEstudios)
                                .addGap(18, 18, 18)
                                .addComponent(btnGeneros)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblRestoOrcamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTempoProducao, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                        .addComponent(lblFaturamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblOrcamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(lblCpfDiretor, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtOrcamento, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTempoProducao)
                                    .addComponent(txtFaturamento)
                                    .addComponent(txtRestoOrcamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                    .addComponent(txtCpfDiretor))))
                        .addContainerGap(323, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnEmpresas)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnEmpresas)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOk))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAno)
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdioma)
                    .addComponent(txtIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDuracao)
                    .addComponent(txtDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClassificacao)
                    .addComponent(txtClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSinopse)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTempoProducao)
                    .addComponent(txtTempoProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFaturamento)
                    .addComponent(txtFaturamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrcamento)
                    .addComponent(txtOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRestoOrcamento)
                    .addComponent(txtRestoOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCpfDiretor)
                    .addComponent(txtCpfDiretor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnEditar)
                    .addComponent(btnProcurar)
                    .addComponent(btnExcluir)
                    .addComponent(btnPersonagens)
                    .addComponent(btnContratos)
                    .addComponent(btnEstudios)
                    .addComponent(btnGeneros)
                    .addComponent(btnCancelar))
                .addGap(32, 32, 32)
                .addComponent(scrlFilmes, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdiomaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdiomaActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        habilitaBotoesNovo();
        limpaCampos();
        botao = Botao.NOVO;
        filme = new Filme();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        limpaCampos();
        if (botao.equals(Botao.NOVO)) {
            excluirFilme();
        }

        botao = Botao.NONE;
        filme = null;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        habilitaBotoesExluir();
        limpaCampos();
        botao = Botao.EXCLUIR;

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarActionPerformed
        habilitaBotoesProcurar();
        botao = Botao.PROCURAR;
        limpaCampos();
    }//GEN-LAST:event_btnProcurarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        habilitaBotoesEditar();
        botao = Botao.EDITAR;
        limpaCampos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnPersonagensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPersonagensActionPerformed

        try {

            if (!txtOrcamento.getText().equals("")) {

                if (!botao.equals(Botao.NOVO)) {
                    filme = validaIdFilme();
                }/* else if (filme.getRestante_orcamento() == null) {
                    filme.setRestante_orcamento(Double.parseDouble(txtOrcamento.getText()));
                }*/

                filme.setOrcamento(Double.parseDouble(txtOrcamento.getText()));

                new AdicionaPersonagem(this.bd.getFuncionarios(), filme, bd).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Preencha o orcamento", "erro", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "filme invalido", "erro", JOptionPane.PLAIN_MESSAGE);
        }

    }//GEN-LAST:event_btnPersonagensActionPerformed

    private void btnContratosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContratosActionPerformed

        try {
            if (!txtOrcamento.getText().equals("")) {

                if (!botao.equals(Botao.NOVO)) {
                    filme = validaIdFilme();
                }/*/ else if (filme.getRestante_orcamento() == null) {
                    filme.setRestante_orcamento(Double.parseDouble(txtOrcamento.getText()));
                }*/

                filme.setOrcamento(Double.parseDouble(txtOrcamento.getText()));
                new RealizaContrato(this.bd.getEmpresas(), this.filme).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Preencha o orcamento", "erro", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "filme invalido", "erro", JOptionPane.PLAIN_MESSAGE);
        }


    }//GEN-LAST:event_btnContratosActionPerformed

    private void btnEstudiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstudiosActionPerformed

        if (!txtCodigo.getText().equals("")) {

            if (!botao.equals(Botao.NOVO)) {
                filme = validaIdFilme();
            }

            new addEstudio(filme, bd.getEstudios()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "preencha codigo", "erro", JOptionPane.PLAIN_MESSAGE);

        }

    }//GEN-LAST:event_btnEstudiosActionPerformed

    private void btnGenerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerosActionPerformed
        if (!txtCodigo.getText().equals("")) {

            if (!botao.equals(Botao.NOVO)) {
                filme = validaIdFilme();
            }

            new TelaAddGenero(filme, bd.getGeneros()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "preencha codigo", "erro", JOptionPane.PLAIN_MESSAGE);

        }
    }//GEN-LAST:event_btnGenerosActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed

        if (botao.equals(Botao.NOVO)) {
            novoFilme();

        } else if (botao.equals(Botao.EDITAR)) {
            editarFilme();

        } else if (botao.equals(Botao.EXCLUIR)) {
            excluirFilme();

        } else if (botao.equals(Botao.PROCURAR)) {
            procurarFilme();

        }
        filme = null;

        botao = Botao.NONE;
        desabilitaBotoes();
        limpaCampos();
        BancoDeDados.writeBancoDeDados(bd);
        carregaTabela();
    }//GEN-LAST:event_btnOkActionPerformed

    private void scrlFilmesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrlFilmesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_scrlFilmesMouseClicked


    private void tblFilmesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFilmesMouseClicked
        int pos = tblFilmes.getSelectedRow();

        String codigo = (String) tblFilmes.getValueAt(pos, 1);
        txtCodigo.setText(codigo);

        Filme f = validaIdFilme();

        txtCodigo.setText("");

        txtNome.setText(f.getNome());
        txtCodigo.setText(f.getIdFilme());
        txtAno.setText(Integer.toString(f.getAno()));
        txtIdioma.setText(f.getIdioma());
        txtClassificacao.setSelectedIndex(classificacao.get(f.getClassificacao()));
        txtDuracao.setText(Integer.toString(f.getDuracao()));
        txtSinopse.setText(f.getSinopse());
        txtTempoProducao.setText(Integer.toString(f.getTempoProducao()));
        txtFaturamento.setText(Double.toString(f.getFaturamento()));
        txtOrcamento.setText(Double.toString(f.getOrcamento()));
        txtRestoOrcamento.setText(Double.toString(f.getRestante_orcamento()));
        txtCpfDiretor.setText(f.getDiretor().getCpf());
    }//GEN-LAST:event_tblFilmesMouseClicked

    private void btnEmpresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresasActionPerformed
        dispose();
        new CadastroEmpresa().setVisible(true);
    }//GEN-LAST:event_btnEmpresasActionPerformed

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
            java.util.logging.Logger.getLogger(CadastroFilme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroFilme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroFilme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroFilme.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroFilme().setVisible(true);
            }
        });
    }

    public JTextField getTxtRestoOrcamento() {
        return txtRestoOrcamento;
    }

    public void setTxtRestoOrcamento(JTextField txtRestoOrcamento) {
        this.txtRestoOrcamento = txtRestoOrcamento;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnContratos;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEmpresas;
    private javax.swing.JButton btnEstudios;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGeneros;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPersonagens;
    private javax.swing.JButton btnProcurar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAno;
    private javax.swing.JLabel lblClassificacao;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCpfDiretor;
    private javax.swing.JLabel lblDuracao;
    private javax.swing.JLabel lblFaturamento;
    private javax.swing.JLabel lblIdioma;
    private javax.swing.JLabel lblOrcamento;
    private javax.swing.JLabel lblRestoOrcamento;
    private javax.swing.JLabel lblSinopse;
    private javax.swing.JLabel lblTempoProducao;
    private javax.swing.JScrollPane scrlFilmes;
    private javax.swing.JTable tblFilmes;
    private javax.swing.JTextField txtAno;
    private javax.swing.JComboBox<String> txtClassificacao;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCpfDiretor;
    private javax.swing.JTextField txtDuracao;
    private javax.swing.JTextField txtFaturamento;
    private javax.swing.JTextField txtIdioma;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtOrcamento;
    private javax.swing.JTextField txtRestoOrcamento;
    private javax.swing.JTextArea txtSinopse;
    private javax.swing.JTextField txtTempoProducao;
    // End of variables declaration//GEN-END:variables
}
