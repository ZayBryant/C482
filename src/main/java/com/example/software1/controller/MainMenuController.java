package com.example.software1.controller;

import javafx.application.Application;
import com.example.software1.HelloApplication;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import static com.example.software1.model.Inventory.*;
import static javafx.fxml.FXMLLoader.load;


public class MainMenuController  implements Initializable {

    Stage stage = new Stage();
    Parent scene;

    @FXML
    private TableView partsTableView;
    @FXML
    private TableColumn<Product, Integer> ProCol;

    @FXML
    private TableColumn<Part, String> pNameCol;

    @FXML
    private TableColumn<Part, Integer> partCol;

    @FXML
    private TableColumn<Part, Double> partCpuCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    @FXML
    private TextField partTxt;



    @FXML
    private TableColumn<Product, Double> proCpuCol;

    @FXML
    private TableColumn<Product, Integer> proInventoryLevelCol;

    @FXML
    private TableColumn<Product, String> proNameCol;

    @FXML
    private TextField proTxt;


    @FXML
   private TableView<Product> productsTableView;




    /**
     * This method deletes a part from the partsviewtable, if it's selected.
     * @param event
     */
    @FXML
    void onACtionDeletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to delete this part");


        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Part selectedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
            deletePart(selectedPart);
        }
        else if (result.isPresent() && result.get() == ButtonType.CANCEL){
            return;
        }


    }

    /**
     * This method switches the user to add the addPart screen.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
       screenSwitch("/view/AddPart.fxml", event);

    }

    /**
     * This method switches the user to the addProduct screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        screenSwitch("/view/AddProduct.fxml", event);


    }

    /**
     * This method deletes a product from the product table view.It needs to be selected and there are no associated parts.
     * @param event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        try {

            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            ObservableList<Part> test = selectedProduct.getAssociatedParts();
            System.out.println(test);

            if (test.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("alert");
                alert.setContentText("Are you sure you want to delete this product");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    deleteProduct(selectedProduct);

                } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                    return;
                }

            } if (selectedProduct.getAssociatedParts() != null){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Alert");
                alert1.setContentText("This product has associated part/parts and cannot be deleted. Please remove the associated parts before deletion. ");
                alert1.showAndWait();
                return;
            }

        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("You need to select a product!");
            alert.showAndWait();

        }
    }


    /**
     * This method closes the application.
     * @param event
     */
    @FXML
    void onActionExitMainMenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to close the application?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
        System.exit(0);}
        else return;

    }

    /**
     * This method sends the user to the modify part screen.
     * This method loads the data from the add part screen, which allows the user to change the values of all textfields except the ID field.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try {
            Part part = (Part) partsTableView.getSelectionModel().getSelectedItem();



            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPart MPController = loader.getController();
          //  MPController.sendPart(part, Inventory.getAllParts().indexOf(part));

          MPController.sendPart((Part) partsTableView.getSelectionModel().getSelectedItem(), Inventory.getAllParts().indexOf(part) );

           //System.out.println(p.getMax() + " " + " **" + p.getMin());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("You need to select a part");
            alert.showAndWait();
        }
    }
    /** RUNTIME ERROR: In this method I expereince a very difficult NullPointerError. It wouldn't pull the associated parts for a particular product,
     * and if it did the entire parts table would be shared amongst the different products. It was simply solved by utilized the proper method in the UML Diagram.
     * Attaching the getassociatedParts method to the proper parameter fixed my issue.
     * This method sends the user to the modify product screen.
     * This method loads the data from the add Product screen, which allows the user to change the values of all textfields except the ID field.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        try {

            Product product = productsTableView.getSelectionModel().getSelectedItem();
            if (product == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a product!");
                alert.show();
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProduct MProdController = loader.getController();
            MProdController.recceiveProduct(Inventory.getAllProducts().indexOf(product), product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("You need to select a Product");
            alert.showAndWait();
        }
    }

    /**
     * This method allows a user to search parts by name or id.
     * @param event
     */

    @FXML
    void searchParts(ActionEvent event) {
        String q = partTxt.getText();

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

        partsTableView.setItems(partList);
        partTxt.getText();

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
     * This method search products using name or ID.
     * @param event
     */

    @FXML
    void searchProducts(ActionEvent event) {
        String pn = proTxt.getText();

        ObservableList<Product> productList = searchByProductName(pn);

        if(productList.size() == 0 ){
            try {
                int productID = Integer.parseInt(pn);
                Product newProduct = searchByProduct(productID);
                if (newProduct!= null) {
                    productList.add(newProduct);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace(); //ignore
            }
        }

        productsTableView.setItems(productList);
        proTxt.getText();

    }

    /**
     * This method fits into the searchProducts method, by adding the search by productID functionality.
     * @param productID
     * @return
     */
    private Product searchByProduct (int productID){

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product gpro = allProducts.get(i);

            if (gpro.getId() == productID) {
                return gpro;
            }
        }
        for(Product gpro: allProducts){
            if(gpro.getId() == productID) {
                return gpro;
            }
        }

        return null;
    }

    /**
     * This method fits into the searchProducts method, by adding the search by product name functionality.
     * @param prodName
     * @return
     */


    private ObservableList<Product> searchByProductName(String prodName){
        ObservableList<Product> prodNames  = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product gpro : allProducts){
            if(gpro.getName().contains(prodName)){
                prodNames.add(gpro);

            }
        }


        return prodNames;
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

    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
       // partsTableView.setItems(filter("x"));

        partCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partCpuCol.setCellValueFactory(new PropertyValueFactory<>("Price"));


       productsTableView.setItems(Inventory.getAllProducts());

        ProCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        proNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        proInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        proCpuCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        //partsTableView.getSelectionModel().getSelectedItem();


        //filterd parts example
        //partsTableView.setItems(filter(" s"));
/*
        if(update(2,new InHouse(1,"NewPartnotwrench", 30.00, 50, 1, 100, 320)))
            System.out.println("Success");
        else
            System.out.println("fail");

        partsTableView.getSelectionModel().select(selectPart(3));
*/

    }


    /**
     * This methods updates part to reflect new changes.
     * @param id
     * @param part
     * @return
     */

// update method for inventory?? Needs to be tweaked for the method in that class inventory
    public boolean update(int id, Part part) {

        int index = -1;
        for (Part part1 : Inventory.getAllParts()) {
            index++;
            if (part1.getId() == id) {
                Inventory.getAllParts().set(index, part);
                    return true;
                }
            }
            return false;
        }
/*
    Does this method need to be removed?

    public Part selectPart (int id){
        for(Part part: Inventory.getAllParts()){
            if( part.getId() == id)
                return part;
        }
        return null;
    }
*/



    }









