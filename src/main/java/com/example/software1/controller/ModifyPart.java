package com.example.software1.controller;



import com.example.software1.model.*;
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

public class ModifyPart  implements Initializable {

    @FXML
    private Label MachineIDorCompany;

    @FXML
    private ToggleGroup locationTG;

    @FXML
    private TextField modPartIdCol;

    @FXML
    private TextField modPartInventoryCol;

    @FXML
    private TextField modPartMachineIdCol;

    @FXML
    private TextField modPartMaxCol;

    @FXML
    private TextField modPartMinCol;

    @FXML
    private TextField modPartNameCol;

    @FXML
    private TextField modPartPriceCostCol;

    @FXML
    private RadioButton modPartRadioBtnInHouse;

    @FXML
    private RadioButton modPartRadioBtnOutSource;
    private int currentIndex = 0;

    Stage stage;
    Parent scene;

    /**
     * This method cancels the attempt to modify the part and redirects the user to the main screen.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionCancelModifyPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you are ready to leave? ");
        alert.setTitle("Alert");


        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            screenSwitch("/view/MainMenu.fxml", event);
        }
    }
// will throw an error if a part is not selected try catch block?

    //private static TextField partToMod = null;

    /**
     * This method saves the modification of a particular part.
     * @param event
     * @throws IOException
     */

    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException {
    //    int index = Inventory.getAllParts().indexOf(partToMod);
        try {
            if (modPartRadioBtnInHouse.isSelected()) {
                int id = Integer.parseInt(modPartIdCol.getText());
                String name = modPartNameCol.getText();
                double price = Double.parseDouble(modPartPriceCostCol.getText());
                int stock = Integer.parseInt(modPartInventoryCol.getText());
                int max = Integer.parseInt(modPartMaxCol.getText());
                int min = Integer.parseInt(modPartMinCol.getText());
                int machineId = Integer.parseInt(modPartMachineIdCol.getText());

                Part updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(index, updatedPart);

              // InHouse newPart = new InHouse(id, name, price, stock, max, min, machineId);
                // Inventory.updatePart(index, newPart);

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



            } else if (modPartRadioBtnOutSource.isSelected()) {
                int id = Integer.parseInt(modPartIdCol.getText());
                String name = modPartNameCol.getText();
                double price = Double.parseDouble(modPartPriceCostCol.getText());
                int stock = Integer.parseInt(modPartInventoryCol.getText());
                int max = Integer.parseInt(modPartMaxCol.getText());
                int min = Integer.parseInt(modPartMinCol.getText());
                String machineId = String.valueOf(modPartMachineIdCol.getText());
                String companyName = String.valueOf(modPartMachineIdCol.getText());

                Part updatedPart = new Outsourced(id, name, price, stock, min, max,companyName);
                Inventory.updatePart(index, updatedPart);



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



              //  screenSwitch("/view/MainMenu.fxml", event);
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please input the correct data type");
            alert.showAndWait();
        }
        screenSwitch("/view/MainMenu.fxml", event);


        //Need to create a piece of code that will delete previous part. need utilize update part method.

    }

    private int index = 0;
    public void sendPart(  Part part ,int selectedIndex ) {
        index = selectedIndex;



        if (part instanceof InHouse) {
            modPartRadioBtnInHouse.setSelected(true);


            modPartMachineIdCol.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        if (part instanceof Outsourced) {
            modPartRadioBtnOutSource.setSelected(true);
            MachineIDorCompany.setText("Company Name");
            modPartMachineIdCol.setText(String.valueOf(((Outsourced) part).getCompanyName()));

        }


        modPartIdCol.setText(String.valueOf(part.getId()));
        modPartNameCol.setText(part.getName());
        modPartInventoryCol.setText(String.valueOf(part.getStock()));
        modPartPriceCostCol.setText(String.valueOf(part.getPrice()));
        modPartMaxCol.setText(String.valueOf(part.getMax()));
        modPartMinCol.setText(String.valueOf(part.getMin()));



    }
   

    /**
     * This method allows the user to adjust the source of the part to InHouse.
     * @param event
     */
    @FXML
    void onActionaddPartInHouse(ActionEvent event) {
        modPartRadioBtnInHouse.isSelected();
        MachineIDorCompany.setText("Machine ID");


    }

    /**
     * This method allows the user to adjust the source of the part to Outsource.
     * @param event
     */

    @FXML
    void onActionaddPartOutsourced(ActionEvent event) {
        modPartRadioBtnOutSource.isSelected();
        MachineIDorCompany.setText("Company Name");

    }

    /**
     * This method recives information from the parts table and inputs the data into their respected textfields.
     * @param part
     */


    /**
     * This method loads the screen as well as the data.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
