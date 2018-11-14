package DAO;

import java.util.ArrayList;
import models.Contato;

public class ContatoXML implements ContatoDAO {

    @Override
    public boolean salvar(Contato dados) {

        return false;
    }

    @Override
    public boolean editar(Contato nome) {
        return false;
    }

    @Override
    public boolean excluir(String nome) {
        return false;
    }

    @Override
    public Contato buscar(String nome) {
        return null;
    }

    @Override
    public ArrayList<Contato> lista() {
        return null;
    }

}
