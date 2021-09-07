package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.Inventory;
import sample.model.Part;

import javafx.event.ActionEvent;
import sample.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 @author KevinTrinh
 */

public class MainMenuController implements Initializable {

    @FXML private TableView<Part> partTable;
    @FXML private TableView<Product> productTable;


    @FXML private Button modifyPartButton;
    @FXML private Button modifyProductButton;



    @FXML private TextField partSearch;
    @FXML private TextField productSearch;

    @FXML private TableColumn<Part, Integer> partId;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partInventory;
    @FXML private TableColumn<Part, Double> partPrice;


    @FXML private TableColumn<Product, Integer> productId;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, Integer> productInventory;
    @FXML private TableColumn<Product, Double> productPrice;


    @FXML private ObservableList<Part> partInv = FXCollections.observableArrayList();
    @FXML private ObservableList<Product> productInv = FXCollections.observableArrayList();
    @FXML private ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();
    @FXML private ObservableList<Product> productInvSearch = FXCollections.observableArrayList();

    /**
     This method adds search function to partSearch text field using either partID or partName
     Not case sensitive
     */

    @FXML public void searchedPart(){

        if (!partSearch.getText().isEmpty()){
            if (isNumeric(partSearch.getText())){
                int partId = Integer.parseInt(partSearch.getText().trim());
                partsInvSearch.setAll(Inventory.lookUpPart(partId));
            }else {
                partsInvSearch.setAll(Inventory.lookUpPart(partSearch.getText().trim()));
            }
        }else{
            partsInvSearch.setAll(Inventory.getAllParts());
        }
        if(partsInvSearch.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setHeaderText("Item Not Found");
            alert.showAndWait();
        }else{
            partTable.setItems(partsInvSearch);
        }
    }

    /**
     This method adds search function to productSearch text field using either productID or productName
     Not case sensitive
     */

    @FXML public void searchedProduct(ActionEvent event){

        if (!productSearch.getText().isEmpty()){
            if (isNumeric(productSearch.getText())){
                int productId = Integer.parseInt(productSearch.getText().trim());
                productInvSearch.setAll(Inventory.lookUpProduct(productId));
            }else {
                productInvSearch.setAll(Inventory.lookUpProduct(productSearch.getText().trim()));
            }
        }else{
            productInvSearch.setAll(Inventory.getAllProducts());
        }
        if(productInvSearch.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setHeaderText("Item Not Found");
            alert.showAndWait();
        }else{
            productTable.setItems(productInvSearch);
        }
    }


    /**
     This method checks if a string is numeric
     @param str Input from the user
     @return boolean
     */


    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     This method loads addPart screen
     @param event
     @throws Exception
     */

    @FXML public void addPartButtonPushed(ActionEvent event) throws Exception{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/AddPart.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }
    /**
     This method loads addProduct screen
     @param event
     @throws Exception
     */
    @FXML public void addProductButtonPushed(ActionEvent event) throws Exception{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/AddProduct.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }

    /**
     Exits the program
     */

    @FXML
    public void exitButtonPushed() {

        System.exit(0);
    }

    /**
     Sets values for product and part tables' columns
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<Product,Integer> ("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<Product,Integer>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productInv.setAll(Inventory.getAllProducts());
        partTable.setItems(Inventory.getAllParts());
        partInv.setAll(Inventory.getAllParts());


    }
    /**
     This methods loads modifyProduct screen and passes selected product from productTable
     @throws IOException
     */

    @FXML public void modifyProductButtonPushed() throws IOException {

        if(productTable.getSelectionModel().getSelectedItem() != null){
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            int index = productTable.getSelectionModel().getSelectedIndex();
            Stage stage;
            Parent root;
            stage = (Stage) modifyProductButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/ModifyProduct.fxml"));
            root = loader.load();

            ModifyProductController controller = loader.getController();


            controller.setProduct(selectedProduct, index);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     This methods loads modifyPart screen and passes selected part from partTable
     @throws IOException
     */

    @FXML public void modifyPartButtonPushed() throws IOException {

        if(partTable.getSelectionModel().getSelectedItem() != null){
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            int index = partTable.getSelectionModel().getSelectedIndex();
            Stage stage;
            Parent root;
            stage = (Stage) modifyPartButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample/view/ModifyPart.fxml"));
            root = loader.load();

            ModifyPartController controller = loader.getController();


            controller.setPart(selectedPart, index);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     This methods delete selected product from productTable and Inventory
     */
    @FXML public void deleteProductButtonPushed(){
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && selectedProduct.getAllAssociatedPart().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete");
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Inventory.getAllProducts().remove(selectedProduct);
            } else {
                alert.close();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Product cannot be deleted.");
            alert.setContentText("Selected product contains one or more parts.\n" +
                    "Please remove all parts to delete");
            alert.showAndWait();
            return;
        }
    }
    /**
     This methods delete selected part from partTable and Inventory
     */
    @FXML public void deletePartButtonPushed(){
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete");
            alert.setContentText("Are you sure you want to delete this Product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Inventory.getAllParts().remove(selectedPart);
            } else {
                alert.close();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
            return;
        }
    }


}
