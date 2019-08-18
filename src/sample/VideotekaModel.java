package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class VideotekaModel {

    private ObservableList<Film> filmovi = FXCollections.observableArrayList();
    private ObjectProperty<Film> trenutniFilm = null;

    private ObservableList<Serija> serije = FXCollections.observableArrayList();
    private ObjectProperty<Serija> trenutnaSerija = null;

    private ObservableList<Sezona> sezone = FXCollections.observableArrayList();
    private ObjectProperty<Sezona> trenutnaSezona = null;

    public String getTrenutniZanr() {
        return trenutniZanr;
    }

    public void setTrenutniZanr(String trenutniZanr) {
        this.trenutniZanr = trenutniZanr;
    }

    private String trenutniZanr = null;

    private static VideotekaModel instanca = null;

    private Connection conn;
    private PreparedStatement stmt, stmt2, stmt3;

    private VideotekaModel() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources/baza.db");
            System.out.println(conn);
            stmt = conn.prepareStatement("SELECT naziv, reziser, zanr, cijena, vrijemeTrajanja FROM filmovi");
            stmt2 = conn.prepareStatement("SELECT naziv, reziser, zanr FROM serije");
            stmt3 = conn.prepareStatement("SELECT naziv, vrijemeTrajanja, cijena, idSerije FROM sezone");

            ResultSet rs = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();
            ResultSet rs3 = stmt3.executeQuery();

            while (rs.next()) {
                // System.out.println()
                Film k = new Film(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));

                filmovi.add(k);

                if (trenutniFilm == null) trenutniFilm = new SimpleObjectProperty<Film>(k);

            }
            while (rs2.next()) {

                Serija s = new Serija(rs2.getString(1), rs2.getString(2), rs2.getString(3));

                serije.add(s);

                if (trenutnaSerija == null) trenutnaSerija = new SimpleObjectProperty<Serija>(s);
            }
            while (rs3.next()) {
                // System.out.println()
                Sezona sezona = new Sezona(rs3.getString(1), rs3.getInt(2), rs3.getDouble(3), rs3.getString(4));

                sezone.add(sezona);

                if (trenutnaSezona == null) trenutnaSezona = new SimpleObjectProperty<Sezona>(sezona);
            }

        } catch(SQLException e) {
            System.out.println("Neuspješno čitanje iz baze: " + e.getMessage());
        }

        if (trenutniFilm == null) trenutniFilm = new SimpleObjectProperty<>();
    }

    private static void initialize() {
        instanca = new VideotekaModel();
    }

    public static VideotekaModel dajInstancu() {
        if (instanca == null) initialize();
        return instanca;
    }


    public ObservableList<Film> getFilmovi() {
        return filmovi;
    }

    public ObservableList<String> dajNaziveFilmova(){
        ObservableList<String> naziviFilmova = FXCollections.observableArrayList();
        for (Film x:filmovi
             ) {naziviFilmova.add(x.getNaziv());

        }
        return naziviFilmova;
    }

    public ObservableList<String> dajNaziveSerija(){
        ObservableList<String> naziviSerija = FXCollections.observableArrayList();
        for (Serija x:serije
        ) {naziviSerija.add(x.getNaziv());

        }
        return naziviSerija;
    }

    public ObservableList<String> dajFilmoveZaZanr(String zanr){
        ObservableList<String> naziviFilmova = FXCollections.observableArrayList();
        for (Film x:filmovi
        ) {if(x.getZanr().equals(zanr)) naziviFilmova.add(x.getNaziv());

        }
        return naziviFilmova;
    }


    public ObservableList<String> dajSerijeZaZanr (String zanr){
        ObservableList<String> naziviSerija = FXCollections.observableArrayList();
        for (Serija x:serije
        ) {if(x.getZanr().equals(zanr)) naziviSerija.add(x.getNaziv());

        }
        return naziviSerija;
    }

    public Film pronadjiFilm(String naziv){

        for (Film f:filmovi
             ) {
            if(f.getNaziv().equals(naziv)) return f;
        }
        return null;
    }

    public Serija pronadjiSeriju(String naziv){

        for (Serija f:serije
        ) {
            if(f.getNaziv().equals(naziv)) return f;
        }
        return null;
    }


    public ObservableList<String> dajNaziveZanrova(){
        ObservableList<String> naziviZanrova = FXCollections.observableArrayList();
        for (Film x:filmovi
        ) {if(!naziviZanrova.contains(x.getZanr()))naziviZanrova.add(x.getZanr());

        }
        return naziviZanrova;
    }


    public ObservableList<String> dajNaziveZanrovaSerija(){
        ObservableList<String> naziviZanrova = FXCollections.observableArrayList();
        for (Serija x:serije
        ) {if(!naziviZanrova.contains(x.getZanr()))naziviZanrova.add(x.getZanr());

        }
        return naziviZanrova;
    }

    public ObservableList<Sezona> dajSezoneSerije(String nazivSerije){


        ObservableList<Sezona> sezoneSerije = FXCollections.observableArrayList();
        for (Sezona x:sezone
        ) {if(x.getIdSerije().equals(nazivSerije) ) sezoneSerije.add(x);

        }
        return sezoneSerije;
    }


    public void setFilmovi(ObservableList<Film> filmovi) {
        this.filmovi = filmovi;
    }

    public Film getTrenutniFilm() {
        return trenutniFilm.get();
    }

    public ObjectProperty<Film> trenutniFilmProperty() {
        return trenutniFilm;
    }

    public void setTrenutniFilm(Film trenutniFilm) {
        this.trenutniFilm.set(trenutniFilm);
    }


    public void ispisiFilmove() {
        System.out.println("Filmovi su:");
        for (Film k : filmovi)
            System.out.println(k);
    }

    public void ispisiSerije() {
        System.out.println("Serije su:");
        for (Serija k : serije)
            System.out.println(k);
    }

    public ObservableList<Serija> getSerije() {
        return serije;
    }

    public void setSerije(ObservableList<Serija> serije) {
        this.serije = serije;
    }

    public Serija getTrenutnaSerija() {
        return trenutnaSerija.get();
    }

    public ObjectProperty<Serija> trenutnaSerijaProperty() {
        return trenutnaSerija;
    }

    public void setTrenutnaSerija(Serija trenutnaSerija) {
        this.trenutnaSerija.set(trenutnaSerija);
    }

    public ObservableList<Sezona> getSezone() {
        return sezone;
    }

    public void setSezone(ObservableList<Sezona> sezone) {
        this.sezone = sezone;
    }

    public Sezona getTrenutnaSezona() {
        return trenutnaSezona.get();
    }

    public ObjectProperty<Sezona> trenutnaSezonaProperty() {
        return trenutnaSezona;
    }

    public void setTrenutnaSezona(Sezona trenutnaSezona) {
        this.trenutnaSezona.set(trenutnaSezona);
    }
}
