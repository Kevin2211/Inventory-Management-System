package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.InHouse;
import sample.model.Inventory;
import sample.model.Outsourced;
import sample.model.Product;

/**
 @author KevinTrinh
 */

/**
 One feature that I would add to extend the usability of this program is a table view in main screen that shows a list
 of associated parts when the user select a product from the product table view, without the need to open modify product
 screen in order to view associated parts of selected product
 */


public class Main extends Application {
    /**
     This class loads the main screen and initiate the tables with data
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Inventory inv = new Inventory();

        Inventory.addPart(new InHouse(1,"CPU",200.00,70,50,300,25));
        inv.addPart(new Outsourced(2,"GPU",500.00,170,50,300,"DragonX"));
        inv.addPart(new InHouse(3,"RAM",200.00,200,50,350,51));

        inv.addProduct(new Product(1,"Laptop",100.00,50 , 40, 105));
        inv.addProduct(new Product(2,"Computer",200.00,45 , 33, 70));
        inv.addProduct(new Product(3,"Gaming Console",480.00,89 , 50, 300));


        Parent root = FXMLLoader.load(getClass().getResource("view/InventoryMainMenu.fxml"));
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
