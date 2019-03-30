package drd2.rig;

import drd2.rig.items.WeaponType;
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

            WeaponType wt1 = WeaponType.valueOf("SWORD");
            WeaponType wt2 = WeaponType.valueOf("HAMMER");
            System.out.println("Strings - " + wt1 + " " + wt2);

            //launch(args);
        }







        @Override
        public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("gui/GUI.fxml"));
        primaryStage.setTitle("My first java app with GUI!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}