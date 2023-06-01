/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.filinho.atviii.classes;

import java.util.Optional;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 */
public abstract class Entity {
    // primary key
    
    private Optional<Integer> id;

    protected Entity(Integer id){
        this.id = Optional.ofNullable(id);
    }
    
    public Optional <Integer> getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = Optional.ofNullable(id);
    }
  
}
