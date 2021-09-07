package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 @author KevinTrinh
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     Constructor for Product class
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
     Setters and getters for each elements
     */
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
     Adds a part to associatedParts list
     @param part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     Delete a part from associatedParts list
     @param part
     */
    public void deleteAssociatedProduct(Part part){
        associatedParts.remove(part);
    }
    /**
     @return all associatedParts
     */
    public ObservableList<Part> getAllAssociatedPart(){
        return associatedParts;
    }



}
