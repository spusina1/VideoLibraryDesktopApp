package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class FimoviController {

    private VideotekaModel model;

    public ChoiceBox<String> izborFilma;
    public ChoiceBox<String> izborZanra;
    public TextField filmNaziv;
    public TextField filmReziser;
    public TextField filmZanr;
    public TextField filmCijena;
    public  TextField filmTrajanje;
    //public ListView listaKnjiga;

    public FimoviController(VideotekaModel m) {
        model = m;
    }

    @FXML
    public void initialize() {
        izborFilma.setItems(model.dajNaziveFilmova());
        izborZanra.setItems(model.dajNaziveZanrova());
        //knjigaAutor.textProperty().bindBidirectional(new SimpleStringProperty(model.getTrenutnaKnjiga().getAutor()));

        model.trenutniFilmProperty().addListener((obs, oldKnjiga, newKnjiga) -> {

            System.out.print("Mijenjam data binding");


            if (oldKnjiga != null) {
                System.out.print(" sa "+oldKnjiga);
                filmNaziv.textProperty().unbindBidirectional(oldKnjiga.nazivProperty());
                filmReziser.textProperty().unbindBidirectional(oldKnjiga.reziserProperty());
                filmZanr.textProperty().unbindBidirectional(oldKnjiga.zanrProperty());
                filmCijena.textProperty().unbindBidirectional(oldKnjiga.cijenaProperty());

            }
            if (newKnjiga == null) {
                System.out.println(" na ništa");
                filmNaziv.setText("");
                filmReziser.setText("");
                filmZanr.setText("");
                filmCijena.setText("");
            }
            else {
                System.out.println(" na " + newKnjiga);
                filmNaziv.textProperty().unbindBidirectional(newKnjiga.nazivProperty());
                filmReziser.textProperty().unbindBidirectional(newKnjiga.reziserProperty());
                filmZanr.textProperty().unbindBidirectional(newKnjiga.zanrProperty());
                filmCijena.textProperty().unbindBidirectional(newKnjiga.cijenaProperty());
            }

            });
    }

    public void izabraniFilm(ActionEvent actionEvent) {
        System.out.println("Promijenjen je trenutnii film na: " + izborFilma.getValue());
        model.setTrenutniFilm(model.pronadjiFilm(izborFilma.getValue()));
        if(model.getTrenutniFilm()!=null){
        filmNaziv.setText(model.getTrenutniFilm().getNaziv());
        filmReziser.setText(model.getTrenutniFilm().getReziser());
        filmZanr.setText(model.getTrenutniFilm().getZanr());
        filmCijena.setText(String.valueOf(model.getTrenutniFilm().getCijena()));
        filmTrajanje.setText(String.valueOf(model.getTrenutniFilm().getVrijemeTrajanja()));}
    }

    public void ispisiFilm(ActionEvent actionEvent) {
        model.ispisiFilmove();
    }
    public void izabraniZanr(ActionEvent actionEvent){
        model.setTrenutniZanr(izborZanra.getValue());
        izborFilma.setItems(model.dajFilmoveZaZanr(izborZanra.getValue()));
    }
}
