package sample;

import javafx.beans.property.SimpleStringProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Korisnik {


    private SimpleStringProperty ime = new SimpleStringProperty("");
    private  SimpleStringProperty prezime = new SimpleStringProperty("");
    private SimpleStringProperty adresa = new SimpleStringProperty("");
    private SimpleStringProperty brojTelefona = new SimpleStringProperty("");
    private Date datumUclanjivanja = new Date();
    private SimpleStringProperty korisnickoIme = new SimpleStringProperty("");
    private SimpleStringProperty lozinka = new SimpleStringProperty("");
    List<Object> listaIznajmljenihSadrzaja = new ArrayList<Object>();
    List<Object> listaNedavnihPregleda = new ArrayList<Object>();




    public Korisnik() {
    }

    public Korisnik(String ime, String prezime, String adresa, String brojTelefona, Date datumUclanjivanja, String korisnickoIme, String lozinka) {
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.adresa = new SimpleStringProperty(adresa);
        this.brojTelefona = new SimpleStringProperty(brojTelefona);
        this.datumUclanjivanja = datumUclanjivanja;
        this.korisnickoIme = new SimpleStringProperty(korisnickoIme);
        this.lozinka = new SimpleStringProperty(lozinka);

    }

    public String getIme() {
        return ime.get();
    }

    public SimpleStringProperty imeProperty() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public String getPrezime() {
        return prezime.get();
    }

    public SimpleStringProperty prezimeProperty() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public String getAdresa() {
        return adresa.get();
    }

    public SimpleStringProperty adresaProperty() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa.set(adresa);
    }

    public String getBrojTelefona() {
        return brojTelefona.get();
    }

    public SimpleStringProperty brojTelefonaProperty() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona.set(brojTelefona);
    }

    public Date getDatumUclanjivanja() {
        return datumUclanjivanja;
    }

    public void setDatumUclanjivanja(Date datumUclanjivanja) {
        this.datumUclanjivanja = datumUclanjivanja;
    }


    @Override
    public String toString() {
        return ime.get() + " " + prezime.get() + ", " + adresa.get() + ", " + brojTelefona.get() + ", " + datumUclanjivanja.toString() + korisnickoIme.get() + lozinka.get();
    }

    public String getKorisnickoIme() {
        return korisnickoIme.get();
    }

    public SimpleStringProperty korisnickoImeProperty() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme.set(korisnickoIme);
    }

    public String getLozinka() {
        return lozinka.get();
    }

    public SimpleStringProperty lozinkaProperty() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka.set(lozinka);
    }
}
