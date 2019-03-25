package drd2.rig;

import drd2.rig.csv.CSVItemParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


public class Main extends Application {

        public static void main(String[] args) {

            try {
                CSVItemParser.parseItemsFromCSV("resources/csv/CURVED_SWORD.csv", null, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


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