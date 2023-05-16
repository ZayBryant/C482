package com.example.software1;

import com.example.software1.controller.AddPart;
import com.example.software1.controller.AddProduct;
import com.example.software1.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The JAVADOC file is located directly beneath the SoftWare1 folder.
 */

/**
 * FUTURE ENHANCEMENT
 * Connect this application to an actual database for the sake of continuity. It will allow future employees to maintain an updated inventory.
 */

public class HelloApplication extends Application {
    /**
     * This method starts the stage for our application to display itself.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is the entry point for the entire javafx application.
     */
    public static void main(String[] args) {

        Part part1 = new InHouse(AddPart.getNextPartID(),"Wrench", 30.00, 50, 1, 100, 320);
        Part part2 = new Outsourced(AddPart.getNextPartID(), "Hammer", 50.00, 100, 1, 150, "It's Hammer Time");
        Part part3 = new InHouse(AddPart.getNextPartID(), "Screwdriver4", 5.00, 250, 10, 300, 450);

        Part part4 = new InHouse(AddPart.getNextPartID(), "Drill", 5.00, 250, 10, 300, 450);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);


        Product product1 = new Product(AddProduct.getNextProductID(),"toolset", 50.00, 500, 200, 650);
        Product product2 = new Product(AddProduct.getNextProductID(),"Screwdriverset", 30.00, 100, 10, 250);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);









        launch();
    }





}