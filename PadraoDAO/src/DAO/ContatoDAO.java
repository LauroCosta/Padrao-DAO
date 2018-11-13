package DAO;

import models.*;
import java.util.ArrayList;

public interface ContatoDAO {
    
    public void salvar(Contato dados);
    public void editar(Contato nome);
    public void excluir(String nome);
    public Contato buscar(String nome);
    public ArrayList<Contato> lista();
    
}
