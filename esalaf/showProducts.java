package com.example.esalaf;

import com.exemple.model.product;
import com.exemple.model.productDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class showProducts implements Initializable {


    @FXML
    private TextField prod_nom;

    @FXML
    private TextField prod_desc;

    @FXML
    private TextField prod_prix;
    @FXML
    private TableView<product> prodTab;
    @FXML
    private TableColumn<product, Integer> col_prod_id;
    @FXML
    private TableColumn<product, String> col_prod_nom;

    @FXML
    private TableColumn<product, String> col_prod_desc;

    @FXML
    private TableColumn<product, Float> col_prod_prix;
    @FXML
    private TableColumn<product, Void> col_delete;
    @FXML
    private TableColumn<product, Void> col_update;
    @FXML
    protected void onSaveClick(){

        product prod = new product(0l , prod_nom.getText() , prod_desc.getText(), prod_prix.getText());

        try {
            productDAO prodao = new productDAO();

            prodao.save(prod);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UpdateTable();

    }

    private void editClient(product prod) {
        // Create a new dialog box to edit the client details
        Dialog<product> dialog = new Dialog<>();
        dialog.setTitle("Edit Product");

        // Set the button types for the dialog box
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create a form with text fields for the client details
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        Float prix=prod.getPrice();

        TextField nom = new TextField(prod.getName());
        TextField desc = new TextField(prod.getDescription());
        TextField price = new TextField(Float.toString(prix));

        gridPane.add(new Label("Nom:"), 0, 0);
        gridPane.add(nom, 1, 0);
        gridPane.add(new Label("Description:"), 0, 1);
        gridPane.add(desc, 1, 1);
        gridPane.add(new Label("price:"), 1, 2);
        gridPane.add(price, 2, 2);
        dialog.getDialogPane().setContent(gridPane);

        // Set the result converter for the dialog box
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new product(prod.getProd_id(), nom.getText(), desc.getText(),price.getText() );
            }
            return null;
        });

        // Show the dialog box and wait for the user to close it
        Optional<product> result = dialog.showAndWait();

        // If the user clicked save, update the client details in the database and refresh the table
        result.ifPresent(updatedProduct -> {
            try {
                productDAO prodao = new productDAO();
                prodao.update(updatedProduct);
                UpdateTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void UpdateTable() {
        col_prod_id.setCellValueFactory(new PropertyValueFactory<product, Integer>("prod_id"));
        col_prod_nom.setCellValueFactory(new PropertyValueFactory<product, String>("name"));

        col_prod_desc.setCellValueFactory(new PropertyValueFactory<product, String>("description"));
        col_prod_prix.setCellValueFactory(new PropertyValueFactory<product, Float>("price"));

        //add delete button
        col_delete.setCellFactory(col -> {
            TableCell<product, Void> cell = new TableCell<product, Void>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        product prod = getTableRow().getItem();
                        try {
                            productDAO proDAO = new productDAO();
                            proDAO.delete(prod);
                            UpdateTable();
                            Alert alert =new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Delete");
                            alert.setHeaderText("you deleted this product");
                            alert.setContentText("deleted succesfully!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setGraphic(deleteButton);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });

        //add update button
        col_update.setCellFactory(col -> {
            TableCell<product, Void> cell = new TableCell<product, Void>() {
                private final Button updateButton = new Button("update");

                {
                    updateButton.setOnAction(event -> {
                        product prod = getTableRow().getItem();

                        if (prod != null) {

                            editClient(prod);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setGraphic(updateButton);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });

        prodTab.setItems(getDataProducts());
    }

    public static ObservableList<product> getDataProducts(){

        productDAO prodao = null;

        ObservableList<product> listfx = FXCollections.observableArrayList();

        try {
            prodao = new productDAO();
            for(product ettemp : prodao.getAll())
                listfx.add(ettemp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTable();
    }
}
