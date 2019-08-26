package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class VideotekaModel {

    private ObservableList<Film> filmovi = FXCollections.observableArrayList();
    private ObjectProperty<Film> trenutniFilm = null;
    private List<Film> listaFilmova =new ArrayList<>();
    public List<Serija> listaSerija = new ArrayList<>();

    private static SZ sz = new SZ();

    private ObservableList<Serija> serije = FXCollections.observableArrayList();
    private ObjectProperty<Serija> trenutnaSerija = null;

    private ObservableList<Sezona> sezone = FXCollections.observableArrayList();
    private ObjectProperty<Sezona> trenutnaSezona = null;


    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private ObjectProperty<Korisnik> trenutniKorisnik = null;


    public String getTrenutniZanr() {
        return trenutniZanr;
    }

    public void setTrenutniZanr(String trenutniZanr) {
        this.trenutniZanr = trenutniZanr;
    }

    private String trenutniZanr = null;

    private static VideotekaModel instanca = null;

    public Connection getConn() {
        return conn;
    }

    private Connection conn;
    private PreparedStatement stmt, stmt2, stmt3, stm4;

    private VideotekaModel() {



        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources/baza.db");
            System.out.println(conn);
            stmt = conn.prepareStatement("SELECT naziv, reziser, zanr, cijena, vrijemeTrajanja FROM filmovi");
            stmt2 = conn.prepareStatement("SELECT naziv, reziser, zanr FROM serije");
            stmt3 = conn.prepareStatement("SELECT naziv, vrijemeTrajanja, cijena, idSerije FROM sezone");
            stm4 = conn.prepareStatement("SELECT ime, prezime, adresa, brojTelefona, datumUclanjivanja, korisnickoIme, lozinka FROM korisnici");

            ResultSet rs = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();
            ResultSet rs3 = stmt3.executeQuery();
            ResultSet rs4 = stm4.executeQuery();

            while (rs.next()) {
                // System.out.println()
                Film k = new Film(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));

                filmovi.add(k);
                listaFilmova.add(k);

                if (trenutniFilm == null) trenutniFilm = new SimpleObjectProperty<Film>(k);

            }
            while (rs2.next()) {

                Serija s = new Serija(rs2.getString(1), rs2.getString(2), rs2.getString(3));

                serije.add(s);
                listaSerija.add(s);

                if (trenutnaSerija == null) trenutnaSerija = new SimpleObjectProperty<Serija>(s);
            }
            while (rs3.next()) {
                // System.out.println()
                Sezona sezona = new Sezona(rs3.getString(1), rs3.getInt(2), rs3.getDouble(3), rs3.getString(4));

                sezone.add(sezona);

                if (trenutnaSezona == null) trenutnaSezona = new SimpleObjectProperty<Sezona>(sezona);
            }
            while (rs4.next()) {
                // System.out.println()
                Korisnik korisnik = new Korisnik(rs4.getString(1), rs4.getString(2), rs4.getString(3), rs4.getString(4), rs4.getDate(5), rs4.getString(6), rs4.getString(7));

                korisnici.add(korisnik);

                if (trenutniKorisnik == null) trenutniKorisnik = new SimpleObjectProperty<Korisnik>(korisnik);
            }



        } catch(SQLException e) {
            System.out.println("Neuspješno čitanje iz baze: " + e.getMessage());
        }

        if (trenutniFilm == null) trenutniFilm = new SimpleObjectProperty<>();


        try {
            ucitajXML();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // System.out.println()
            Film k = null;
            try {
                k = new Film(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            filmovi.add(k);
            listaFilmova.add(k);

            if (trenutniFilm == null) trenutniFilm = new SimpleObjectProperty<Film>(k);

        }

        ucitajSerije();

        ResultSet rs2 = null;
        try {
            rs2 = stmt2.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!rs2.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // System.out.println()
            Serija s = null;
            try {
                s = new Serija(rs2.getString(1), rs2.getString(2), rs2.getString(3));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            serije.add(s);
            listaSerija.add(s);
            sz.setSerije(listaSerija);

            if (trenutnaSerija == null) trenutnaSerija = new SimpleObjectProperty<Serija>(s);

        }

        zapisiXml();

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

    public Korisnik pronadjiKorisnika(String korisnickoIme, String lozinka){
        for (Korisnik korisnik:korisnici){
            if(korisnik.getKorisnickoIme().equals(korisnickoIme) && korisnik.getLozinka().equals(lozinka)) return korisnik;
        }
        return null;
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

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public ObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void spremiNovogKorisnika(Korisnik korisnik) throws SQLException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        PreparedStatement unosKorisnika = conn.prepareStatement("INSERT INTO korisnici VALUES (?, ?, ?, ?, ?, ?, ?, ? )");
        unosKorisnika.setString(2, korisnik.getIme());
        unosKorisnika.setString(3, korisnik.getPrezime());
        unosKorisnika.setString(4, korisnik.getAdresa());
        unosKorisnika.setString(5, korisnik.getBrojTelefona());
        unosKorisnika.setDate(6, Date.valueOf(simpleDateFormat.format(korisnik.getDatumUclanjivanja())));
        unosKorisnika.setString(7, korisnik.getKorisnickoIme());
        unosKorisnika.setString(8, korisnik.getLozinka());
        unosKorisnika.executeUpdate();
    }

    public ArrayList<String> postojecaKorisnickaImena(){
        ArrayList<String > korisnickaImena = new ArrayList<>();
        for (Korisnik k: korisnici
             ) {
            korisnickaImena.add(k.getKorisnickoIme());
        }
        return korisnickaImena;
    }

    public void spremiNoviNajam(String korisnikId, String filmId, String serijaId, String sezonaId, int logicka) throws SQLException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        PreparedStatement unosNajma = conn.prepareStatement("INSERT INTO najam VALUES (?, ?, ?, ?, ?, ?, ? )");
        unosNajma.setString(2, korisnikId);
        unosNajma.setString(3, filmId);
        unosNajma.setString(4, serijaId);
        unosNajma.setString(5, sezonaId);
        unosNajma.setDate(6, Date.valueOf(simpleDateFormat.format(new java.util.Date())));
        unosNajma.setInt(7, logicka);
        unosNajma.executeUpdate();
    }

    public  void dohvatiNarudzbe() throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT korisnickoIme, filmNaziv, serijaNaziv, sezonaNaziv, datumIznajmljivanja, aktivnaNarudzba FROM najam");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            System.out.println(String.valueOf(rs.getInt(6)));
            System.out.println(getTrenutniKorisnik().getKorisnickoIme());

            if(rs.getString(1).equals(getTrenutniKorisnik().getKorisnickoIme())) {
                if(rs.getString(2) != null){
                    Film f = pronadjiFilm(rs.getString(2));
                    if(rs.getInt(6)==1)
                       // System.out.println(f.getNaziv());
                    getTrenutniKorisnik().dodajUListuIznajmljenih(f);
                    getTrenutniKorisnik().dodajUListuHistorije(f);
                }
                else{
                    Serija s = pronadjiSeriju(rs.getString(3));
                    if(rs.getInt(6)==1)
                        getTrenutniKorisnik().dodajUListuIznajmljenih(s);
                    getTrenutniKorisnik().dodajUListuHistorije(s);
                }
            }



        }
    }

    public void vratiNajam(Object o) throws SQLException {
        if(o instanceof Film){
            Film f = pronadjiFilm(((Film) o).getNaziv());
            PreparedStatement vracanjeNajma = conn.prepareStatement("UPDATE najam SET aktivnaNarudzba=? WHERE korisnickoIme = ? AND  filmNaziv = ?");
            vracanjeNajma.setInt(1,0);
            vracanjeNajma.setString(2, getTrenutniKorisnik().getKorisnickoIme());
            vracanjeNajma.setString(3, ((Film) o).getNaziv());
            vracanjeNajma.executeUpdate();
        }
        else{
            Serija s = pronadjiSeriju(((Serija) o).getNaziv());
            PreparedStatement vracanjeNajma = conn.prepareStatement("UPDATE najam SET aktivnaNarudzba=? WHERE korisnickoIme = ? AND  serijaNaziv = ?");
            vracanjeNajma.setInt(1,0);
            vracanjeNajma.setString(2, getTrenutniKorisnik().getKorisnickoIme());
            vracanjeNajma.setString(3, ((Serija) o).getNaziv());
            vracanjeNajma.executeUpdate();
        }
    }

    public void ucitajXML() throws SQLException {
        Document xmldoc = null;
        try {
            DocumentBuilder documentReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = documentReader.parse(new File("/home/samra/IdeaProjects/aplikacijaZaVideoteku/src/sample/film.xml"));
        }
        catch (Exception e) {
            System.out.println("film.xml nije validan XML dokument");

        }

        Element element = xmldoc.getDocumentElement();
        NodeList djeca=element.getChildNodes();


        for(int i=0; i<djeca.getLength(); i++){
            Node dijete = djeca.item(i);
            if(dijete instanceof Element){
                Film f=new Film();
                f.setNaziv(((Element) dijete).getElementsByTagName("naziv").item(0).getTextContent());
                f.setReziser(((Element) dijete).getElementsByTagName("reziser").item(0).getTextContent());
                f.setZanr(((Element) dijete).getElementsByTagName("zanr").item(0).getTextContent());
                f.setCijena(Double.parseDouble(((Element) dijete).getElementsByTagName("cijena").item(0).getTextContent()));
                f.setVrijemeTrajanja(Integer.parseInt(((Element) dijete).getElementsByTagName("vrijemeTrajanja").item(0).getTextContent()));

                System.out.println(listaFilmova.size());

                boolean logicka = true;
                for(int j=0; j<listaFilmova.size(); j++){
                    if(listaFilmova.get(j).getNaziv().equals(f.getNaziv()))  logicka=false;

                }

                if(logicka){
                PreparedStatement unosFilma = conn.prepareStatement("INSERT INTO filmovi VALUES (?, ?, ?, ?, ?, ?)");
                unosFilma.setString(2, f.getNaziv());
                unosFilma.setString(3, f.getReziser());
                unosFilma.setString(4, f.getZanr());
                unosFilma.setDouble(5, f.getCijena());
                unosFilma.setInt(6, f.getVrijemeTrajanja());
                unosFilma.executeUpdate();}

                System.out.println(f);
            }
        }

    }

    public void ucitajSerije(){

        Scanner ulaz=null;
        ArrayList<Serija> serije = new ArrayList<>();

        try {
            ulaz = new Scanner(new FileReader("/home/samra/IdeaProjects/aplikacijaZaVideoteku/src/sample/serije.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka serije.txt ne postoji ili se ne može otvoriti");
            System.out.println("Greška: " + e);
        }

        try {
            if(ulaz!=null)
                while (ulaz.hasNext()) {
                    String[] line = ulaz.nextLine().split(",");
                    String naziv=line[0].trim();
                    String reziser = line[1].trim();
                    String zanr = line[2].trim();
                    Serija s = new Serija(naziv,reziser,zanr);
                    System.out.println(s);


                    boolean logicka = true;
                    for(int j=0; j<listaSerija.size(); j++){
                        if(listaSerija.get(j).getNaziv().equals(s.getNaziv()))  logicka=false;

                    }

                    if(logicka){
                        System.out.println("Tu sam");
                        PreparedStatement unoseSerije = conn.prepareStatement("INSERT INTO serije VALUES (?, ?, ?, ? )");
                        unoseSerije.setString(2, s.getNaziv());
                        unoseSerije.setString(3, s.getReziser());
                        unoseSerije.setString(4, s.getZanr());
                        unoseSerije.executeUpdate();}
                }
        }
        catch(Exception e) {
            System.out.println("Problem pri čitanju/pisanju podataka.");
            System.out.println("Greška: " + e);
        }
        finally {
            if(ulaz!=null)
                ulaz.close();
        }

    }

    public static void  zapisiXml(){
        try{

            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("serije.xml"));
            izlaz.writeObject(sz);
            izlaz.close();

            System.out.println("Tu sam");
        }
        catch (Exception e){
            System.out.println("Greška: " + e);
        }
    }
}
