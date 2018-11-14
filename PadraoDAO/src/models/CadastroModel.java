package models;

import DAO.ContatoARQ;
import DAO.ContatoDAO;
import DAO.ContatoSGBD;
import DAO.ContatoXML;
import java.util.ArrayList;

public class CadastroModel {

    private ArrayList<ContatoDAO> daos;
    private ArrayList<Observer> observers;

    public CadastroModel() {

        this.setObservers(new ArrayList<Observer>());
        this.setDaos(new ArrayList<ContatoDAO>());
        this.criarDAOS();

    }

    public boolean salvar(Contato contato, int sistemaArquivo) {
        
        boolean salvo = false;
        
        if (this.getDaos().get(sistemaArquivo) != null) {
         salvo = this.getDaos().get(sistemaArquivo).salvar(contato);
            this.notifyALL();
        }
        return salvo;
    }   

    public void editar(Contato contato, int sistemaArmazenamento) {

         if (this.getDaos().get(sistemaArmazenamento) != null) {
            this.getDaos().get(sistemaArmazenamento).editar(contato);
            this.notifyALL();
         }
  
    }

    public void excluir(Contato contato, int sistemaArmazenamento) {

        if (this.getDaos().get(sistemaArmazenamento) != null) {
            this.getDaos().get(sistemaArmazenamento).excluir(contato.getNome());
            this.notifyALL();

        }

    }

    public Contato buscar(String nome, int sistemaArmazenamento) {

        return this.getDaos().get(sistemaArmazenamento).buscar(nome);

    }

    public ArrayList<Contato> lista(int sistemaDados) {
        
        if(this.getDaos().get(sistemaDados) != null)
          return this.getDaos().get(sistemaDados).lista();

        return null;
    }

    public void addObserver(Observer ob) {
        this.getObservers().add(ob);
    }

    private void notifyALL() {

        for (Observer observer : observers) {
            observer.update();
        }

    }

    private void criarDAOS() {

        this.getDaos().add(new ContatoSGBD());
        this.getDaos().add(new ContatoXML());
        this.getDaos().add(new ContatoARQ());

    }

    private ArrayList<ContatoDAO> getDaos() {
        return daos;
    }

    private void setDaos(ArrayList<ContatoDAO> daos) {
        if (daos != null) {
            this.daos = daos;
        }
    }

    private ArrayList<Observer> getObservers() {
        return observers;
    }

    private void setObservers(ArrayList<Observer> observers) {
        if (observers != null) {
            this.observers = observers;
        }
    }

}
