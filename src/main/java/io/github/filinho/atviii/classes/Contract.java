/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.filinho.atviii.classes;

import java.time.LocalDate;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 */
public class Contract extends Entity{
        private String essay;
        private LocalDate lastupdate;
        private Client client;

    public Contract( Integer id,String essay, LocalDate lastupdate, Client client) {
        super(id);
        this.essay = essay;
        this.lastupdate = lastupdate;
        this.client = client;
    }

    public String getEssay() {
        return essay;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public LocalDate getLastupdate() {
        return lastupdate;
    }

    public Client getClient() {
        return client;
    }
    
    public void  updateContract(String newEssay){
        this.essay = newEssay;
        this.lastupdate = LocalDate.now();
    }
    
    public String getIdAsString(){
        return Integer.toString(this.getId().get());
    }

    @Override
    public String toString() {
        return "Contracts{" + "essay: " + essay + ", lastupdate " + lastupdate +" client id: "+ client.getId().get() + '}';
    }
    

     
}
        

