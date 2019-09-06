package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SeriesController {

    private VideoLibraryModel model;

    public ChoiceBox<String> serialChoice;
    public ChoiceBox<String> typeChoice;
    public ChoiceBox<Season> seasonChoice;
    public TextField serialTitle;
    public TextField serialDirector;
    public TextField serialType;
    public TextField serialTime;
    public TextField serialPrice;

    public SeriesController(VideoLibraryModel m) {
        model = m;
    }

    @FXML
    public void initialize() {

        typeChoice.getItems().clear();
        typeChoice.setItems(model.getSerialsTypes());

    }
    public void selectedType(ActionEvent actionEvent){
        model.setCurrentType(typeChoice.getValue());
        serialChoice.getItems().clear();
        serialChoice.setItems(model.getSerialsForType(typeChoice.getValue()));
    }

    public void selectedSerial(ActionEvent actionEvent) {
        System.out.println("Promijenjen je trenutnia serija na: " + serialChoice.getValue());
        model.setCurrentSerial(model.findSerial(serialChoice.getValue()));
        seasonChoice.getItems().clear();
        seasonChoice.setItems(model.getSerialSeasons(serialChoice.getValue()));

    }

    public void selectedSeason(ActionEvent actionEvent){
        model.setCurrentSeasone(seasonChoice.getValue());
        if(model.getCurrentSerial()!= null && model.getCurrentSeasone()!= null) {
            serialTitle.setText(model.getCurrentSerial().getTitle() + "-" + model.getCurrentSeasone().getTitle());
            serialDirector.setText(model.getCurrentSerial().getDirector());
            serialType.setText(model.getCurrentSerial().getType());
            serialTime.setText(String.valueOf(model.getCurrentSeasone().getTime()));
            serialPrice.setText(String.valueOf(model.getCurrentSeasone().getPrice()));
        }
    }

    /*  Metoda koja se poziva klikom na button naruci u prozoru series.
      U ovoj metodi se iz baze dobavljaju svi najmovi korisnika koji su aktivni,
      te se provjerava da li je korisnik u mogucnosti iznajmiti novi sadržaj.
   */
    public void orderSerial(ActionEvent actionEvent) throws SQLException {


        model.getRents();

        if(model.getCurrentUser().getOrderList().size()<4){

            model.addNewRent(model.getCurrentUser().getUserName(), null, model.getCurrentSerial().getTitle(), model.getCurrentSeasone().getTitle(), 1);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Uspješno iznajmljivanje");
            alert.show();
        }
        else    {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Greška!");
            alert.setContentText("Ne možete iznajmiti više od 4 filma/serije!");
            alert.show();}
        //model.printFilms();
        model.printSerials();
    }


}
