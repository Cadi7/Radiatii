package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

import static sample.Controller.citesteFisier;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        scene.setFill(Color.web("#81c483"));
        stage.setScene(scene);
        stage.setTitle("Varianta 16");
        stage.show();




    }

    public static void main(String[] args) {
        System.out.println("Vrei sa primesti meniul in consola sau sub forma de inferfata grafica?");
        Scanner cin = new Scanner(System.in);
        String cuv = cin.next();
        if (cuv.equals("consola")) {
            Vector<Vector<Short>> A;
            A = citesteFisier();
            Controller.meniu(A);
        } else if (cuv.equals("interfata")) {
            launch(args);
        }
        else  {
            System.out.println("Ai scris gresit datele. ");System.exit(0);
        }

    }
}





