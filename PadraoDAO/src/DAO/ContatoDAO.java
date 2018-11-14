package DAO;

import models.*;
import java.util.ArrayList;

public interface ContatoDAO {
    
    public boolean salvar(Contato dados);
    public boolean editar(Contato nome);
    public boolean excluir(String nome);
    public Contato buscar(String nome);
    public ArrayList<Contato> lista();
    
}
