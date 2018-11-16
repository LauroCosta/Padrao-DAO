package DAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Contato;

public class ContatoDAO_ARQ implements ContatoDAO {

   private final String CAMINHO = "src\\arquivosDados\\contatos.txt";

    @Override
    public boolean salvar(Contato dados) {

        try {

            FileWriter arq = new FileWriter(this.getCAMINHO(), true);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(dados.getNome() + ":" + dados.getTelefone() + ":" + dados.getEmail());
            arq.close();

        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean editar(Contato contato) {
        try {
            FileReader arq = new FileReader(this.getCAMINHO());
            BufferedReader lerArq = new BufferedReader(arq);
            String novo = "";
            String linha = "";

            linha = lerArq.readLine();
            while (linha != null) {

                if (this.getContato(linha).getNome().equals(contato.getNome())) {

                    novo += contato.getNome() + ":" + contato.getTelefone() + ":" + contato.getEmail() + "\n";
                    linha = lerArq.readLine();
                } else {
                    novo += linha + "\n";
                    linha = lerArq.readLine();
                }

            }
            this.salvarTexto(novo);
            arq.close();

        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean excluir(String nome) {

        try {
            FileReader arq = new FileReader(this.getCAMINHO());
            BufferedReader lerArq = new BufferedReader(arq);
            String novo = "";
            String linha = "";

            linha = lerArq.readLine();
            while (linha != null) {

                if (this.getContato(linha).getNome().equals(nome)) {
                    linha = lerArq.readLine();
                } else {
                    novo += linha + "\n";
                    linha = lerArq.readLine();
                }

            }
            this.salvarTexto(novo);
            arq.close();

        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    @Override
    public Contato buscar(String nome) {

        try {
            FileReader arq = new FileReader(this.getCAMINHO());
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";

            linha = lerArq.readLine();
            while (linha != null) {

                if (this.getContato(linha).getNome().equals(nome)) {
                    return this.getContato(linha);
                }

                linha = lerArq.readLine();

            }

            arq.close();

        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Contato> lista() {

        ArrayList<Contato> contatos = new ArrayList<>();

        try {
            FileReader arq = new FileReader(this.getCAMINHO());
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";

            linha = lerArq.readLine();
            while (linha != null) {

                contatos.add(this.getContato(linha));
                linha = lerArq.readLine();
            }
            arq.close();
            return contatos;

        } catch (FileNotFoundException ex) {
            System.out.println("Erro: Arquivo não encontrado!");
            return null;
        } catch (IOException ex) {
            return null;
        }

    }

    private String getCAMINHO() {
        return CAMINHO;
    }

    private boolean salvarTexto(String texto) {

        try {

            FileWriter arq = new FileWriter(this.getCAMINHO());
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.print(texto);
            arq.close();

        } catch (IOException ex) {

            return false;
        }

        return true;
    }

    private Contato getContato(String linha) {

        String[] dados = linha.split(":");

        Contato contato = new Contato();
        contato.setNome(dados[0]);
        contato.setTelefone(dados[1]);
        contato.setEmail(dados[2]);

        return contato;
    }
}
