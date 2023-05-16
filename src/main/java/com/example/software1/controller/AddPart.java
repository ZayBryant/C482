package com.example.software1.controller;



import com.example.software1.model.InHouse;
import com.example.software1.model.Inventory;
import com.example.software1.model.Outsourced;
import com.example.software1.model.Part;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;




public class AddPart  implements Initializable {


    public TableView partsTable;
    public TableColumn partID;
    public TableColumn partsInventoryLevel;
    public TableColumn partPriceAndCost;
    public TableColumn partsName;

    public TableView productTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productInventory;
    public TableColumn productPrice;
    public Button removeB;
    public Button addB;

    public Stage stage;
    Parent scene;

    @FXML
    private TextField addPartIDCol;

    @FXML
    private TextField addPartMin;


    @FXML
    private TextField addPartCol;

    @FXML
    private TextField addPartInventory;

    @FXML
    private TextField addPartMachineId;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartNameCol;

    @FXML
    private TextField addPartPriceAndCost;

    @FXML
    private RadioButton addPartRadioBtnInHouse;

    @FXML
    private RadioButton addPartRadioBtnOutSource;

    @FXML
    private Label MachineIDorCompany;


    @FXML
    private Label addPartLabel;

    private static int nextPartID = 1;
    public static int getNextPartID(){
        return nextPartID++;
    }

    public void idGen(int id) {
        int randoNumber = (int) (Math.random() * 1000 * Math.round(5));
    }

    /**
     * This method toggles the readio button to select InHouse.
     * @param actionEvent
     */
    @FXML
    public void onActionAddPartInHouse(ActionEvent actionEvent) {
        addPartRadioBtnInHouse.isSelected();
        MachineIDorCompany.setText("Machine ID");
    }

    /**
     * This method toggles the radio button to select Outsource.
     * @param actionEvent
     */

    @FXML
    public void onActionAddPartOutsourced(ActionEvent actionEvent) {
        addPartRadioBtnOutSource.isSelected();
        MachineIDorCompany.setText("Company Name");


    }

    /**
     * This method will cancel the attempt of the new part
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionCancelAddPart(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you are ready to leave? ");
        alert.setTitle("Alert");


        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            screenSwitch("/view/MainMenu.fxml", event);
        }
    }

    /**
     * This method will save a part to the parts table when the save button is hit.
     * @param event
     * @throws IOException
     */


    @FXML
    void onActionSaveAddPart(ActionEvent event) throws IOException {

        try {


            String name = addPartNameCol.getText();
            double price = Double.parseDouble(addPartPriceAndCost.getText());
            int stock = Integer.parseInt(addPartInventory.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(addPartMin.getText());

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

            if (addPartRadioBtnInHouse.isSelected()) {

                int machineId = Integer.parseInt(addPartMachineId.getText());
                Inventory.addPart(new InHouse(getNextPartID(), name, price, stock, min, max, machineId));
            }
            if (addPartRadioBtnOutSource.isSelected()) {
                String companyName = String.valueOf(addPartMachineId.getText());

                Inventory.addPart(new Outsourced(getNextPartID(), name, price, stock, min, max, companyName));


                screenSwitch("/view/MainMenu.fxml", event);}
                System.out.println(min + " " + max);
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please input the correct data type");
            alert.showAndWait();
            return;
        }
        screenSwitch("/view/MainMenu.fxml", event);

    //    screenSwitch("/view/MainMenu.fxml", event);
    }

    /**
     * This method will initalize this screen and it's neccesaary data.
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

        /**
         * This method will take the user to the fxml documents name in string method
         **/

    public void screenSwitch(String updateScreen, ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(updateScreen));
        stage.setScene(new Scene(scene));
        stage.show();


    }


    //  This auto generates a number on the add part form but line 115 int partID = 0; sets it on the main menu
    // auto gen needs to be placed in the initalized portion of this class and matching in the send part calls above.

}










