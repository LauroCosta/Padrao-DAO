package DAO;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import models.*;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContatoSGBD implements ContatoDAO {

    @Override
    public boolean salvar(Contato contato) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO contato (nome,telefone,email)VALUES(?,?,?)");

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            return false;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return true;
    }

    @Override
    public boolean editar(Contato p) {

        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE contato SET nome = ?,telefone = ?, email = ? WHERE nome = ?");
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getTelefone());
            stmt.setString(3, p.getEmail());

            stmt.setString(4, p.getNome());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            return false;
        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return true;
    }

    @Override
    public boolean excluir(String nome) {

        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM contato WHERE nome = ?");
            stmt.setString(1, nome);
            stmt.executeUpdate();

        } catch (SQLException ex) {

            return false;

        } finally {
            Conexao.closeConnection(con, stmt);
        }
        return true;
    }

    public Contato buscar(String nome) {

        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Contato c1 = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM contato WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            if (rs.next()) {

                c1 = new Contato();

                c1.setNome(rs.getString("nome"));
                c1.setTelefone(rs.getString("telefone"));
                c1.setEmail(rs.getString("email"));

            }

        } catch (SQLException ex) {

            return null;

        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return c1;

    }

    public ArrayList<Contato> lista() {

        Connection con = Conexao.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<Contato> contatos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM contato");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Contato c1 = new Contato();

                c1.setNome(rs.getString("nome"));
                c1.setTelefone(rs.getString("telefone"));
                c1.setEmail(rs.getString("email"));

                contatos.add(c1);
            }

        } catch (SQLException ex) {

            return null;
        
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return contatos;
    }

}
