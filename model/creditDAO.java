package com.exemple.model;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class creditDAO extends  BaseDAO<credit> {
    public creditDAO() throws SQLException {

        super();
    }
    public List<Long> getProductIdsByClientId(Long clientId) throws SQLException {
        if (this.connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        List<Long> productIds = new ArrayList<>();


            preparedStatement = this.connection.prepareStatement("SELECT * FROM credits WHERE client_id = ?");
            preparedStatement.setLong(1, clientId);
            this.resultSet = this.preparedStatement.executeQuery();

            while (this.resultSet.next()) {
                Long productId = this.resultSet.getLong("product_id");
                productIds.add(productId);
            }


        return productIds;
    }

    public List<credit> getRowsByClientId(Long clientId) throws SQLException {
        if (this.connection.isClosed()) {
            throw new SQLException("Connection is closed");
        }

        List<credit> mylist = new ArrayList<credit>();


        preparedStatement = this.connection.prepareStatement("SELECT * FROM credits WHERE client_id = ?");
        preparedStatement.setLong(1, clientId);
        this.resultSet = this.preparedStatement.executeQuery();

        while (this.resultSet.next()){

            mylist.add( new credit(
                    this.resultSet.getLong(1) ,
                    this.resultSet.getInt(2),
                    this.resultSet.getLong(4),
                    this.resultSet.getInt(5)


            ));


        }

        return mylist;
    }
    @Override
    public void save(credit object) throws SQLException {

        String req = "insert into credits ( quantity,client_id ,  product_id) values (? , ?, ?) ;";


        this.preparedStatement = this.connection.prepareStatement(req);
        if((object.getQuantity())!=0) {
            this.preparedStatement.setInt(1, object.getQuantity());
        }else{
            this.preparedStatement.setInt(1, 1);

        }
        this.preparedStatement.setLong(2, object.getClient_id());

        this.preparedStatement.setLong(3, object.getProduit_id());




        this.preparedStatement.execute();
        System.out.println("credit saved to data base ");
    }

    @Override
    public void update(credit object) throws SQLException {

    }

    @Override
    public void delete(credit object) throws SQLException {

    }

    @Override
    public credit getOne(Long id) throws SQLException {
        return null;
    }

    @Override
    public List<credit> getAll() throws SQLException{

        List<credit> mylist = new ArrayList<credit>();
        String req = " select * from credits" ;


        this.statement = this.connection.createStatement();

        this.resultSet =  this.statement.executeQuery(req);

        while (this.resultSet.next()){

            mylist.add( new credit(
                    this.resultSet.getLong(1) ,
                    this.resultSet.getInt(2),
                    this.resultSet.getLong(4),
                    this.resultSet.getInt(5)


                    ));


        }

        return mylist;
    }
}
