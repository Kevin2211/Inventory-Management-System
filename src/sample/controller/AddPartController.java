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
import javafx.stage.Stage;
import sample.model.*;
import static sample.controller.MainMenuController.isNumeric;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
/**
 @author KevinTrinh
 */

public class AddPartController implements Initializable {


    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;

    @FXML private TextField partMachineId;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMax;
    @FXML private TextField partMin;
    @FXML private TextField partId;


    @FXML private Label ratioOptionLabel;

    @FXML private Button cancelButton;
    @FXML private Button savePart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**
     This method saves new part to Inventory
     if statements check whether new part is valid to be saved
     @param event
     @throws IOException
     */
    @FXML
    public void saveButtonPushed(ActionEvent event) throws IOException{
        int id = uniqueIdGenerator();
        int inventory = 0;
        double price = 0;
        int max = 0;
        int min = 0;

            if (partName.getText().isEmpty() || partInv.getText().isEmpty() || partMin.getText().isEmpty() || partMax.getText().isEmpty() || partPrice.getText().isEmpty() || partMachineId.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText("Error");
                alert.setContentText("Please fill each field for part!");
                alert.showAndWait();
                return;
            }

            String name = partName.getText();


            try{
                inventory = Integer.parseInt(partInv.getText());
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inv must be a number");
                alert.showAndWait();
                return;
            }
            try {
                price = Double.parseDouble(partPrice.getText());
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Price must be a number");
                alert.showAndWait();
                return;
            }
            try {
                max = Integer.parseInt(partMax.getText());
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Max must be a number");
                alert.showAndWait();
                return;
            }
            try {
                min = Integer.parseInt(partMin.getText());
            } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Min must be a number");
                alert.showAndWait();
                return;
            }


            if (min > max) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("error");
              alert.setHeaderText("Input Error");
              alert.setContentText("Min value must be less than Max value");
              alert.showAndWait();
               return;
           }

                if (inventory < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error");
                    alert.setHeaderText("Input Error");
                    alert.setContentText("Inv must be between min and max");
                    alert.showAndWait();
                    return;
                }

                if (inventory > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error");
                    alert.setHeaderText("Input Error");
                    alert.setContentText("Inv must be between min and max");
                    alert.showAndWait();
                    return;
                }


            try{
            if (inHouseRadio.isSelected()) {
                int machineId = Integer.parseInt(partMachineId.getText());
                Part newPart = new InHouse(id, name, price, inventory, min, max, machineId);
                Inventory.addPart(newPart);
            }
            else {
                String companyName = partMachineId.getText();
                Part newPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                Inventory.addPart(newPart);
            }

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view/InventoryMainMenu.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

            } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Machine ID must be a number");
                alert.showAndWait();
            }






    }
    /**
     This method sets the text for MachineID when radio  is selected
     */

    @FXML
    public void inHouseRadioOption(){
        if (inHouseRadio.isSelected()){
            ratioOptionLabel.setText("Machine ID");
            partMachineId.promptTextProperty().setValue("Machine ID");
            outsourcedRadio.setSelected(false);
        }
    }
    /**
     This method sets the text for Company Name when radio  is selected
     */
    @FXML
    public void outSourcedRadioOption(){
        if (outsourcedRadio.isSelected()){
            ratioOptionLabel.setText("Company Name");
            partMachineId.promptTextProperty().setValue("Company name");
            inHouseRadio.setSelected(false);
        }
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
     This method generates a new unique ID when saving a new part
     This method is able to generate a continuous and unique number
     */
    private Integer uniqueIdGenerator(){
        ObservableList exclude = FXCollections.observableArrayList();
        int id = 1;
        for (Part item:Inventory.getAllParts()) {
            exclude.add(item.getId());
        }
        while (exclude.contains(id)){
            id++;
        }
        return id;
    }
}
