package com.example.esalaf;

import com.exemple.model.credit;
import com.exemple.model.creditDAO;
import com.exemple.model.product;
import com.exemple.model.productDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class showCreditById implements Initializable {

    @FXML
    private TableView<product> mytab2;
    @FXML
    private TableColumn<product, String> produit;
    @FXML
    private TableColumn<product, Float> prix;
    @FXML
    private Label nom;
    @FXML
    private Label tele;
    @FXML
    private Label total;


    private  long id_cli;
    public void setSelecetdClient(String name, String Tele , String id) throws SQLException {
        this.nom.setText(name);
        this.tele.setText(Tele);
        long id_client=Long.parseLong(id);
        id_cli=id_client;
        getProductIDs();
        List<credit> credits = new ArrayList<>(); // create a list of credit objects
// add credit objects to the list

        populateTableData(credits); // call the function with the list of credit objects
        creditDAO credao = null;
        credao = new creditDAO();
        calculateTotal(credao.getRowsByClientId(id_cli));



    }

    public void populateTableData(List<credit> credits) throws SQLException {
        // Get the list of product IDs for the credits
        ObservableList<Long> productIDs = getProductIDs();

        // Create a new list to hold the product data
        ObservableList<product> productData = FXCollections.observableArrayList();
        productDAO prodao = new productDAO();
        // Loop through the list of product IDs and get the corresponding products
        for (Long id : productIDs) {
            // Get the product for the current ID
            product p = prodao.getOne(id);

            // Add the product to the productData list
            productData.add(p);
        }

        // Set the table data to the productData list
        mytab2.setItems(productData);

        //add quantity column
        creditDAO credao = null;
        credao = new creditDAO();
        List<credit> credi=  credao.getRowsByClientId(id_cli);
        ObservableList<Integer> creditData = FXCollections.observableArrayList();
        ObservableList<Timestamp> creditDate = FXCollections.observableArrayList();
        System.out.println( "size of the credits list " +credi.size());
        if (!credi.isEmpty()){
            for (credit credit : credi) {

                int cred = credit.getQuantity();
                Timestamp date = credit.getCredit_date();
                System.out.println("date :" +date);
                creditData.add(cred);
                creditDate.add(date);
            }


        }// Create a new column for the credit data
        TableColumn<product, Integer> creditDataCol = new TableColumn<>("Quantité");
        TableColumn<product, Timestamp> creditDateCol = new TableColumn<>("Date");

// Set the cell value factory for the credit data column
        creditDataCol.setCellValueFactory(cellData -> {
            product product = cellData.getValue();
            int index = mytab2.getItems().indexOf(product);
            if (index >= 0 && index < creditData.size()) {
                return new SimpleObjectProperty<>(creditData.get(index));
            } else {
                return new SimpleObjectProperty<>(null);
            }
        });
        // Set the cell value factory for the credit data column
        creditDateCol.setCellValueFactory(cellData -> {
            product product = cellData.getValue();
            int index = mytab2.getItems().indexOf(product);
            if (index >= 0 && index < creditDate.size()) {
                return new SimpleObjectProperty<>(creditDate.get(index));
            } else {
                return new SimpleObjectProperty<>(null);
            }
        });

// Add the credit data column to the table
        mytab2.getColumns().add(creditDataCol);
        mytab2.getColumns().add(creditDateCol);
//add delete button

        // Set the column value factories to the appropriate properties of the product class
        produit.setCellValueFactory(new PropertyValueFactory<product, String>("name"));
        prix.setCellValueFactory(new PropertyValueFactory<product, Float>("price"));

    }
    public  ObservableList<Long> getProductIDs(){

        creditDAO credao = null;

        ObservableList<Long> listfx = FXCollections.observableArrayList();

        try {
            credao = new creditDAO();
            for(Long ettemp : credao.getProductIdsByClientId(id_cli))
                listfx.add(ettemp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;

    }


    public void calculateTotal(List<credit> credits) throws SQLException {


        float totalPrice = 0;
        for (credit credit : credits) {
            productDAO prodao = new productDAO();
            long id=credit.getProduit_id();
            product p=prodao.getOne(id);
            totalPrice += credit.getQuantity() * p.getPrice();

        }
        total.setText(String.format("%.2f", totalPrice) +"DH");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
