package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SerijeController {

    private VideotekaModel model;

    public ChoiceBox<String> izborSerije;
    public ChoiceBox<String> izborZanra;
    public ChoiceBox<Sezona> izborSezone;
    public TextField serijaNaziv;
    public TextField serijaReziser;
    public TextField serijaZanr;
    public TextField serijaTrajanje;
    public TextField serijaCijena;
    //public ListView listaKnjiga;

    public SerijeController(VideotekaModel m) {
        model = m;
    }

    @FXML
    public void initialize() {

        izborZanra.setItems(model.dajNaziveZanrovaSerija());

    }
    public void izabraniZanr(ActionEvent actionEvent){
        model.setTrenutniZanr(izborZanra.getValue());
        izborSerije.setItems(model.dajSerijeZaZanr(izborZanra.getValue()));
    }

    public void izabranaSerija(ActionEvent actionEvent) {
        System.out.println("Promijenjen je trenutnia serija na: " + izborSerije.getValue());
        model.setTrenutnaSerija(model.pronadjiSeriju(izborSerije.getValue()));
        izborSezone.setItems(model.dajSezoneSerije(izborSerije.getValue()));

    }

    public void  izabranaSezona(ActionEvent actionEvent){
        model.setTrenutnaSezona(izborSezone.getValue());
        if(model.getTrenutnaSerija()!= null && model.getTrenutnaSezona()!= null) {
            serijaNaziv.setText(model.getTrenutnaSerija().getNaziv() + "-" + model.getTrenutnaSezona().getNaziv());
            serijaReziser.setText(model.getTrenutnaSerija().getReziser());
            serijaZanr.setText(model.getTrenutnaSerija().getZanr());
            serijaTrajanje.setText(String.valueOf(model.getTrenutnaSezona().getVrijeTrajanja()));
            serijaCijena.setText(String.valueOf(model.getTrenutnaSezona().getCijena()));
        }
    }

    public void ispisiSeriju(ActionEvent actionEvent) throws SQLException {

        model.getTrenutniKorisnik().getListaIznajmljenihSadrzaja().clear();
        model.getTrenutniKorisnik().getListaNedavnihPregleda().clear();

        model.dohvatiNarudzbe();

        if(model.getTrenutniKorisnik().getListaIznajmljenihSadrzaja().size()<4){


            System.out.println(model.getTrenutniKorisnik().getListaIznajmljenihSadrzaja().size());
            model.spremiNoviNajam(model.getTrenutniKorisnik().getKorisnickoIme(), null, model.getTrenutnaSerija().getNaziv(), model.getTrenutnaSezona().getNaziv(), 1);

        }
        else    {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Greška!");
            alert.setContentText("Ne možete iznajmiti više od 4 filma/serije!");
            alert.show();}
        //model.ispisiFilmove();
        model.ispisiSerije();
    }


}
