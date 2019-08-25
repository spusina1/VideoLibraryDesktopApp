package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static sample.PrijavaController.appJezik;

public class GlavniIzbornikController {

    VideotekaModel model = VideotekaModel.dajInstancu();

    public void izborFilma(javafx.event.ActionEvent actionEvent) throws IOException {

        Locale.setDefault(appJezik);
        System.out.println("Trenutni jezik " + appJezik);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("filmovi.fxml"), bundle);

        loader.setController(new FimoviController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Filmovi");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();


    }


    public void izborSerije(javafx.event.ActionEvent actionEvent) throws IOException {

        Locale.setDefault(appJezik);
        System.out.println("Trenutni jezik " + appJezik);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("serije.fxml"), bundle);

        loader.setController(new SerijeController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Serije");
        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.show();


    }


    public void pregledajPodatke(javafx.event.ActionEvent actionEvent) throws IOException {


        Locale.setDefault(appJezik);
        System.out.println("Trenutni jezik " + appJezik);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("korisnici.fxml"), bundle);

        loader.setController(new KorisniciController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Podaci o korisniku");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();


    }


    public void odjaviKorisnika(javafx.event.ActionEvent actionEvent) {



    }

}
