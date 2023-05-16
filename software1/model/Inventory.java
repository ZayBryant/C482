package com.example.software1.model;
import com.example.software1.model.Product;
import  com.example.software1.model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);

    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);

    }

    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        int partID = 0;
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

    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        int productID = 0;
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

    public static ObservableList<Part> loookupPart(String partName) {

        ObservableList<Part> partNames  = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part gp : allParts){
            if(gp.getName().contains(partName)){
                partNames.add(gp);

            }
        }


        return partNames;


    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> prodNames  = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product gpro : allProducts){
            CharSequence prodName = null;
            if(gpro.getName().contains(prodName)){
                prodNames.add(gpro);

            }
        }


        return prodNames;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index,selectedPart);


    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);

    }

    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);

    }

    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }
}




