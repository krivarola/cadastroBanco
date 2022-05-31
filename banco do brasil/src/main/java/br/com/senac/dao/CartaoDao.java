/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author karen.rivarola
 */
public interface CartaoDao extends BaseDao<Cartao, Long>{
    Cartao pesquisarPorNumero(String numero, Session sessao) throws HibernateException;
    
}
