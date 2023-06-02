/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.filinho.atviii.dao;

import io.github.filinho.atviii.classes.Client;
import io.github.filinho.atviii.classes.Contract;
import io.github.filinho.atviii.db.DataBase;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 */

/*CREATE TABLE `contracts` (
    `id` int NOT NULL AUTO_INCREMENT,
     `essay` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `last_update` date NOT NULL,
    `client_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FOREIGN` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci*/


public class ContractDao extends Dao <Contract>{
    private static ContractDao instance;
    private ContractDao(){}
    
    public static ContractDao getInstance(){
        if(instance == null){
            instance = new ContractDao();
        }
        return instance;
    }
    
    public static final String TABLE_NAME = "contracts";
    
    
    @Override
    protected String getUpdateStatement() {
        var rawStatement = new StringBuilder();
        
        rawStatement.append("UPDATE ");
        rawStatement.append(TABLE_NAME);
        rawStatement.append(" SET ");
        rawStatement.append("essay = ?, ");
        rawStatement.append("last_update = ?, ");
        rawStatement.append("client_id = ? ");
        
        rawStatement.append("WHERE id = ?;");
        
        return rawStatement.toString();
    }

    @Override
    protected String getCreateStatement() {
        var rawStatement = new StringBuilder();
        
        rawStatement.append("INSERT INTO ");
        rawStatement.append(TABLE_NAME);
        rawStatement.append(" (essay");
        rawStatement.append(", last_update");
        rawStatement.append(", client_id) ");
        
        rawStatement.append("VALUES (?,?,?);");
        
        return rawStatement.toString();
    }

    @Override
    protected String getFindByIdStatement(int id) {
        return "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;"; 
    }

    @Override
    protected String getRetrieveAllStatement() {
       return "SELECT * FROM " + TABLE_NAME + ";";
    }

    protected String getRetriveByForeingKeyStatement(int foreignKeyId){
        return "SELECT * FROM "+ TABLE_NAME + " WHERE " + "client_id" +" = " + foreignKeyId+";";
    }
    
    @Override
    protected String getRemoveByIdStatement(int id) {
       return "DELETE FROM "  + TABLE_NAME + " WHERE id = " + id + ";"; 
    }

    @Override
    protected void putData(PreparedStatement s, Contract e) {
        try{
            
            s.setString(1, e.getEssay());
            s.setObject(2,e.getLastupdate().toString(),JDBCType.DATE);
            s.setInt(3, e.getClient().getId().get());
            
            if(e.getId().isPresent()){
                s.setInt(4, e.getId().get());
            }
        }catch(SQLException ex){
            Logger.getLogger(ContractDao.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    protected void putId(PreparedStatement s, int id) {
        try{
           s.setInt(1, id);
       }catch(SQLException ex){
            Logger.getLogger(ContractDao.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    protected Contract getObjectFrom(ResultSet resultSet) {
        Contract c = null;
        try{
            final var id = resultSet.getInt(1);
            var essay = resultSet.getString(2);
            var data =  resultSet.getDate(3).toLocalDate();
            final var client = ClientDao.getInstance().findByid(resultSet.getInt(4)).get();
            
            c = new Contract(id,essay,data,client);
        }catch(SQLException ex){
            Logger.getLogger(ContractDao.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        return c;
    }
    
    protected Contract getObjectFromNoClient(ResultSet resultSet) {
        Contract c = null;
        try{
            final var id = resultSet.getInt(1);
            var essay = resultSet.getString(2);
            var data =  resultSet.getDate(3).toLocalDate();
             Client client = null;
            
            c = new Contract(id,essay,data,client);
        }catch(SQLException ex){
            Logger.getLogger(ContractDao.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        return c;
    }
    
     public Optional <Contract> findByidNoClient(int id) {
              
        final var db = DataBase.getConnection();
        
        Optional <Contract> entity = Optional.empty();
        
        try (var statement = db.prepareStatement(getFindByIdStatement(id), Statement.RETURN_GENERATED_KEYS)) {
            
            putId(statement, id);
            
            System.out.println("SQL: " + statement.toString());
            
            var currentRow = statement.executeQuery();
            if (currentRow.next()) {
                entity = Optional.of(getObjectFromNoClient(currentRow));
            }
            
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        
        return entity;
    }
     
     public ArrayList<Contract> findContractsByClientId(int id){
         ArrayList<Contract> contracts = new ArrayList<Contract>();
         
        
        final var db = DataBase.getConnection();
        
        try (var statement = db.prepareStatement(getRetriveByForeingKeyStatement(id), Statement.RETURN_GENERATED_KEYS)) {
            
            System.out.println("SQL: " + statement.toString());
            var currentRow = statement.executeQuery();
            while (currentRow.next()) {
                contracts.add(getObjectFromNoClient(currentRow));
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }
         
         return contracts;
     }
     
}
