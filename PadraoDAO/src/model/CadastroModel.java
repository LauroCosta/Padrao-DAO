package model;


import DAO.ContatoDAO;
import DAO.ContatoDAO_ARQ;
import DAO.ContatoDAO_SGBD;
import DAO.ContatoDAO_XML;
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

    public boolean editar(Contato contato, int sistemaArmazenamento) {

       boolean editou = false;
       
         if (this.getDaos().get(sistemaArmazenamento) != null) {
            editou = this.getDaos().get(sistemaArmazenamento).editar(contato);
            this.notifyALL();
         }
        return editou;
    }

    public boolean excluir(Contato contato, int sistemaArmazenamento) {

        boolean excluiu = false;
        
        if (this.getDaos().get(sistemaArmazenamento) != null) {
            excluiu =  this.getDaos().get(sistemaArmazenamento).excluir(contato.getNome());
            this.notifyALL();

        }
        return excluiu;
    }

    public Contato buscar(String nome, int sistemaArmazenamento) {

        return this.getDaos().get(sistemaArmazenamento).buscar(nome);

    }

    public ArrayList<Contato> lista(int sistemaArmazenamento) {
        
        if(this.getDaos().get(sistemaArmazenamento) != null)
          return this.getDaos().get(sistemaArmazenamento).lista();

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

        this.getDaos().add(new ContatoDAO_SGBD());
        this.getDaos().add(new ContatoDAO_XML());
        this.getDaos().add(new ContatoDAO_ARQ());

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
