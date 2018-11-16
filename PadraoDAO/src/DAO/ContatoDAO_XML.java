package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.SAXException;

import model.Contato;

public class ContatoDAO_XML implements ContatoDAO {

    private final String CAMINHO = "src\\arquivosDados\\contatos.xml";

    @Override
    public boolean salvar(Contato dados) {

        try {

            Document d = this.getDocumento(this.getCAMINHO());
            Element contatos = d.getDocumentElement();
            Element contato = d.createElement("contato");

            Element nome = d.createElement("nome");
            nome.appendChild(d.createTextNode(dados.getNome()));
            contato.appendChild(nome);

            Element telefone = d.createElement("telefone");
            telefone.appendChild(d.createTextNode(dados.getTelefone()));
            contato.appendChild(telefone);

            Element email = d.createElement("email");
            email.appendChild(d.createTextNode(dados.getEmail()));
            contato.appendChild(email);

            contatos.appendChild(contato);

            this.setXMLDados(d, this.getCAMINHO());

        } catch (Exception e) {

            return false;
        }

        return true;
    }

    @Override
    public boolean editar(Contato dados) {

        try {

            Document d = this.getDocumento(this.getCAMINHO());
            NodeList n1 = d.getElementsByTagName("contato");
            for (int i = 0; i < n1.getLength(); i++) {
                Element contato = (Element) n1.item(i);
                if (contato.getElementsByTagName("nome").item(0).getTextContent().equals(dados.getNome())) {

                    contato.getElementsByTagName("telefone").item(0).setTextContent(dados.getTelefone());
                    contato.getElementsByTagName("email").item(0).setTextContent(dados.getEmail());

                }
            }

            this.setXMLDados(d, this.getCAMINHO());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean excluir(String nome) {

        try {

            Document d = this.getDocumento(this.getCAMINHO());
            NodeList n1 = d.getElementsByTagName("contato");
            for (int i = 0; i < n1.getLength(); i++) {
                Element contato = (Element) n1.item(i);
                if (contato.getElementsByTagName("nome").item(0).getTextContent().equals(nome)) {

                    contato.getParentNode().removeChild(contato);
                }
            }

            this.setXMLDados(d, this.getCAMINHO());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Contato buscar(String nome) {

        Contato c1 = null;

        try {

            Document d = this.getDocumento(this.getCAMINHO());
            NodeList n1 = d.getElementsByTagName("contato");
            for (int i = 0; i < n1.getLength(); i++) {
                Element contato = (Element) n1.item(i);
                if (contato.getElementsByTagName("nome").item(0).getTextContent().equals(nome)) {

                    c1 = new Contato();

                    c1.setNome(contato.getElementsByTagName("nome").item(0).getTextContent());
                    c1.setTelefone(contato.getElementsByTagName("telefone").item(0).getTextContent());
                    c1.setEmail(contato.getElementsByTagName("email").item(0).getTextContent());

                    return c1;
                }
            }

            this.setXMLDados(d, this.getCAMINHO());
        } catch (Exception e) {
            return null;
        }
        return c1;
    }

    @Override
    public ArrayList<Contato> lista() {

        ArrayList<Contato> contatos = new ArrayList<>();

        Document d = this.getDocumento(this.getCAMINHO());
        NodeList n1 = d.getElementsByTagName("contato");
        for (int i = 0; i < n1.getLength(); i++) {
            Element contato = (Element) n1.item(i);

            Contato c1 = new Contato();

            c1.setNome(contato.getElementsByTagName("nome").item(0).getTextContent());
            c1.setTelefone(contato.getElementsByTagName("telefone").item(0).getTextContent());
            c1.setEmail(contato.getElementsByTagName("email").item(0).getTextContent());

            contatos.add(c1);
        }

        return contatos;
    }

    private Document getDocumento(String caminho) {

        Document d = null;

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            d = db.parse(caminho);

        } catch (SAXException ex) {
            Logger.getLogger(ContatoDAO_XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContatoDAO_XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ContatoDAO_XML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return d;
    }

    private String getXMLDados(Document d) {
        String result = "";
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();

            tf.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            DOMSource source = new DOMSource(d);

            tf.transform(source, sr);
            result = sw.toString();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ContatoDAO_XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ContatoDAO_XML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private void setXMLDados(Document d, String caminho) {

        try {

            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource ds = new DOMSource(d);
            StreamResult sr = new StreamResult(caminho);
            tf.transform(ds, sr);

        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ContatoDAO_XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ContatoDAO_XML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String getCAMINHO() {
        return CAMINHO;
    }

}
