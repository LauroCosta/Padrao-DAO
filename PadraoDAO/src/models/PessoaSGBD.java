package models;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PessoaSGBD implements PessoaDAO {

    @Override
    public void salvar(Pessoa p) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO pessoa (nome,telefone,email)VALUES(?,?,?)");

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getTelefone());
            stmt.setString(3, p.getEmail());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("algo de errado não está certo\n");
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    @Override
    public void editar(Pessoa p) {
        
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE pessoa SET nome = ?,telefone = ?, email = ? WHERE nome = ?");
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getTelefone());
            stmt.setString(3, p.getEmail());
          
            stmt.setString(4, p.getNome());

            stmt.executeUpdate();
        } catch (SQLException ex) {


        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    @Override
    public void excluir(Pessoa p) {

        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM pessoa WHERE nome = ?");
            stmt.setString(1, p.getNome());
            stmt.executeUpdate();

        } catch (SQLException ex) {

        } finally {
            Conexao.closeConnection(con, stmt);
        }

    }

    @Override
    public Pessoa buscar(Pessoa p) {

        return null;
    }

}
