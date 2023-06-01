/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package io.github.filinho.atviii.poo;

import io.github.filinho.atviii.classes.Client;
import io.github.filinho.atviii.dao.ClientDao;
import io.github.filinho.atviii.tools.Cpf;
import io.github.filinho.atviii.classes.Contract;
import io.github.filinho.atviii.dao.ContractDao;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Contract> contracts = new ArrayList<>();
        Cpf cpf = new Cpf(23456789104l);
        
        Client client = new Client(null, cpf,"Maicon Pedro ", contracts);
        
        Contract contract = new Contract(null, "Contrato de Assinatura",LocalDate.now(),client);
        
        
        client.addNewContract(contract);
        
        
        // 1 - inclusão de entidade cliente
        System.out.println("1 - inclusão de entidade cliente");
        ClientDao.getInstance().createOrUpdate(client);
        
        // 6 - inclusão de entidade contrato
        System.out.println("6 - inclusão de entidade contrato");
        ContractDao.getInstance().createOrUpdate(contract);
        
        // 2- atualização de entidade cliente
        System.out.println("2 - atualização de entidade cliente");
        client.setName("Maicon Pedro Macedo");
        ClientDao.getInstance().createOrUpdate(client);
        
        // 7 - atualização de entidade contrato
        System.out.println("7 - atualização da entidade contrato");
        contract.updateContract("Contrato vitalicio");
        ContractDao.getInstance().createOrUpdate(contract);
        
        // 3 - consulta de entidade cliente especifica
        System.out.println("3 - consulta de entidade cliente especifica");
        Client client1 = ClientDao.getInstance().findByid(1).get();
        System.out.println(client1.toString());
        
        // 8 - consulta de entidade Cliente Especifica 
        System.out.println("8 - consulta por entidade contrato especifica");
        Contract contract1 =  ContractDao.getInstance().findByid(1).get();
        System.out.println(contract1.toString());
         
        //4 - consulta por todas entidadades clientes
        System.out.println("4 - consulta por todas entidades clientes");
        ArrayList <Client> clients = ClientDao.getInstance().retriveAll();
        for(Client c: clients){
            System.out.println(c.toString());
        }   
        
        //9 - Consulta por todas as entidades contratos
        System.out.println("9 - consulta por todas as entidades contratos");
        ArrayList <Contract> contracts1 = ContractDao.getInstance().retriveAll();
        for(Contract c: contracts1){
            System.out.println(c.toString());
        }
        
       
         //5- remoção de entidade cliente especifica
        System.out.println("5 - remoção de entidade cliente especifica");
        ClientDao.getInstance().deleteById(client.getId().get());
        
        //10 - remoção de entidade contrato especifica
        System.out.println("10 - remoção de entidade contrato especifica");
        ContractDao.getInstance().deleteById(contract.getId().get());
        
        
    }
}
