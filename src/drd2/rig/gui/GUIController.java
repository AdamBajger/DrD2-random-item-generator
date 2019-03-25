package drd2.rig.gui;

import drd2.rig.Merchant;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Adam Václav Bajger on 29.08.2017.
 */

public class GUIController implements Initializable{
    public Button createSortimentButton;
    public Button createMerchantButton;
    public TabPane mainWindow;
    public Accordion sortimentViewSpace;
    public Tab defaultMerchant;


    //public FXMLLoader Loader = new FXMLLoader();

    Merchant testingMerchant = new Merchant();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading Merchant data...");

        // try to load default merchant...
        try {
            // load default merchant
            Merchant defaultMerchantObject;
            try {
                defaultMerchantObject = Merchant.loadObjectFromFile(Merchant.DEFAULT_OBJ_STOR_DIR + Merchant.DEFAULT_OBJ_NAME + ".merchant");
            } catch(IOException e) {
                e.printStackTrace();
                // Initialize new merchant without loading it from file
                defaultMerchantObject = new Merchant();
                defaultMerchantObject.name = Merchant.DEFAULT_OBJ_NAME;
                System.out.println("Trying to create default merchant file...");
                try {
                    defaultMerchantObject.storeObjectToFile(Merchant.DEFAULT_OBJ_STOR_DIR); // After initializing Merchant, store it to file
                } catch(IOException ee) {
                    System.out.println("Failed to store default merchant object.");
                    ee.printStackTrace();
                }
            }
            // use the method to add the tab
            addMerchantTab(defaultMerchantObject);
        } catch (Exception e) {
            //if default merchant can nor be loaded, just go on with no merchant loaded.
            e.printStackTrace();
            // let user create his own merchant
        }
    }

    public void addMerchantTab() {
        addMerchantTab(new Merchant());
    }

    private void addMerchantTab(Merchant m) {
        try {
            // Load tab from FXML
            Tab newMerchantTab = FXMLLoader.load(getClass().getResource("GUI_MerchantTab.fxml"));
            newMerchantTab.setUserData(m); // Add merchant to the tab as user data


            // add the tab to the scene graph
            mainWindow.getTabs().add(newMerchantTab);
            /*!!!! Learn how to use runnable API and use it here
                    create new thread*/


        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}