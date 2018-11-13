package controllers;

import java.awt.event.ActionEvent;
import models.CadastroModel;
import models.Contato;
import models.Observer;
import views.CadastroView;

public class CadastroController implements Observer{

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

    @Override
    public void update() {
        
    }

    public void tratarEvento(ActionEvent evt) {
        
        switch (evt.getActionCommand()){
            
            case "salvar":
                
                Contato c1 = new Contato();
                c1.setNome(this.getView().getCampoNome().getText());
                c1.setTelefone(this.getView().getCampoTelefone().getText());
                c1.setEmail(this.getView().getCampoEmail().getText());

                if(this.validarFormulario(c1) != null){
                    this.getView().notificaUsuario(this.validarFormulario(c1));
                    break;
                }
                
                this.getModel().salvar(c1);
                this.getView().notificaUsuario("Contato salvo com sucesso: \n" + c1.toString());
                   
                break;
    
        } 
    }

    private String validarFormulario(Contato form){
        
        if(form.getNome().length() < 2)
            return "Campo nome é obrigatório";
          
        if(form.getTelefone().indexOf(" ") != -1)
            return "Digite um telefone válido";
        
        if(form.getEmail().indexOf("@") == -1 || form.getEmail().indexOf(".") == -1 || form.getEmail().indexOf("@.") != -1 )
           return "Digite um Email válido";
        
        return null;
    }
 
 }
