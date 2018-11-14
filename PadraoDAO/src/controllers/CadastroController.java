package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.CadastroModel;
import models.Contato;
import models.Observer;
import views.CadastroView;

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

                Contato c1 = new Contato();
                c1.setNome(this.getView().getCampoNome().getText());
                c1.setTelefone(this.getView().getCampoTelefone().getText());
                c1.setEmail(this.getView().getCampoEmail().getText());

                if (this.validarFormulario(c1) != null) {
                    this.getView().notificaUsuario(this.validarFormulario(c1));
                    break;
                }

                if (this.isCadastrado(c1)) {

                    this.getView().notificaUsuario("Usuario já cadastrado: \n");
                    break;
                }

                this.getModel().salvar(c1, this.verificarSistemaArmazenamento());
                this.getView().notificaUsuario("Contato salvo com sucesso: \n" + c1.toString());

                break;

            case "novo":

                this.limparForm();

                break;

            case "excluir":

                Contato c2 = new Contato();
                c2.setNome(this.getView().getCampoNome().getText());
                c2.setTelefone(this.getView().getCampoTelefone().getText());
                c2.setEmail(this.getView().getCampoEmail().getText());

                this.getModel().excluir(c2, this.verificarSistemaArmazenamento());

                break;

            case "editar":
               
                this.camposEditaveis(true);
                this.getView().getCampoNome().setEditable(false);
                this.getView().getBtExcluir().setEnabled(false);
                this.getView().getBtEditar().setActionCommand("salvarAlt");
                this.getView().getBtEditar().setText("Salvar Alterações");
                
                break;
            
            case "salvarAlt":

                
                this.getView().getBtExcluir().setEnabled(true);
                this.camposEditaveis(false);
                this.getView().getBtEditar().setActionCommand("editar");
                this.getView().getBtEditar().setText("Editar");
                
                if(this.validarFormulario(this.contatoSelecionado()) != null){
                    this.getView().notificaUsuario(this.validarFormulario(this.contatoSelecionado()));
                    break;
                }
                
                this.getModel().editar(this.contatoSelecionado(), this.verificarSistemaArmazenamento());
                
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
        this.pegarSelecionado();
    }

    private Contato contatoSelecionado(){
        
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

    public void tratarRadio(ActionEvent evt) {

        switch (evt.getActionCommand()) {

            case "SGBD":

                this.getView().getRadioXML().setSelected(false);
                this.getView().getRadioArquivo().setSelected(false);
                this.preencherTabela(0);

                break;

            case "XML":

                this.getView().getRadioSGBD().setSelected(false);
                this.getView().getRadioArquivo().setSelected(false);
                this.preencherTabela(1);

                break;

            case "ARQUIVO":

                this.getView().getRadioSGBD().setSelected(false);
                this.getView().getRadioXML().setSelected(false);
                this.preencherTabela(2);

                break;

        }

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

    @Override
    public void update() {
        this.preencherTabela(this.verificarSistemaArmazenamento());
        this.limparForm();
    }
}
