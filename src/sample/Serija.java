package sample;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Serija {
    private SimpleStringProperty naziv = new SimpleStringProperty("");
    private SimpleStringProperty reziser = new SimpleStringProperty("");
    private SimpleStringProperty zanr = new SimpleStringProperty("");
    private ObservableList<Sezona> sezone = FXCollections.observableArrayList();


    public Serija() {
    }

    public Serija(String  naziv, String reziser, String zanr) {
        this.naziv = new SimpleStringProperty(naziv);
        this.reziser = new SimpleStringProperty(reziser);
        this.zanr = new SimpleStringProperty(zanr);
    }

    public String getNaziv() {
        return naziv.get();
    }

    public SimpleStringProperty nazivProperty() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public String getReziser() {
        return reziser.get();
    }

    public SimpleStringProperty reziserProperty() {
        return reziser;
    }

    public void setReziser(String reziser) {
        this.reziser.set(reziser);
    }

    public String getZanr() {
        return zanr.get();
    }

    public SimpleStringProperty zanrProperty() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr.set(zanr);
    }


    @Override
    public String toString() {
        return naziv.get()+ ", " + reziser.get() + ", " + zanr.get();
    }
}
