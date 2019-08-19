package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
    public void initialize() {


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


    }

}
