package drd2.rig;

import drd2.rig.generators.BagOfStuff;
import drd2.rig.items.WeaponBuilder;
import drd2.rig.items.WeaponType;
import drd2.rig.text.csv.CSVParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;


public class Main extends Application {

        public static void main(String[] args) {

            BagOfStuff<WeaponBuilder> bowb = CSVParser.parseWeaponsFromCSV("resources/csv/WEAPON_CURVED_SWORD.csv");


            // TODO: Complete WeaponBuilder to generate weapons and test it

        }







        @Override
        public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("gui/GUI.fxml"));
        primaryStage.setTitle("My first java app with GUI!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}