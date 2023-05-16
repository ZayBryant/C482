package com.example.software1.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.example.software1.model.Inventory.getAllParts;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This method is a constructor that allows the user to create a product object without parts.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method allows the user to add parts to a particular product.
     * @param part
     */

    public void addAssociatedPart(Part part) {

        associatedParts.add(part);

    }

    /**
     * This method allows the user to retrieve the products that is associated with a particular product.
     * @return
     */
    public ObservableList<Part> getAssociatedParts() {

        return associatedParts;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method allows the user to delete the associated parts.
     * @param selectedAssociatedPart
     * @return
     */

    public boolean deleteAssociatedPart(Part selectedAssociatedPart){

        return associatedParts.remove(selectedAssociatedPart);
    }


}