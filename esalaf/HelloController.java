package com.example.esalaf;

import com.exemple.model.Client;
import com.exemple.model.ClientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField nom;

    @FXML
    private TextField tele;


    @FXML
    private TableView<Client> mytab;

    @FXML
    private TableColumn<Client, Long> col_id;

    @FXML
    private TableColumn<Client, String> col_nom;

    @FXML
    private TableColumn<Client, String> col_tele;

    @FXML
    private TableColumn<Client, Void> col_del;

    @FXML
    private TableColumn<Client, Void> col_update;
    @FXML
    private TableColumn<Client, Void> col_new;

    @FXML
    private TableColumn<Client, Void> col_check;

    @FXML
    protected void onSaveButtonClick(){

        Client cli = new Client(0l , nom.getText() , tele.getText());

        try {
            ClientDAO clidao = new ClientDAO();

            clidao.save(cli);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UpdateTable();

    }

    private void editClient(Client client) {
        // Create a new dialog box to edit the client details
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Edit Client");

        // Set the button types for the dialog box
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create a form with text fields for the client details
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField nom = new TextField(client.getNom());
        TextField tele = new TextField(client.getTelepehone());

        gridPane.add(new Label("Nom:"), 0, 0);
        gridPane.add(nom, 1, 0);
        gridPane.add(new Label("Téléphone:"), 0, 1);
        gridPane.add(tele, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Set the result converter for the dialog box
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Client(client.getId_client(), nom.getText(), tele.getText());
            }
            return null;
        });

        // Show the dialog box and wait for the user to close it
        Optional<Client> result = dialog.showAndWait();

        // If the user clicked save, update the client details in the database and refresh the table
        result.ifPresent(updatedClient -> {
            try {
                ClientDAO clidao = new ClientDAO();
                clidao.update(updatedClient);
                UpdateTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }




// Add an event listener to the table cells

    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Client,Long>("id_client"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));

        col_tele.setCellValueFactory(new PropertyValueFactory<Client,String>("telepehone"));

        // Add cell value factories for the update and delete buttons


        col_del.setCellFactory(col -> {
            TableCell<Client, Void> cell = new TableCell<Client, Void>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        Client client = getTableRow().getItem();
                        try {
                            ClientDAO clientDAO = new ClientDAO();
                            clientDAO.delete(client);
                            UpdateTable();
                            Alert alert =new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("delete");
                            alert.setHeaderText("anything");
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

        col_update.setCellFactory(param -> new TableCell<Client, Void>() {
            private final Button updateButton = new Button("Update");


            {
                updateButton.setOnAction(event -> {
                    Client client = getTableRow().getItem();

                    if (client != null) {

                        editClient(client);
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5);
                    hbox.getChildren().addAll(updateButton);
                    setGraphic(hbox);
                }
            }
        });

        col_new.setCellFactory(param -> new TableCell<Client, Void>() {
            private final Button newButton = new Button("add credit");

            {
                newButton.setOnAction(event -> {

                    try {
                        Client client = getTableRow().getItem();
                        long id_cli=client.getId_client();
                        String id = Long.toString(id_cli);

                        // Load the FXML file for the new interface
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCredit.fxml"));
                        Parent root = loader.load();
                        // Get the controller associated with the new FXML file
                        addCredit controller = loader.getController();

                        // Set the selected client data as properties of the controller
                        controller.setSelecetdClientName(client.getNom(), client.getTelepehone(), id );
                        // Create a new stage to display the new interface
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Hello E-salaf");

                        // Show the new interface
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5);
                    hbox.getChildren().addAll(newButton);
                    setGraphic(hbox);
                }
            }
        });
        col_check.setCellFactory(param -> new TableCell<Client, Void>() {
            private final Button checkButton = new Button("show credit");

            {
                checkButton.setOnAction(event -> {
                    try {
                        Client client = getTableRow().getItem();
                        long id_cli=client.getId_client();
                        String id = Long.toString(id_cli);
                        // Load the FXML file for the new interface
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("showCreditById.fxml"));
                        Parent root = loader.load();
                        // Get the controller associated with the new FXML file
                        showCreditById controller = loader.getController();

                        // Set the selected client data as properties of the controller
                        controller.setSelecetdClient(client.getNom(), client.getTelepehone(), id );
                        // Create a new stage to display the new interface
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Hello E-salaf");

                        // Show the new interface
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5);
                    hbox.getChildren().addAll(checkButton);
                    setGraphic(hbox);
                }
            }
        });

        mytab.setItems(getDataClients());

    }

    public static ObservableList<Client> getDataClients(){

        ClientDAO clidao = null;

        ObservableList<Client> listfx = FXCollections.observableArrayList();

        try {
            clidao = new ClientDAO();
            for(Client ettemp : clidao.getAll())
                listfx.add(ettemp);

       } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UpdateTable();
    }
}