/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.filinho.atviii.dao;

import io.github.filinho.atviii.classes.Client;
import io.github.filinho.atviii.classes.Contract;
import io.github.filinho.atviii.tools.Cpf;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 */

/*CREATE TABLE `client` (
    `id` int NOT NULL AUTO_INCREMENT,
     `cpf` bigint NOT NULL,
    `name` varchar(45) NOT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci */


public class ClientDao extends Dao<Client> {

    private static ClientDao instance;

    private ClientDao() {
    }

    public static ClientDao getInstance() {
        if (instance == null) {
            instance = new ClientDao();
        }
        return instance;
    }

    public static final String TABLE_NAME = "client";

    @Override
    protected String getUpdateStatement() {
        var rawStatement = new StringBuilder();

        rawStatement.append("UPDATE ");
        rawStatement.append(TABLE_NAME);
        rawStatement.append(" SET ");
        rawStatement.append("cpf = ?, ");
        rawStatement.append("name = ? ");

        rawStatement.append("WHERE id = ?;");

        return rawStatement.toString();
    }

    @Override
    protected String getCreateStatement() {
        var rawStatement = new StringBuilder();

        rawStatement.append("INSERT INTO ");
        rawStatement.append(TABLE_NAME);
        rawStatement.append(" (cpf");
        rawStatement.append(", name) ");
        

        rawStatement.append("VALUES (?,?);");

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

    @Override
    protected String getRemoveByIdStatement(int id) {
        return "DELETE FROM "  + TABLE_NAME + " WHERE id = " + id; 
    }

    @Override
    protected void putData(PreparedStatement s, Client e) {
        try {

            s.setLong(1, e.getCpf().getAsLong());
            s.setString(2, e.getName());

            if (e.getId().isPresent()) {
                s.setInt(3, e.getId().get());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void putId(PreparedStatement s, int id) {
        try {
            s.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Client getObjectFrom(ResultSet resultSet) {
        Client c = null;
        try {
            final var id = resultSet.getInt(1);
            final var cpf = new Cpf(resultSet.getLong(2));
            final var name = resultSet.getString(3);
            final var contracts = ContractDao.getInstance().findContractsByClientId(id);
            c = new Client(id, cpf, name, contracts);
            c.updateContractsClient();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
