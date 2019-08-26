package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class KorisniciController {

    private VideotekaModel model;

    public TextField korisnikIme;
    public TextField korisnikPrezime;
    public TextField korisnikAdresa;
    public TextField korisnikBroj;
    public TextField korisnikDatumUclanjivanja;
    public TextField korisnikIstekClanarine;
    public ListView listaIznajmljenih;
    public ListView listaHistorije;

    public KorisniciController(VideotekaModel m) {
        model = m;
    }

    @FXML
    public void initialize() throws SQLException {


        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        korisnikIme.setText(model.getTrenutniKorisnik().getIme());
        korisnikPrezime.setText(model.getTrenutniKorisnik().getPrezime());
        korisnikAdresa.setText(model.getTrenutniKorisnik().getAdresa());
        korisnikBroj.setText(model.getTrenutniKorisnik().getBrojTelefona());
        korisnikDatumUclanjivanja.setText(simpleDateFormat.format(model.getTrenutniKorisnik().getDatumUclanjivanja()));
//        Date date = model.getTrenutniKorisnik().getDatumUclanjivanja();
//        int godina = date

        int godinaIsteka = Integer.parseInt(korisnikDatumUclanjivanja.getText().substring(0,4));
        godinaIsteka = godinaIsteka+1;
        String godina = String.valueOf(godinaIsteka);
        String mjesecIsteka = korisnikDatumUclanjivanja.getText().substring(5,7);
        String danIsteka = korisnikDatumUclanjivanja.getText().substring(8,10);

        try {
            Date datumIsteka = simpleDateFormat.parse(godina + "-" + mjesecIsteka + "-" + danIsteka);
            System.out.println(datumIsteka);
            korisnikIstekClanarine.setText(simpleDateFormat.format(datumIsteka));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        model.getTrenutniKorisnik().getListaIznajmljenihSadrzaja().clear();
        model.getTrenutniKorisnik().getListaNedavnihPregleda().clear();

        model.dohvatiNarudzbe();

        listaIznajmljenih.setItems(model.getTrenutniKorisnik().getListaIznajmljenihSadrzaja());
        listaHistorije.setItems(model.getTrenutniKorisnik().getListaNedavnihPregleda());

        listaHistorije.setMouseTransparent(true);


    }

    public void vrati(ActionEvent actionEvent) throws SQLException {
        listaIznajmljenih.getSelectionModel().getSelectedItem();
        model.vratiNajam(listaIznajmljenih.getSelectionModel().getSelectedItem());
        listaIznajmljenih.getItems().remove( listaIznajmljenih.getSelectionModel().getSelectedItem());
    }

    public void stampaj(ActionEvent actionEvent) throws SQLException {
        try {
            new PrintReport().showReport(model.getConn(), model.getTrenutniKorisnik().getKorisnickoIme());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

}
