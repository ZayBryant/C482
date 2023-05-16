package com.example.software1.controller;

import com.example.software1.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.software1.model.Inventory.*;

public class AddProduct  implements Initializable {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
   // public static ObservableList<Part> tempPartList = FXCollections.observableArrayList();
   // public static ObservableList<Part> bottomTBVAssociatedParts = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> InventoryLevelCol;

    @FXML
    private TableView<Part> bottomProductTBV;

    @FXML
    private TableColumn<Part, Integer> costPerUnitCol;

    @FXML
    private TableColumn<Part, Integer> costPerUnitColBtv;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelColBtv;


    @FXML
    private TextField apProdIdTxt;

    @FXML
    private TextField apProdInventoryTxt;

    @FXML
    private TextField apProdMaxTxt;

    @FXML
    private TextField apProdMinTxt;

    @FXML
    private TextField apProdNameTxt;

    @FXML
    private TextField apProdPriceTxt;
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partIdColBtv;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, String > partNameColBtv;

    @FXML
    private HBox searchBarTxt;

    @FXML
    private TableView<Part> topProductTBV;

    @FXML
    private TextField topTBVSearchBar;

    private static int nextProductID = 1;
    public static int getNextProductID(){
        return nextProductID++;
    }

    Stage stage;
    Parent scene;
    /**
     * This method fits into searchParts method enabling the user to search by partID.
     * @param partID
     * @return
     */

    private Part searchByPartID (int partID){
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part gp = allParts.get(i);

            if (gp.getId() == partID) {
                return gp;
            }
        }
        for(Part gp: allParts){
            if(gp.getId() == partID) {
                return gp;
            }
        }
        return null;
    }
    /**
     * This method fits into searchParts method enabling the user to search by name.
     * @param partName
     * @return
     */

    private ObservableList<Part> searchByPartName(String partName){
        ObservableList<Part> partNames  = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part gp : allParts){
            if(gp.getName().contains(partName)){
                partNames.add(gp);

            }
        }


        return partNames;
    }

    /**
     * This method allows the user to search the top table view by utilizing the searchByPartID and searchByPartName methods.
     * @param event
     */

    @FXML
    void topTBVSearchParts(ActionEvent event) {
        String q = topTBVSearchBar.getText();

        ObservableList<Part> partList = searchByPartName(q);

        if(partList.size() == 0 ){
            try {
                int partID = Integer.parseInt(q);
                Part gp = searchByPartID(partID);
                if (gp!= null) {
                    partList.add(gp);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace(); //ignore
            }
        }

        topProductTBV.setItems(partList);


    }


    /**
     * This method allows the user to add a selected part to the product they are creating.
     * @param event
     * @throws IOException
     */


    @FXML
    void onActionAddModify(ActionEvent event) throws IOException {


        Part selectedPart = topProductTBV.getSelectionModel().getSelectedItem();

        associatedParts.add(selectedPart);
    }


    /**
     * This method cancels the users attempt to create a new product, and redirects them to the main screen.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you are ready to leave? ");
        alert.setTitle("Alert");


        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            screenSwitch("/view/MainMenu.fxml", event);
        }


    }

    /**
     * This method removes a part from the product.
     * @param event
     */

    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you want to remove this part?");
        alert.showAndWait();

        Part selectedPart = bottomProductTBV.getSelectionModel().getSelectedItem();

        associatedParts.remove(selectedPart);

    }

    /**
     * This method saves the product with all of the added parts, and validates the textfields were entered in correctly.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {



            String name = apProdNameTxt.getText();
            double price = Double.parseDouble(apProdPriceTxt.getText());
            int stock = Integer.parseInt(apProdInventoryTxt.getText());
            int max = Integer.parseInt(apProdMaxTxt.getText());
            int min = Integer.parseInt(apProdMinTxt.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Min is greater than max please change!");
                alert.showAndWait();

                return;

            }
            if (stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Stock is greater than max");
                alert.showAndWait();
                return;

            }
            if (stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Stock is less than min");
                alert.showAndWait();
                return;

            }

            Product product = new Product(getNextProductID(), name, price, stock, min, max);


            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }


            Inventory.addProduct(product);


            screenSwitch("/view/MainMenu.fxml", event);

            System.out.println(getAllProducts());

        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please input the correct data type");
            alert.showAndWait();
        }

    }

    /**
     * This method switches between screens based upon what string is inputted for its parameter.
     * @param updateScreen
     * @param event
     * @throws IOException
     */



    public   void screenSwitch(String updateScreen, ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(updateScreen));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method loads the screen as well as the data.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        topProductTBV.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        costPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        bottomProductTBV.setItems(associatedParts);
        partIdColBtv.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColBtv.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelColBtv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        costPerUnitColBtv.setCellValueFactory(new PropertyValueFactory<>("Price"));




    }




}
