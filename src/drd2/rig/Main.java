package drd2.rig;

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

            LinkedList<String> list = new LinkedList<String>();
            list.add("aaááuuuu");
            list.add("aaaauuúú");
            list.add("aaaáuuuu");
            list.add("aaáauuúu");
            list.add("aaaáuuuú");
            list.add("aaaauuúu");
            list.add("aaáauuuú");
            list.add("aaáauuuů");
            list.add("aaaáuuuů");
            list.add("aaáauuuu");
            list.add("aaaauuúů");
            list.add("aaaauuúů");
            list.add("aaaauuůú");
            list.add("aaaauuůů");
            list.add("aaaauuuú");
            list.add("aaaauuůu");
            list.add("aaaauuuu");
            list.add("aaaauuuů");
            list.add("aaaauuůu");


            Comparator<String> pls = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            };

            list.sort(pls);

            System.out.println(Arrays.toString(list.toArray()));


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