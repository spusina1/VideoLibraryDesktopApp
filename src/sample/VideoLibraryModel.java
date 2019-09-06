package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class VideoLibraryModel implements Documents{

    /*
    Ova klasa je singleton, instancira se samo jednom,
    a instanci je omogucen globalni pristup preko metode getInstance()
    iz ostalih klasa. Uz to je ova klasa i kontejnerska klasa.
     */

    private ObservableList<Film> films = FXCollections.observableArrayList();
    private ObjectProperty<Film> currentFilm = null;
    private List<Film> filmsList =new ArrayList<>();
    private List<Serial> serialsList = new ArrayList<>();

    private static SZ sz = new SZ();

    private ObservableList<Serial> serials = FXCollections.observableArrayList();
    private ObjectProperty<Serial> currentSerial = null;

    private ObservableList<Season> seasone = FXCollections.observableArrayList();
    private ObjectProperty<Season> currentSeasone = null;


    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObjectProperty<User> currentUser = null;


    private String currentType = null;



    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    private static VideoLibraryModel instanca = null;

    public Connection getConn() {
        return conn;
    }

    private Connection conn;
    private PreparedStatement stmt, stmt2, stmt3, stm4;

    private VideoLibraryModel() {


        /*
        Konekcija s bazom i provjera ispravnosti iste.
         */
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

                Film k = new Film(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));

                films.add(k);
                filmsList.add(k);

                if (currentFilm == null) currentFilm = new SimpleObjectProperty<Film>(k);

            }
            while (rs2.next()) {

                Serial s = new Serial(rs2.getString(1), rs2.getString(2), rs2.getString(3));

                serials.add(s);
                serialsList.add(s);

                if (currentSerial == null) currentSerial = new SimpleObjectProperty<Serial>(s);
            }
            while (rs3.next()) {

                Season season = new Season(rs3.getString(1), rs3.getInt(2), rs3.getDouble(3), rs3.getString(4));

                seasone.add(season);

                if (currentSeasone == null) currentSeasone = new SimpleObjectProperty<Season>(season);
            }
            while (rs4.next()) {

                User user = new User(rs4.getString(1), rs4.getString(2), rs4.getString(3), rs4.getString(4), rs4.getDate(5), rs4.getString(6), rs4.getString(7));

                users.add(user);

                if (currentUser == null) currentUser = new SimpleObjectProperty<User>(user);
            }



        } catch(SQLException e) {
            System.out.println("Neuspješno čitanje iz baze: " + e.getMessage());
        }

        if (currentFilm == null) currentFilm = new SimpleObjectProperty<>();

        films.clear();
        serials.clear();




        try {
            loadXML();
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

            films.add(k);
            filmsList.add(k);

            if (currentFilm == null) currentFilm = new SimpleObjectProperty<Film>(k);

        }

        loadTXT();

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
            Serial s = null;
            try {
                s = new Serial(rs2.getString(1), rs2.getString(2), rs2.getString(3));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            serials.add(s);
            serialsList.add(s);
            sz.setSerials(serialsList);

            if (currentSerial == null) currentSerial = new SimpleObjectProperty<Serial>(s);

        }

        recordXML();

    }

    private static void initialize() {
        instanca = new VideoLibraryModel();
    }

    public static VideoLibraryModel getInstance() {
        if (instanca == null) initialize();
        return instanca;
    }


    public ObservableList<Film> getFilms() {
        return films;
    }

    public ObservableList<String> dajNaziveFilmova(){
        ObservableList<String> naziviFilmova = FXCollections.observableArrayList();
        for (Film x: films
             ) {naziviFilmova.add(x.getTitle());

        }
        return naziviFilmova;
    }

    public ObservableList<String> getSerialsTitles(){
        ObservableList<String> naziviSerija = FXCollections.observableArrayList();
        for (Serial x: serials
        ) {naziviSerija.add(x.getTitle());

        }
        return naziviSerija;
    }

    public ObservableList<String> getFilmForType(String zanr){
        ObservableList<String> naziviFilmova = FXCollections.observableArrayList();
        for (Film x: films
        ) {if(x.getType().equals(zanr)) naziviFilmova.add(x.getTitle());

        }
        return naziviFilmova;
    }


    public ObservableList<String> getSerialsForType(String zanr){
        ObservableList<String> naziviSerija = FXCollections.observableArrayList();
        for (Serial x: serials
        ) {if(x.getType().equals(zanr)) naziviSerija.add(x.getTitle());

        }
        return naziviSerija;
    }

    public Film findFilm(String naziv){

        for (Film f: films
             ) {
            if(f.getTitle().equals(naziv)) return f;
        }
        return null;
    }

    public Serial findSerial(String naziv){

        for (Serial f: serials
        ) {
            if(f.getTitle().equals(naziv)) return f;
        }
        return null;
    }


    public ObservableList<String> getFilmsTypes(){
        ObservableList<String> naziviZanrova = FXCollections.observableArrayList();
        for (Film x: films
        ) {if(!naziviZanrova.contains(x.getType()))naziviZanrova.add(x.getType());

        }
        return naziviZanrova;
    }


    public ObservableList<String> getSerialsTypes(){
        ObservableList<String> naziviZanrova = FXCollections.observableArrayList();
        for (Serial x: serials
        ) {if(!naziviZanrova.contains(x.getType()))naziviZanrova.add(x.getType());

        }
        return naziviZanrova;
    }

    public ObservableList<Season> getSerialSeasons(String nazivSerije){


        ObservableList<Season> seasons = FXCollections.observableArrayList();
        for (Season x: seasone
        ) {if(x.getSerialId().equals(nazivSerije) ) seasons.add(x);

        }
        return seasons;
    }

    public User findUser(String korisnickoIme, String lozinka) throws SQLException {

        try {
            PreparedStatement stm4 = conn.prepareStatement("SELECT ime, prezime, adresa, brojTelefona, datumUclanjivanja, korisnickoIme, lozinka FROM korisnici");
        } catch (SQLException e) {
            e.printStackTrace();
        }

            ResultSet rs4 = stm4.executeQuery();

        while (rs4.next()) {
            // System.out.println()
            User user = new User(rs4.getString(1), rs4.getString(2), rs4.getString(3), rs4.getString(4), rs4.getDate(5), rs4.getString(6), rs4.getString(7));

            users.add(user);

            if (currentUser == null) currentUser = new SimpleObjectProperty<User>(user);
        }


        for (User user : users){
            if(user.getUserName().equals(korisnickoIme) && user.getPassword().equals(lozinka)) return user;
        }
        return null;
    }


    public void setFilms(ObservableList<Film> films) {
        this.films = films;
    }

    public Film getCurrentFilm() {
        return currentFilm.get();
    }

    public ObjectProperty<Film> currentFilmProperty() {
        return currentFilm;
    }

    public void setCurrentFilm(Film currentFilm) {
        this.currentFilm.set(currentFilm);
    }


    public void printFilms() {
        System.out.println("Filmovi su:");
        for (Film k : films)
            System.out.println(k);
    }

    public void printSerials() {
        System.out.println("Serije su:");
        for (Serial k : serials)
            System.out.println(k);
    }

    public ObservableList<Serial> getSerials() {
        return serials;
    }

    public void setSerials(ObservableList<Serial> serials) {
        this.serials = serials;
    }

    public Serial getCurrentSerial() {
        return currentSerial.get();
    }

    public ObjectProperty<Serial> currentSerialProperty() {
        return currentSerial;
    }

    public void setCurrentSerial(Serial currentSerial) {
        this.currentSerial.set(currentSerial);
    }

    public ObservableList<Season> getSeasone() {
        return seasone;
    }

    public void setSeasone(ObservableList<Season> seasone) {
        this.seasone = seasone;
    }

    public Season getCurrentSeasone() {
        return currentSeasone.get();
    }

    public ObjectProperty<Season> currentSeasoneProperty() {
        return currentSeasone;
    }

    public void setCurrentSeasone(Season currentSeasone) {
        this.currentSeasone.set(currentSeasone);
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.set(currentUser);
    }

    public void addNewUser(User user) throws SQLException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        PreparedStatement unosKorisnika = conn.prepareStatement("INSERT INTO korisnici VALUES (?, ?, ?, ?, ?, ?, ?, ? )");
        unosKorisnika.setString(2, user.getName());
        unosKorisnika.setString(3, user.getSurname());
        unosKorisnika.setString(4, user.getAddress());
        unosKorisnika.setString(5, user.getMobileNumber());
        unosKorisnika.setDate(6, Date.valueOf(simpleDateFormat.format(user.getMembershipDate())));
        unosKorisnika.setString(7, user.getUserName());
        unosKorisnika.setString(8, user.getPassword());
        unosKorisnika.executeUpdate();
    }

    public ArrayList<String> getCurrentUserNames(){
        ArrayList<String > korisnickaImena = new ArrayList<>();
        for (User k: users
             ) {
            korisnickaImena.add(k.getUserName());
        }
        return korisnickaImena;
    }

    public void addNewRent(String korisnikId, String filmId, String serijaId, String sezonaId, int logicka) throws SQLException {
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

    public  void getRents() throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT korisnickoIme, filmNaziv, serijaNaziv, sezonaNaziv, datumIznajmljivanja, aktivnaNarudzba FROM najam");
        ResultSet rs = stm.executeQuery();

        getCurrentUser().getOrderList().clear();
        getCurrentUser().getHistoryList().clear();

        System.out.println("Velicina prije " + getCurrentUser().getOrderList().size());
        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            System.out.println(String.valueOf(rs.getInt(6)));
            System.out.println(getCurrentUser().getUserName());

            if(rs.getString(1).equals(getCurrentUser().getUserName())) {
                if(rs.getString(2) != null){
                    Film f = findFilm(rs.getString(2));
                    if(rs.getInt(6)==1)
                       // System.out.println(f.getTitle());

                    getCurrentUser().addInOrderList(f);
                    getCurrentUser().addInHistoryList(f);
                }
                else{
                    Serial s = findSerial(rs.getString(3));
                    if(rs.getInt(6)==1)
                        getCurrentUser().addInOrderList(s);
                    getCurrentUser().addInHistoryList(s);
                }
            }


        }

        System.out.println("Velicina poslije " + getCurrentUser().getOrderList().size());
    }

    public void returnRent(Object o) throws SQLException {
        if(o instanceof Film){
            Film f = findFilm(((Film) o).getTitle());
            PreparedStatement vracanjeNajma = conn.prepareStatement("UPDATE najam SET aktivnaNarudzba=? WHERE korisnickoIme = ? AND  filmNaziv = ?");
            vracanjeNajma.setInt(1,0);
            vracanjeNajma.setString(2, getCurrentUser().getUserName());
            vracanjeNajma.setString(3, ((Film) o).getTitle());
            vracanjeNajma.executeUpdate();

        }
        else{
            Serial s = findSerial(((Serial) o).getTitle());
            PreparedStatement vracanjeNajma = conn.prepareStatement("UPDATE najam SET aktivnaNarudzba=? WHERE korisnickoIme = ? AND  serijaNaziv = ?");
            vracanjeNajma.setInt(1,0);
            vracanjeNajma.setString(2, getCurrentUser().getUserName());
            vracanjeNajma.setString(3, ((Serial) o).getTitle());
            vracanjeNajma.executeUpdate();
        }

       // getCurrentUser().getOrderList().remove(o);
    }

    /*
    metode interfejsa Documents
    loadXML() - provjerava validnost film.xml fajla te na osnovu podataka popunjava tabelu baze filmovi
    loadTXT() - provjerava validnost series.txt datoteke te sadrzaj zapisuje u tabelu serije
    recordXML() - zapisuje serije pohranjene u listi klase SZ u serials.txt datoteku
     */

    @Override
    public void loadXML() throws SQLException {
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
                f.setTitle(((Element) dijete).getElementsByTagName("title").item(0).getTextContent());
                f.setDirector(((Element) dijete).getElementsByTagName("director").item(0).getTextContent());
                f.setType(((Element) dijete).getElementsByTagName("type").item(0).getTextContent());
                f.setPrice(Double.parseDouble(((Element) dijete).getElementsByTagName("price").item(0).getTextContent()));
                f.setTime(Integer.parseInt(((Element) dijete).getElementsByTagName("time").item(0).getTextContent()));

                boolean logicka = true;
                for(int j = 0; j< filmsList.size(); j++){
                    if(filmsList.get(j).getTitle().equals(f.getTitle()))  logicka=false;

                }

                if(logicka){
                PreparedStatement unosFilma = conn.prepareStatement("INSERT INTO filmovi VALUES (?, ?, ?, ?, ?, ?)");
                unosFilma.setString(2, f.getTitle());
                unosFilma.setString(3, f.getDirector());
                unosFilma.setString(4, f.getType());
                unosFilma.setDouble(5, f.getPrice());
                unosFilma.setInt(6, f.getTime());
                unosFilma.executeUpdate();}

            }
        }

    }

    @Override
    public void loadTXT(){

        Scanner ulaz=null;
        ArrayList<Serial> serije = new ArrayList<>();

        try {
            ulaz = new Scanner(new FileReader("/home/samra/IdeaProjects/aplikacijaZaVideoteku/src/sample/series.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka series.txt ne postoji ili se ne može otvoriti");
            System.out.println("Greška: " + e);
        }

        try {
            if(ulaz!=null)
                while (ulaz.hasNext()) {
                    String[] line = ulaz.nextLine().split(",");
                    String naziv=line[0].trim();
                    String reziser = line[1].trim();
                    String zanr = line[2].trim();
                    Serial s = new Serial(naziv,reziser,zanr);

                    boolean logicka = true;
                    for(int j = 0; j< serialsList.size(); j++){
                        if(serialsList.get(j).getTitle().equals(s.getTitle()))  logicka=false;

                    }

                    if(logicka){

                        PreparedStatement unoseSerije = conn.prepareStatement("INSERT INTO serije VALUES (?, ?, ?, ? )");
                        unoseSerije.setString(2, s.getTitle());
                        unoseSerije.setString(3, s.getDirector());
                        unoseSerije.setString(4, s.getType());
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

    @Override
    public void recordXML(){
        try{

            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("serials.xml"));
            izlaz.writeObject(sz);
            izlaz.close();

        }
        catch (Exception e){
            System.out.println("Greška: " + e);
        }
    }
}
