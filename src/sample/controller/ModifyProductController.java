package sample.controller;

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
import sample.model.Inventory;
import sample.model.Part;
import sample.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static sample.controller.MainMenuController.isNumeric;

/**
 @author KevinTrinh
 */

public class ModifyProductController implements Initializable {


    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @FXML private TextField searchedPart;
    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField minTextField;
    @FXML private TextField maxTextField;

    @FXML private TableView<Part> allPartTableView;
    @FXML private TableView<Part> associatedPartTableView;

    @FXML private TableColumn<Part, Integer> partId;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partInventory;
    @FXML private TableColumn<Part, Double> partPrice;

    @FXML private TableColumn<Part, Integer> associatedPartId;
    @FXML private TableColumn<Part, String> associatedPartName;
    @FXML private TableColumn<Part, Integer> associatedPartInv;
    @FXML private TableColumn<Part, Double> associatedPartPrice;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> partSearchList = FXCollections.observableArrayList();


    private Product selectedProduct;
    private int selectedIndex;


    /**
     Sets values for associatedPartTableView and allPartTableView
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartTableView.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        reloadAssocPartTable();
    }
    /**
     This methods add selected part to associated part table
     */
    @FXML
    public void addPartToProductButton() {

        Part singleSelectedPart = allPartTableView.getSelectionModel().getSelectedItem();
        if(singleSelectedPart != null && !associatedParts.contains(singleSelectedPart)) {
            associatedParts.add(singleSelectedPart);
            reloadAssocPartTable();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("Error");
            alert.setContentText("Part is already selected");
            alert.showAndWait();
        }

        associatedPartTableView.refresh();
    }

    /**
     Returns to main screen when button pushed
     @throws IOException
     */

    @FXML
    public void cancelButtonPushed() throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/InventoryMainMenu.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     This methods remove selected part from associated part table
     */

    @FXML
    public void deleteButtonPushed(){
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                associatedParts.remove(selectedPart);
            } else {
                alert.close();
            }
        }

        associatedPartTableView.refresh();
    }
    /**
     This method saves modified product to Inventory
     if statements check whether modified product is valid to be saved
     @throws IOException
     */
    @FXML
    public void saveButtonPushed() throws IOException{
        int id = selectedProduct.getId();
        int inventory = 0;
        double price = 0;
        int max = 0;
        int min = 0;
        if (nameTextField.getText().isEmpty() || invTextField.getText().isEmpty() || minTextField.getText().isEmpty() || maxTextField.getText().isEmpty() || priceTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("Error");
            alert.setContentText("Please fill out all information for part!");
            alert.showAndWait();
            return;
        }

        String name = nameTextField.getText();
        double totalCost = 0;

        try{
            inventory = Integer.parseInt(invTextField.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inv must be a number");
            alert.showAndWait();
            return;
        }
        try {
            price = Double.parseDouble(priceTextField.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Price must be a number");
            alert.showAndWait();
            return;
        }
        try {
            max = Integer.parseInt(maxTextField.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Max must be a number");
            alert.showAndWait();
            return;
        }
        try {
            min = Integer.parseInt(minTextField.getText());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Min must be a number");
            alert.showAndWait();
            return;
        }

        for (Part p: associatedParts) {
          totalCost += p.getPrice();
        }

        if (min > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("Error");
            alert.setContentText("Min value must be less than Max value");
            alert.showAndWait();
            return;
        }
        if (inventory < min){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("Error");
            alert.setContentText("Inventory level must be between min and max value");
            alert.showAndWait();
            return;
        }

        if (inventory > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("Error");
            alert.setContentText("Inventory level must be between min and max value");
            alert.showAndWait();
            return;
        }
        if (price < totalCost){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("Error");
            alert.setContentText("Product Price cannot be less than the sum of the price of the parts.");
            alert.showAndWait();
            return;
        }

        Product modifiedProduct = new Product(id, name, price, inventory, min, max);
        for (Part p: associatedParts) {
            modifiedProduct.addAssociatedPart(p);
        }

        Inventory.updateProduct(selectedIndex, modifiedProduct);




        Stage stage = (Stage)saveButton.getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/InventoryMainMenu.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     This method loads information of selected product from main screen
     @param product
     @param index
     */

    public void setProduct(Product product, int index) {

        this.selectedProduct = product;
        this.selectedIndex = index;



            this.idTextField.setText(Integer.toString(product.getId()));
            this.nameTextField.setText(product.getName());
            this.invTextField.setText((Integer.toString(product.getStock())));
            this.priceTextField.setText((Double.toString(product.getPrice())));
            this.maxTextField.setText((Integer.toString(product.getMax())));
            this.minTextField.setText((Integer.toString(product.getMin())));

        if (product.getAllAssociatedPart().isEmpty()) {
            associatedPartTableView.setPlaceholder(new Label(("No parts associated with this product.Add Parts")));
        } else {

            this.associatedParts = product.getAllAssociatedPart();
            reloadAssocPartTable();
        }


    }
    /**
     This method adds search function to partSearch text field using either partID or partName
     Not case sensitive
     */
    @FXML public void searchedParts(){

        if (!searchedPart.getText().isEmpty()){
            if (isNumeric(searchedPart.getText())){
                int partId = Integer.parseInt(searchedPart.getText().trim());
                partSearchList.setAll(Inventory.lookUpPart(partId));
            }else {
                partSearchList.setAll(Inventory.lookUpPart(searchedPart.getText().trim()));
            }
        }
        else{
            partSearchList.setAll(Inventory.getAllParts());
        }
        if(partSearchList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setHeaderText("Item Not Found");
            alert.showAndWait();
        }else{
            allPartTableView.setItems(partSearchList);
        }
    }
    /**
    Updates associated part table after add or delete pushed
     */
    private void reloadAssocPartTable() {
        associatedPartTableView.setItems(associatedParts);

    }

}
