/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package io.github.filinho.atviii.dao;

import io.github.filinho.atviii.classes.Entity;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 * @param <E> entity from the database.
 */
interface IDao <E extends Entity> {
    
    Optional <Integer> createOrUpdate(E e);
    
    List<E> retriveAll();
    
    Optional <E> findByid(int id);
    
}
