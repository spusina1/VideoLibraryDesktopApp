package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class FilmsController {

    private VideoLibraryModel model;

    public ChoiceBox<String> filmChoice;
    public ChoiceBox<String> typeChoice;
    public TextField filmTitle;
    public TextField filmDirector;
    public TextField filmType;
    public TextField filmPrice;
    public  TextField filmTime;
    public static BorderPane paneFilm;

    public FilmsController(VideoLibraryModel m) {
        model = m;
    }

    @FXML
    public void initialize() {
        filmChoice.setItems(model.dajNaziveFilmova());
        typeChoice.setItems(model.getFilmsTypes());

        model.currentFilmProperty().addListener((obs, oldFilm, newFilm) -> {
            

            if (oldFilm != null) {
                System.out.print(" sa "+oldFilm);
                filmTitle.textProperty().unbindBidirectional(oldFilm.titleProperty());
                filmDirector.textProperty().unbindBidirectional(oldFilm.directorProperty());
                filmType.textProperty().unbindBidirectional(oldFilm.typeProperty());
                filmPrice.textProperty().unbindBidirectional(oldFilm.priceProperty());

            }
            if (newFilm == null) {
                System.out.println(" na ništa");
                filmTitle.setText("");
                filmDirector.setText("");
                filmType.setText("");
                filmPrice.setText("");
            }
            else {
                System.out.println(" na " + newFilm);
                filmTitle.textProperty().unbindBidirectional(newFilm.titleProperty());
                filmDirector.textProperty().unbindBidirectional(newFilm.directorProperty());
                filmType.textProperty().unbindBidirectional(newFilm.typeProperty());
                filmPrice.textProperty().unbindBidirectional(newFilm.priceProperty());
            }

            });
    }

    public void selectedFilm(ActionEvent actionEvent) {
        System.out.println("Promijenjen je trenutni film na: " + filmChoice.getValue());
        model.setCurrentFilm(model.findFilm(filmChoice.getValue()));
        if(model.getCurrentFilm()!=null){
        filmTitle.setText(model.getCurrentFilm().getTitle());
        filmDirector.setText(model.getCurrentFilm().getDirector());
        filmType.setText(model.getCurrentFilm().getType());
        filmPrice.setText(String.valueOf(model.getCurrentFilm().getPrice()));
        filmTime.setText(String.valueOf(model.getCurrentFilm().getTime()));}
    }

    /*  Metoda koja se poziva klikom na button naruci u prozoru films.
        U ovoj metodi se iz baze dobavljaju svi najmovi korisnika koji su aktivni,
        te se provjerava da li je korisnik u mogucnosti iznajmiti novi sadržaj.
     */
    public void orderFilm(ActionEvent actionEvent) throws SQLException {


        model.getRents();
        if(model.getCurrentUser().getOrderList().size()<4){
        model.addNewRent(model.getCurrentUser().getUserName(), model.getCurrentFilm().getTitle(), null, null, 1);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Uspješno iznajmljivanje");
            alert.show();

        }
        else    {

            IllegalOrderException izuzetak= new IllegalOrderException();

             try {
                throw izuzetak;
                }
          catch (IllegalOrderException o){

            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setHeaderText("Greška!");
             alert.setContentText("Ne možete iznajmiti više od 4 filma/serije!");
             alert.show();
           }

        }

    }
    public void selectedType(ActionEvent actionEvent){
        model.setCurrentType(typeChoice.getValue());
        filmChoice.setItems(model.getFilmForType(typeChoice.getValue()));
    }
}
