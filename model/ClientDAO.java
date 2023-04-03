package com.exemple.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends  BaseDAO<Client> {
    public ClientDAO() throws SQLException {

        super();
    }

    // mapping objet --> relation
    @Override
    public void save(Client object) throws SQLException {

        String req = "insert into client (nom , telephone) values (? , ?) ;";


        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setString(1 , object.getNom());
        this.preparedStatement.setString(2 , object.getTelepehone());


        this.preparedStatement.execute();

    }

    @Override
    public void update(Client client) throws SQLException {
        String req = "update client set nom = ?, telephone = ? where id_client = ?";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setString(1, client.getNom());
        this.preparedStatement.setString(2, client.getTelepehone());
        this.preparedStatement.setLong(3, client.getId_client());
        this.preparedStatement.execute();
    }

    @Override
    public void delete(Client object) throws SQLException {
        if (this.connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        System.out.println("Deleting client with ID " + object.getId_client());

        String query = "DELETE FROM client WHERE id_client = ?";
        preparedStatement = this.connection.prepareStatement(query);
        this.preparedStatement.setLong(1 , object.getId_client());
        this.preparedStatement.execute();
    }

    @Override
    public Client getOne(Long id) throws SQLException {
        return null;
    }


    // mapping relation --> objet
    @Override
    public List<Client> getAll() throws SQLException{

        List<Client> mylist = new ArrayList<Client>();
        String req = " select * from client" ;


        this.statement = this.connection.createStatement();

       this.resultSet =  this.statement.executeQuery(req);

       while (this.resultSet.next()){

           mylist.add( new Client(this.resultSet.getLong(1) , this.resultSet.getString(2),
                   this.resultSet.getString(3)));


       }

        return mylist;
    }
}
