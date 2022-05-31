/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Endereco;
import br.com.senac.entidade.PessoaJuridica;
import static br.com.senac.util.GeradorUtil.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karen.rivarola
 */
public class PessoaJuridicaDaoImplTest {

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaDao pessoaJuridicaDao;
    private Session sessao;

    public PessoaJuridicaDaoImplTest() {
        pessoaJuridicaDao = new PessoaJuridicaDaoImpl();
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarPessoaJuridicaBd();
        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pesJuridica = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();
        assertNotNull(pesJuridica);
    }

//    @Test
    public void testExcluir() {
        System.out.println("Excluir");
        buscarPessoaJuridicaBd();
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.excluir(pessoaJuridica, sessao);

        PessoaJuridica pesJuridicaExc = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();
        assertNull(pesJuridicaExc);
    }

//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        pessoaJuridica = new PessoaJuridica("Empresa" + gerarNome(), gerarLogin() + "gmail.com", gerarCnpj(), "999.999.999.999");
        Endereco endereco = gerarEndereco();
        pessoaJuridica.setEndereco(endereco);
        endereco.setCliente(pessoaJuridica);

        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();
        assertNotNull(pessoaJuridica.getId());
    }

    private Endereco gerarEndereco() {
        Endereco end = new Endereco("Rua dos Araças", "Madri", gerarNumero(3), gerarCidade(), "Santa Catarina", "Casa", gerarCep());
        return end;
    }

//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarPessoaJuridicaBd();
        pessoaJuridica.setNome("PessoaJuridica alterado");
        pessoaJuridica.getEndereco().setLogradouro("Rua Alterada");
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pessoaJuridicaAlt = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();
        assertEquals(pessoaJuridica.getNome(), pessoaJuridicaAlt.getNome());
        assertEquals(pessoaJuridica.getEndereco().getLogradouro(), pessoaJuridicaAlt.getEndereco().getLogradouro());
    }

    public PessoaJuridica buscarPessoaJuridicaBd() {
        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from PessoaJuridica pj ");
        List<PessoaJuridica> pessoaJuridicas = consulta.list();
        sessao.close();
        if (pessoaJuridicas.isEmpty()) {
            testSalvar();
        } else {
            pessoaJuridica = pessoaJuridicas.get(0);
        }
        return pessoaJuridica;
    }

}
