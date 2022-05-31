/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Endereco;
import br.com.senac.entidade.PessoaFisica;
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
public class PessoaFisicaDaoImplTest {

    private PessoaFisica pessoaFisica;
    private PessoaFisicaDao pessoaFisicaDao;
    private Session sessao;

    public PessoaFisicaDaoImplTest() {
        pessoaFisicaDao = new PessoaFisicaDaoImpl();
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarPessoaFisicaBd();
        sessao = HibernateUtil.abrirConexao();
        PessoaFisica pesFisica = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();
        assertNotNull(pesFisica);
    }

//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        ProfissaoDaoImplTest pdit = new ProfissaoDaoImplTest();
        
        pessoaFisica = new PessoaFisica(gerarNome(), gerarLogin(), gerarCpf(), gerarRg());
        Endereco endereco = gerarEndereco();
        pessoaFisica.setEndereco(endereco);
        endereco.setCliente(pessoaFisica);
        pessoaFisica.setProfissao(pdit.buscarProfissaoBd());
        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, sessao);
        sessao.close();
        assertNotNull(pessoaFisica.getId());
    }
    
    private Endereco gerarEndereco(){
        Endereco end = new Endereco("Rua dos Araças", "Centro", gerarNumero(3), gerarCidade(), "Santa Catarina", "Casa", gerarCep());
        return end;
    }

//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarPessoaFisicaBd();
        pessoaFisica.setNome(gerarNome());
        pessoaFisica.setCpf(gerarCpf());
        pessoaFisica.getEndereco().setLogradouro("Rua Alterada");
        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        PessoaFisica pesFisicaAlt = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();
        assertEquals(pessoaFisica.getNome(), pesFisicaAlt.getNome());
        assertEquals(pessoaFisica.getCpf(), pesFisicaAlt.getCpf());
        assertEquals(pessoaFisica.getEndereco().getLogradouro(), pesFisicaAlt.getEndereco().getLogradouro());
    }

//    @Test
    public void testExcluir() {
        System.out.println("Excluir");
        buscarPessoaFisicaBd();
        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.excluir(pessoaFisica, sessao);

        PessoaFisica pesFisicaExc = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();
        assertNull(pesFisicaExc);
    }

//    @Test
    public void testPesquisarPorNome() {
        System.out.println("PesquisarPorNome");
        buscarPessoaFisicaBd();
        sessao = HibernateUtil.abrirConexao();

        List<PessoaFisica> fisicas = pessoaFisicaDao.pesquisarPorNome(pessoaFisica.getNome(), sessao);
        sessao.close();
        assertTrue(!fisicas.isEmpty());
    }

    public PessoaFisica buscarPessoaFisicaBd() {
        String hql = "from PessoaFisica pf ";
        sessao = HibernateUtil.abrirConexao();
        Query<PessoaFisica> consulta = sessao.createQuery(hql);
        List<PessoaFisica> pessoaFisicas = consulta.getResultList();
        sessao.close();
        if (pessoaFisicas.isEmpty()) {
            testSalvar();
        } else {
            pessoaFisica = pessoaFisicas.get(0);
        }
        return pessoaFisica;
    }

}
