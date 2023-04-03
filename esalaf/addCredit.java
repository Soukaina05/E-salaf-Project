package com.example.esalaf;

import com.exemple.model.credit;
import com.exemple.model.creditDAO;
import com.exemple.model.product;
import com.exemple.model.productDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
public class addCredit implements Initializable {
    @FXML
    private TextField Nom;

    @FXML
    private TextField Tele;
    @FXML
    private TextField quantity;
    @FXML
    private Label id_cli;
@FXML
private Button save;

private long product_id;
private long client_id;

    public void setSelecetdClientName(String name, String Tele , String id) {
    this.Nom.setText(name);
    this.Tele.setText(Tele);
    this.id_cli.setText(id);
    long id_cli=Long.parseLong(id);
    client_id=id_cli;


}
    @FXML private MenuButton produit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            productDAO productDAO = new productDAO();
            List<product> productList = productDAO.getAll();
            addProductsToMenu(productList);
        } catch (SQLException ex) {
            // Handle SQL exception
        }
    }

    private void addProductsToMenu(List<product> productList) {
        for (product product : productList) {
            MenuItem menuItem = new MenuItem(product.getName());




            // Set the product id as the id property of the MenuItem
            menuItem.setId(String.valueOf(product.getProd_id()));
            menuItem.setOnAction(event -> {
                // Get the selected product name
                String selectedProduct = ((MenuItem) event.getSource()).getText();
                // Set the text of the MenuButton to the selected product name
                produit.setText(selectedProduct);

                // Get the selected product id
                String selectedProductId = ((MenuItem) event.getSource()).getId();
                // Call a method with the selected product id
                handleProductSelection(selectedProductId);
            });
            produit.getItems().add(menuItem);
        }
    }
    private void handleProductSelection(String productId) {
        // Do something with the selected product id
        System.out.println("Selected product id: " + productId);
        long id_prod=Long.parseLong(productId);
        product_id=id_prod;
    }
public void onSave(){
    // Get the quantity from the text field
LocalDate date= LocalDate.now();
    LocalDateTime currentDate =date.atStartOfDay();
    Timestamp currentTime=Timestamp.valueOf(currentDate);

    int quantite = Integer.parseInt(quantity.getText());
    credit cred = new credit(0l ,  quantite,currentTime, client_id,product_id );

    try {
        creditDAO creditdao = new creditDAO();

        creditdao.save(cred);

       // Close the current interface
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }


}
}



