package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.CadastroModel;
import model.Contato;
import model.Observer;
import view.CadastroView;

public class CadastroController implements Observer {

    private CadastroView view;
    private CadastroModel model;

    public CadastroController(CadastroView view, CadastroModel model) {

        this.setModel(model);
        this.setView(view);
        this.getModel().addObserver(this);

    }

    public CadastroView getView() {
        return view;
    }

    public void setView(CadastroView view) {
        if (view != null) {
            this.view = view;
        }
    }

    public CadastroModel getModel() {
        return model;
    }

    public void setModel(CadastroModel model) {
        if (model != null) {
            this.model = model;
        }
    }

    public void tratarEvento(ActionEvent evt) {

        switch (evt.getActionCommand()) {

            case "salvar":

                String temp = this.contatoSelecionado().toString();

                if (this.validarFormulario(this.contatoSelecionado()) != null) {
                    this.getView().notificaUsuario(this.validarFormulario(this.contatoSelecionado()));
                    break;
                }
                if (this.isCadastrado(this.contatoSelecionado())) {
                    this.getView().notificaUsuario("Usuario já cadastrado: \n");
                    break;
                }

                if (this.getModel().salvar(this.contatoSelecionado(), this.verificarSistemaArmazenamento())) {

                    this.getView().notificaUsuario("Contato salvo com sucesso: \n" + temp);
                } else {
                    this.getView().notificaUsuario("Não foi possivel salvar o contato");
                }

                break;

            case "novo":

                this.limparForm();

                break;

            case "excluir":

                if (this.getModel().excluir(this.contatoSelecionado(), this.verificarSistemaArmazenamento())) {
                    this.getView().notificaUsuario("Contato excluído com sucesso!");
                } else {
                    this.getView().notificaUsuario("Contato não foi excluído, tente novamente...");
                }

                break;

            case "editar":

                this.camposEditaveis(true);
                this.getView().getCampoNome().setEditable(false);
                this.getView().getBtExcluir().setEnabled(false);
                this.getView().getBtEditar().setActionCommand("salvarAlt");
                this.getView().getBtEditar().setText("Salvar Alterações");

                break;

            case "salvarAlt":

                if (this.validarFormulario(this.contatoSelecionado()) != null) {
                    this.getView().notificaUsuario(this.validarFormulario(this.contatoSelecionado()));
                    break;

                } else {

                    this.getView().getBtExcluir().setEnabled(true);
                    this.camposEditaveis(false);
                    this.getView().getBtEditar().setActionCommand("editar");
                    this.getView().getBtEditar().setText("Editar");

                    if (this.getModel().editar(this.contatoSelecionado(), this.verificarSistemaArmazenamento())) {
                        this.getView().notificaUsuario("Contato alterado com sucesso!");
                    } else {
                        this.getView().notificaUsuario("Contato não alterado, tente novamente...");
                    }
                }

                break;

        }
    }

    private String validarFormulario(Contato form) {

        if (form.getNome().length() < 2) {
            return "Campo nome é obrigatório";
        }

        if (form.getTelefone().indexOf(" ") != -1) {
            return "Digite um telefone válido";
        }

        if (form.getEmail().indexOf("@") == -1 || form.getEmail().indexOf(".") == -1 || form.getEmail().indexOf("@.") != -1) {
            return "Digite um Email válido";
        }

        return null;
    }

    public void tratarTecladoTabela(KeyEvent evt) {
       
    
        this.pegarSelecionado();
    }

    public void tratarTecladoMouse(MouseEvent evt) {
       this.getView().getBtEditar().setActionCommand("editar");
       this.getView().getBtEditar().setText("Editar");
       
        this.pegarSelecionado();
    }

    private Contato contatoSelecionado() {

        Contato contato = new Contato();

        contato.setNome(this.getView().getCampoNome().getText());
        contato.setTelefone(this.getView().getCampoTelefone().getText());
        contato.setEmail(this.getView().getCampoEmail().getText());

        return contato;
    }

