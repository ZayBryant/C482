package com.example.software1.controller;


import com.example.software1.model.Inventory;
import com.example.software1.model.Part;
import com.example.software1.model.Product;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.software1.model.Inventory.getAllParts;

import static java.lang.Double.*;

public class ModifyProduct extends AddProduct  implements Initializable {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // public static ObservableList<Part> tempPartList = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Product, Integer> InventoryLevelCol;

    @FXML
    private TableView<Part> bottomProductTBV;

    @FXML
    private TableColumn<Part, Double> costPerUnitCol;

    @FXML
    private TableColumn<Part, Double> costPerUnitColBtv;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelColBtv;

    @FXML
    private TextField modProdIdTxt;

    @FXML
    private TextField modProdInventoryTxt;

    @FXML
    private TextField modProdMaxTxt;

    @FXML
    private TextField modProdMinTxt;

    @FXML
    private TextField modProdNameTxt;

    @FXML
    private TextField modProdPriceTxt;

    @FXML
    private TableColumn<Product, Integer> partIdCol;

    @FXML
    private TableColumn<Product, Integer> partIdColBtv;

    @FXML
    private TableColumn<Product, String> partNameCol;

    @FXML
    private TableColumn<Product, String> partNameColBtv;


    @FXML
    private TextField topTBVSearchBar;


    @FXML
    private TableView<Part> topProductTBV;



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



    public ObservableList<Part> filter(String name){

        if((!getFilteredParts().isEmpty()))
            getFilteredParts().clear();
        for (Part part : getAllParts()){
           if ( part.getName().contains(name))
               getFilteredParts().add(part);
        }

        return getFilteredParts();
}

    /**
     * This method allows the user to add a selected part to the product they are creating.
     * @param event
     */

    @FXML
    void onActionAddModify(ActionEvent event) {
        Part selectedPart = topProductTBV.getSelectionModel().getSelectedItem();
       // if (!associatedParts.contains(selectedPart))
        {
            associatedParts.add(selectedPart);
            bottomProductTBV.setItems(associatedParts);
        }

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

        /**
         * This method removes a part from the product.
         * @param event
         */

    }

    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you want to remove this part?");
        alert.showAndWait();

        Part selectedPart = bottomProductTBV.getSelectionModel().getSelectedItem();
        if (associatedParts.contains(selectedPart))
        {
            associatedParts.remove(selectedPart);
            bottomProductTBV.setItems(associatedParts);
        }

    }
    /**
     * This method saves the product with all of the added parts, and validates the textfields were entered in correctly.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modProdIdTxt.getText());
            String name = modProdNameTxt.getText();
            double price = parseDouble(modProdPriceTxt.getText());
            int stock = Integer.parseInt(modProdInventoryTxt.getText());
            int max = Integer.parseInt(modProdMaxTxt.getText());
            int min = Integer.parseInt(modProdMinTxt.getText());

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

            Product updatedProduct = new Product(id, name, price, stock, min, max);

            for (Part part : associatedParts) {
                updatedProduct.addAssociatedPart(part);

            }

            Inventory.updateProduct(currentIndex, updatedProduct);

            screenSwitch("/view/MainMenu.fxml", event);

            // need to replicate the top portion in the bottom half just utilizing btv variables
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please input the correct data type");
            alert.showAndWait();
        }

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
        topTBVSearchBar.getText();

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

    private int currentIndex = 0;
    

    public void recceiveProduct(int selectedIndex, Product product) {



        currentIndex = selectedIndex;

        modProdIdTxt.setText(String.valueOf(product.getId()));
        modProdNameTxt.setText(String.valueOf(product.getName()));
        modProdInventoryTxt.setText(String.valueOf(product.getStock()));
        modProdPriceTxt.setText(String.valueOf(product.getPrice()));
        modProdMaxTxt.setText(String.valueOf(product.getMax()));
        modProdMinTxt.setText(String.valueOf(product.getMin()));


        for (Part part: product.getAssociatedParts()) {
            associatedParts.add(part);
        }




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



    public static void productCreation(Part part){

        ObservableList<Part> bottomTBV = FXCollections.observableArrayList();



    }
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    public static ObservableList<Part> getFilteredParts(){

        return filteredParts;
    }

}



