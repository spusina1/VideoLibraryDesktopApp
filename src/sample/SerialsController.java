package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SerialsController {

    private VideotekaModel model;

    public ChoiceBox<String> serialChoice;
    public ChoiceBox<String> typeChoice;
    public ChoiceBox<Season> seasonChoice;
    public TextField serialTitle;
    public TextField serialDirector;
    public TextField serialType;
    public TextField serialTime;
    public TextField serialPrice;

    public SerialsController(VideotekaModel m) {
        model = m;
    }

    @FXML
    public void initialize() {

        typeChoice.setItems(model.getSerialsTypes());

    }
    public void selectedType(ActionEvent actionEvent){
        model.setCurrentType(typeChoice.getValue());
        serialChoice.setItems(model.gerSerialsForType(typeChoice.getValue()));
    }

    public void selectedSerial(ActionEvent actionEvent) {
        System.out.println("Promijenjen je trenutnia serija na: " + serialChoice.getValue());
        model.setCurrentSerial(model.findSerial(serialChoice.getValue()));
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

    public void orderSerial(ActionEvent actionEvent) throws SQLException {

        model.getCurrentUser().getOrderList().clear();
        model.getCurrentUser().getHistoryList().clear();

        model.getRents();

        if(model.getCurrentUser().getOrderList().size()<4){


            System.out.println(model.getCurrentUser().getOrderList().size());
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