    private void pegarSelecionado() {

        if (this.getView().getTabelaDados().getSelectedRow() != -1) {

            this.getView().getBtSalvar().setEnabled(false);
            this.getView().getBtEditar().setEnabled(true);
            this.getView().getBtExcluir().setEnabled(true);

            String nome = (String) this.getView().getTabelaDados().getValueAt(this.getView().getTabelaDados().getSelectedRow(), 0);
            String telefone = (String) this.getView().getTabelaDados().getValueAt(this.getView().getTabelaDados().getSelectedRow(), 1);
            String email = (String) this.getView().getTabelaDados().getValueAt(this.getView().getTabelaDados().getSelectedRow(), 2);

            this.getView().getCampoNome().setText(nome);
            this.getView().getCampoTelefone().setText(telefone);
            this.getView().getCampoEmail().setText(email);

            this.camposEditaveis(false);

        }

    }

    public void preencherTabela(int sistemaArmazenamento) {

        DefaultTableModel modelo = (DefaultTableModel) this.getView().getTabelaDados().getModel();
        modelo.setNumRows(0);

        ArrayList<Contato> contatos = this.getModel().lista(sistemaArmazenamento);

        if (contatos != null) {
            for (Contato c : contatos) {

                modelo.addRow(new Object[]{
                    c.getNome(),
                    c.getTelefone(),
                    c.getEmail()

                });
            }

        }
        this.limparForm();
    }

    private void ativarCampos() {

        this.getView().getCampoNome().setEnabled(true);
        this.getView().getCampoTelefone().setEnabled(true);
        this.getView().getCampoEmail().setEnabled(true);
        this.getView().getBtSalvar().setEnabled(true);
        this.getView().getTabelaDados().setEnabled(true);
        this.getView().getBtNovo().setEnabled(true);

    }

    private int verificarSistemaArmazenamento() {

        if (this.getView().getRadioSGBD().isSelected()) {
            return 0;
        }

        if (this.getView().getRadioXML().isSelected()) {
            return 1;
        }

        if (this.getView().getRadioArquivo().isSelected()) {
            return 2;
        }

        return -1;
    }

    private void limparForm() {

        this.getView().getCampoNome().setText("");
        this.getView().getCampoTelefone().setText("");
        this.getView().getCampoEmail().setText("");
        this.getView().getBtSalvar().setEnabled(true);
        this.getView().getBtEditar().setEnabled(false);
        this.getView().getBtExcluir().setEnabled(false);
        this.getView().getTabelaDados().clearSelection();
        this.camposEditaveis(true);

    }

    private void camposEditaveis(boolean b) {

        this.getView().getCampoNome().setEditable(b);
        this.getView().getCampoTelefone().setEditable(b);
        this.getView().getCampoEmail().setEditable(b);

    }

    private boolean isCadastrado(Contato cont) {

        Contato contato = this.getModel().buscar(cont.getNome(), this.verificarSistemaArmazenamento());

        if (contato == null) {
            return false;
        } else {
            return true;
        }
    }

    public void tratarRadio(ActionEvent evt) {

        this.ativarCampos();

        switch (evt.getActionCommand()) {

            case "SGBD":

                this.getView().getRadioSGBD().setSelected(true);
                this.getView().getRadioXML().setSelected(false);
                this.getView().getRadioArquivo().setSelected(false);
                this.preencherTabela(0);

                break;

            case "XML":

                this.getView().getRadioXML().setSelected(true);
                this.getView().getRadioSGBD().setSelected(false);
                this.getView().getRadioArquivo().setSelected(false);
                this.preencherTabela(1);

                break;

            case "ARQUIVO":

                this.getView().getRadioArquivo().setSelected(true);
                this.getView().getRadioSGBD().setSelected(false);
                this.getView().getRadioXML().setSelected(false);
                this.preencherTabela(2);

                break;

        }

    }

    @Override
    public void update() {
        this.preencherTabela(this.verificarSistemaArmazenamento());
        this.limparForm();
    }
}
