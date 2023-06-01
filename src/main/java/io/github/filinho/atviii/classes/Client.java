/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.filinho.atviii.classes;

import io.github.filinho.atviii.tools.Cpf;
import java.util.ArrayList;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 */
public class Client extends Entity {

    private final Cpf cpf;
    private  String name;

    public void setName(String name) {
        this.name = name;
    }
    private ArrayList<Contract> contracts;

    public Client(Integer id, Cpf cpf, String name, ArrayList<Contract> contracts) {
        super(id);
        this.cpf = cpf;
        this.name = name;
        this.contracts = contracts;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public String getName() {
        return name;
    }
    
    public void addNewContract(Contract c){
        contracts.add(c);
    }
    
    public void updateContractsClient(){
        for(Contract contract : contracts){
            contract.setClient(this);
        }
        
     
    }

    @Override
    public String toString() {
        return "Client{" + "cpf: " + cpf.getAsLong() + ", name: " + name  + contracts.toString()+ "}";
    }
    
}