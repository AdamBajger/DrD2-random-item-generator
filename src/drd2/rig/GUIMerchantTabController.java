package drd2.rig;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIMerchantTabController implements Initializable{
    public Tab merchantTab;
    public Accordion sortimentViewSpace;
    public Button createSortimentButton;


    private Merchant m;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Tab has been loaded.");

    }

    public void generateSortiment() throws IOException {
        // clear out original item elements
        sortimentViewSpace.getPanes().clear();

        System.out.println("Merchant saved = " + merchantTab.getUserData());

        // get the merchant from the tab
        if(this.m == null) {
            this.m = (Merchant)merchantTab.getUserData();
        } else {
            System.out.println("Merchant already set.");
        }
        m.createSortiment("A1");

        // visualize those items in GUI, add the to the accordion
        for (Weapon weapon : this.m.getCurrentGoods()) {
            TitledPane newItemElement = FXMLLoader.load(getClass().getResource("GUI_ItemAccordionElement.fxml"));

            // you need to add an element to that accordion for each item!
            newItemElement.setText(weapon.name);


            sortimentViewSpace.getPanes().add(newItemElement);
        }

    }

}
