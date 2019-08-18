package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PrijavaController implements Initializable{



    public TextField korisnickoIme;
    public PasswordField lozinka;
    public Button prijava;

    public TextField ime;
    public TextField prezime;
    public TextField brojMobitela;
    public PasswordField novaLozinka;
    public DatePicker datumRodjenja;
    public Button registracija;




    @FXML
    public void initialize() {korisnickoIme.getStyleClass().add("poljeNijeIspravno");

    korisnickoIme.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (n.isEmpty()) {korisnickoIme.getStyleClass().add("poljeNijeIspravno");
                } else {korisnickoIme.getStyleClass().removeAll("poljeNijeIspravno");
                }
            }
        });
    }



    public void prijavi(ActionEvent actionEvent){

        //otvaranje novog prozora

        if(!validacijaPrijave()){ korisnickoIme.getStyleClass().add("poljeNijeIspravno");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Greška!");
            alert.setContentText("Korisničko ime mora počinjati s velikim slovom abecede!");
            alert.show();

        }else {korisnickoIme.getStyleClass().removeAll("poljeNijeIspravno");
            try{

                Parent root= FXMLLoader.load(getClass().getResource("glavniIzbornik.fxml"));
                Stage stage=new Stage();
                stage.setTitle("Glavni izbornik");
                stage.setScene(new Scene(root, 600, 400));
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean validacijaPrijave(){
        String kIme = korisnickoIme.getText();
        if (kIme.isEmpty() || kIme.charAt(0)<'A' || kIme.charAt(0)>'Z') {
            return false;

        }
        return true;
    }


    private boolean logicka1=false, logicka2=false, logicka3=false, logicka4=false, logicka5=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {

                if (validnoIme(n)) {
                    ime.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka1=true;


                } else {
                    ime.getStyleClass().add("poljeNijeIspravno");
                    logicka1=false;

                }

            }
        });

        prezime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validnoPrezime(n)) {
                    prezime.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka2=true;

                } else {
                    prezime.getStyleClass().add("poljeNijeIspravno");
                    logicka2=false;

                }
            }
        });





        brojMobitela.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validanKontaktTelefon(n)) {
                    brojMobitela.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka3=true;

                } else {
                    brojMobitela.getStyleClass().add("poljeNijeIspravno");
                    logicka3=false;

                }
            }
        });

        novaLozinka.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (novaLozinka.getText().length()<8) {
                    novaLozinka.getStyleClass().add("poljeNijeIspravno");
                    logicka4=false;

                } else {
                    novaLozinka.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka4=true;

                }
            }
        });



        datumRodjenja.valueProperty().addListener((ow, o, n) -> {
            if (validanDatumRodjenja(n)) {
                datumRodjenja.getStyleClass().removeAll("poljeNijeIspravno");
                logicka5=true;


            } else {

                datumRodjenja.getStyleClass().add("poljeNijeIspravno");
                logicka5=false;

            }
        });


    }

    private boolean validnoIme(String n) {
        if (n.trim().isEmpty() || n.length() > 20 || n.charAt(0) < 65 || n.charAt(0) > 90){ return false; }
        for (int i = 1; i < n.length(); i++)
            if (n.charAt(i) < 97 || n.charAt(i) > 122){  return false; }

        return true;
    }

    private boolean validnoPrezime(String n) {
        if (n.trim().isEmpty() || n.length() > 20 || n.charAt(0) < 65 || n.charAt(0) > 90) { return false;}
        for (int i = 1; i < n.length(); i++)
            if (n.charAt(i) < 97 || n.charAt(i) > 122) { return false;}

        return true;
    }


    private boolean validanDatumRodjenja(LocalDate d) {
        LocalDate td = LocalDate.now();
        if (d.isAfter(td) || d.isEqual(td)) {  return false;}

            return  true;
    }

    private boolean validanKontaktTelefon(String n) {
        if(n.length()<9 || n.length()>10) return false;
        String prviDio=n.substring(0,3);
        String drugiDio=n.substring(3);

        try{
            int prvi= Integer.parseInt(prviDio);
            int drugi = Integer.parseInt(drugiDio);
            if(prvi>99){  return  false;}
        }catch (Exception e){

            return false;
        }


        return true;
    }


    public void registriraj(javafx.event.ActionEvent actionEvent) {

        if(!(logicka5 && logicka1 && logicka2 && logicka3 && logicka4)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Podaci nisu validni!");
            alert.setContentText("Provjerite da li ste ispravno unijeli sve podatke!");
            alert.show();}

}

}
