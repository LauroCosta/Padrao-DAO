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
    

    public void salvar(Contato contato){
  
        for (ContatoDAO dao : daos) 
            dao.salvar(contato);
        
    }
    
    public void editar(Contato contato){
  
        for (ContatoDAO dao : daos) 
            dao.editar(contato);
        
        this.notifyALL();
        
    }
    
    public void excluir(Contato contato){
  
        for (ContatoDAO dao : daos) 
            dao.excluir(contato.getNome());
        
        this.notifyALL();
        
    }
    
    public Contato buscar(String nome, int sistemaDados){
  
       return this.getDaos().get(sistemaDados).buscar(nome);
    
    }
    
    public ArrayList<Contato> buscar(int sistemaDados){
        
       return this.getDaos().get(sistemaDados).lista();
    
    }
   
    public void addObserver(Observer ob){
        this.getObservers().add(ob);
    }
 
    private void notifyALL(){
 
        for (Observer observer : observers) 
           observer.update();

    }
    
    private void criarDAOS(){
      
        this.getDaos().add(new ContatoSGBD());
        this.getDaos().add(new ContatoXML());
        this.getDaos().add(new ContatoARQ());
  
    }
  
    private ArrayList<ContatoDAO> getDaos() {
        return daos;
    }

    private void setDaos(ArrayList<ContatoDAO> daos) {
        if(daos != null)
            this.daos = daos;
    }

    private ArrayList<Observer> getObservers() {
        return observers;
    }

    private void setObservers(ArrayList<Observer> observers) {
        if(observers != null)
            this.observers = observers;
    }
  
}
