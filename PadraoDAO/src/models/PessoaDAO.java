
package models;

public interface PessoaDAO {
    
    public void salvar(Pessoa dados);
    public void editar(Pessoa dados);
    public void excluir(Pessoa dados);
    public Pessoa buscar(Pessoa dados);
    
}
