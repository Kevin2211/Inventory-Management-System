package sample.model;

/**


 @author KevinTrinh

 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.Locale;

/**
 Inventory class holds part and product data.
 */

public class Inventory {

    /**
     @value allParts This array list holds all parts of inventory
     @value allProducts This array list holds all products of inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     addPart method.
     This method adds a new Part to allParts
     @param newPart This a new created art that gets passed in the method
     */

    public static void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /**
     addProduct method.
     This adds a new Product to allProducts
     @param newProduct This a new created product that gets passed in the method
     */

    public static void addProduct(Product newProduct) {
        if( newProduct != null)
            allProducts.add(newProduct);
    }

    /**
     lookupPart using id method.
     This method looks up part in allParts using a unique id
     @param partId part id using to look up specific part
     @return null
     */


    public static Part lookUpPart(int partId) {


        for (Part p : allParts)
        {
            if (p.getId() == partId)
                return p;
        }
        return null;

    }

    /**
     lookupProduct using id method.
     This method looks up product in allParts using a unique id
     @param productId product id using to look up specific product
     @return null
     */

    public static Product lookUpProduct(int productId) {
        for (Product p : allProducts)
        {
            if (p.getId() == productId)
                return p;
        }
        return null;
    }

    /**
     lookupPart using name method.
     This method looks up part using name string
     @param partName part's name
     @return  ObservableList parts containing the name string
     */


    public static ObservableList<Part> lookUpPart(String partName) {

        if(allParts != null){
            ObservableList searchedPartList = FXCollections.observableArrayList();
            for (Part p: allParts) {
                if((p.getName().toLowerCase()).contains(partName.toLowerCase())){
                    searchedPartList.add(p);
                }

            }
            return searchedPartList;
        }
        return null;
    }

    /**
     lookupProduct using name method.
     This method looks up product using name string
     The search function was case sensitive so I change both productName and a.getName to lower case, this solves
     the case sensitive problem
     @param productName product's name
     @return  ObservableList products containing the name string
     */

    public static ObservableList<Product> lookUpProduct(String productName) {

        if(allProducts != null){
            ObservableList searchedProductList = FXCollections.observableArrayList();
            for (Product a: allProducts) {
                if((a.getName().toLowerCase()).contains(productName.toLowerCase())){
                    searchedProductList.add(a);
                }

            }
            return searchedProductList;
        }

        return null;
    }

    /**
     This updates a part in allParts
     @param index
     @param selectedPart
     */

    public static void updatePart(int index,Part selectedPart) {

        allParts.set(index,selectedPart);

    }

    /**
     This updates a product in allProducts
     @param index
     @param newProduct
     */

    public static void updateProduct(int index,Product newProduct) {

        allProducts.set(index, newProduct);
    }

    /**
     This deletes a part from allParts
     @param selectedPart
     @return true after part is removed
     */

    public static boolean deletePart(Part selectedPart) {

        allParts.remove(selectedPart);
        return true;
    }

    /**

     This returns a boolean if a product is valid to delete
     @param selectedProduct
     @return boolean
     */

    public static boolean deleteProduct(Product selectedProduct) {


        if(selectedProduct.getAllAssociatedPart().isEmpty()){
            allProducts.remove(selectedProduct);
            selectedProduct.getAllAssociatedPart().toString();
            return true;
        }else {

            selectedProduct.getAllAssociatedPart().toString();
            return false;
        }

    }

    /**
     @return getAllParts

     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     @return getAllProducts
     */

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }




}
