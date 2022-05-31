/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.PessoaFisica;
import br.com.senac.entidade.Profissao;
import com.github.javafaker.Faker;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karen.rivarola
 */
public class ProfissaoDaoImplTest {

    private Profissao profissao;
    private PessoaFisica pessoaFisica;
    private ProfissaoDao profissaoDao;
    private Session sessao;

    public ProfissaoDaoImplTest() {
        profissaoDao = new ProfissaoDaoImpl();
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();
        Profissao profi = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertNotNull(profi);

    }

//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        Faker faker = new Faker();
        profissao = new Profissao(faker.job().title(), faker.lorem().sentence());
        sessao = HibernateUtil.abrirConexao();
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();
        assertNotNull(profissao.getId());
    }

//    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();

        List<Profissao> profissoes = profissaoDao.pesquisarPorNome(profissao.getNome(), sessao);
        sessao.close();
        assertTrue(!profissoes.isEmpty());
    }

    @Test
    public void testAlterar() {
        System.out.println("alterar");
        Faker faker = new Faker();
        buscarProfissaoBd();
        profissao.setNome(faker.job().title());
        sessao = HibernateUtil.abrirConexao();
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        Profissao profissaoAlt = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertEquals(profissao.getNome(), profissaoAlt.getNome());
    }

    public Profissao buscarProfissaoBd() {
        sessao = HibernateUtil.abrirConexao();
        Query<Profissao> consulta = sessao.createQuery("from Profissao p");
        List<Profissao> profissoes = consulta.getResultList();
        sessao.close();
        if (profissoes.isEmpty()) {
            testSalvar();
        } else {
            profissao = profissoes.get(0);
        }

        return profissao;
    }

}
