/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.filinho.atviii.dao;

import io.github.filinho.atviii.classes.Entity;
import io.github.filinho.atviii.db.DataBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Ivanderlei Filho &lt;imsf@aluno.ifnmg.edu.br&gt;
 * @param <E> entity from the database.
 */
public abstract class Dao<E extends Entity> implements IDao<E> {

    // Functions that generate SQL
    // add the entity
    protected abstract String getUpdateStatement();
    // update the entity

    protected abstract String getCreateStatement();
    // get one entity by the id

    protected abstract String getFindByIdStatement(int id);
    // get all entitys 

    protected abstract String getRetrieveAllStatement();
    // remove entity by the id

    protected abstract String getRemoveByIdStatement(int id);

    protected abstract void putData(PreparedStatement s, E e);

    protected abstract void putId(PreparedStatement s, int id);

    protected abstract E getObjectFrom(ResultSet resultSet);

    private void update(E e) {
        final var db = DataBase.getConnection();

        try (var statement = db.prepareStatement(getUpdateStatement(), Statement.RETURN_GENERATED_KEYS)) {
            putData(statement, e);
            System.out.println("SQL: " + statement.toString());

            final int count = statement.executeUpdate();
            System.out.println("Affected rows: " + count);
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    private Optional<Integer> create(E e) {
        final var db = DataBase.getConnection();
        try (var statement = db.prepareStatement(getCreateStatement(), Statement.RETURN_GENERATED_KEYS)) {
            putData(statement, e);
            System.out.println("SQL: " + statement.toString());

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();

            if (keys.first()) {
                System.out.println("OK");
                e.setId(keys.getInt(1));
            } else {
                System.out.println("No rows affected");
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return e.getId();
    }

    public void deleteById(int id) {
        final var db = DataBase.getConnection();
        try (var statement = db.prepareStatement(getRemoveByIdStatement(id), Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();
            System.out.println(getRemoveByIdStatement(id));
            System.out.println("OK");
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    @Override
    public final Optional<Integer> createOrUpdate(E e) {
        if (e.getId().isPresent()) {
            update(e);
            return e.getId();
        }
        return create(e);
    }

    @Override
    public ArrayList<E> retriveAll() {
        final var entities = new ArrayList<E>();
        final var db = DataBase.getConnection();

        try (var statement = db.prepareStatement(getRetrieveAllStatement(), Statement.RETURN_GENERATED_KEYS)) {

            System.out.println("SQL: " + statement.toString());

            var currentRow = statement.executeQuery();
            while (currentRow.next()) {
                entities.add(getObjectFrom(currentRow));
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return entities;
    }

    @Override
    public Optional<E> findByid(int id) {

        final var db = DataBase.getConnection();

        Optional<E> entity = Optional.empty();

        try (var statement = db.prepareStatement(getFindByIdStatement(id), Statement.RETURN_GENERATED_KEYS)) {

            putId(statement, id);

            System.out.println("SQL: " + statement.toString());

            var currentRow = statement.executeQuery();
            if (currentRow.next()) {
                entity = Optional.of(getObjectFrom(currentRow));
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return entity;
    }

}
