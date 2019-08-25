package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Film {

    private SimpleStringProperty naziv = new SimpleStringProperty("");
    private SimpleStringProperty reziser = new SimpleStringProperty("");
    private SimpleStringProperty zanr = new SimpleStringProperty("");
    private SimpleIntegerProperty vrijemeTrajanja = new SimpleIntegerProperty(0);
    private SimpleDoubleProperty cijena = new SimpleDoubleProperty(0.0);

    public Film() {
    }

    public Film(String naziv, String reziser, String zanr, double cijena, int vrijemeTrajanja) {
        this.naziv =new SimpleStringProperty(naziv);
        this.reziser = new SimpleStringProperty(reziser);
        this.zanr = new SimpleStringProperty(zanr);
        this.vrijemeTrajanja = new SimpleIntegerProperty(vrijemeTrajanja);
        this.cijena = new SimpleDoubleProperty(cijena);
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

    public double getCijena() {
        return cijena.get();
    }

    public SimpleDoubleProperty cijenaProperty() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena.set(cijena);
    }

    @Override
    public String toString() {
        return naziv.get()+ ", " + reziser.get() + ", " + zanr.get() + ", " + cijena.get() + "KM";
    }

    public int getVrijemeTrajanja() {
        return vrijemeTrajanja.get();
    }

    public SimpleIntegerProperty vrijemeTrajanjaProperty() {
        return vrijemeTrajanja;
    }

    public void setVrijemeTrajanja(int vrijemeTrajanja) {
        this.vrijemeTrajanja.set(vrijemeTrajanja);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return naziv.equals(film.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv, reziser, zanr, vrijemeTrajanja, cijena);
    }
}
