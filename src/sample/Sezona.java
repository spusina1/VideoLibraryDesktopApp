package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Sezona {
    private SimpleStringProperty naziv = new SimpleStringProperty("");
    private SimpleIntegerProperty vrijeTrajanja = new SimpleIntegerProperty(0);
    private SimpleDoubleProperty cijena = new SimpleDoubleProperty(0.0);
    private SimpleStringProperty idSerije = new SimpleStringProperty("");

    public Sezona() {
    }

    public Sezona(String naziv, int vrijeTrajanja, double cijena, String idSerije) {
        this.naziv = new SimpleStringProperty(naziv);
        this.vrijeTrajanja = new SimpleIntegerProperty(vrijeTrajanja);
        this.cijena = new SimpleDoubleProperty(cijena);
        this.idSerije = new SimpleStringProperty(idSerije);
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

    public int getVrijeTrajanja() {
        return vrijeTrajanja.get();
    }

    public SimpleIntegerProperty vrijeTrajanjaProperty() {
        return vrijeTrajanja;
    }

    public void setVrijeTrajanja(int vrijeTrajanja) {
        this.vrijeTrajanja.set(vrijeTrajanja);
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
        return naziv.get()+ ", " + vrijeTrajanja.get() + "min, " + cijena.get() + "KM";
    }

    public String getIdSerije() {
        return idSerije.get();
    }

    public SimpleStringProperty idSerijeProperty() {
        return idSerije;
    }

    public void setIdSerije(String idSerije) {
        this.idSerije.set(idSerije);
    }
}
