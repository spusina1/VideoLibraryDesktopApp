package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Film extends Content{


    private SimpleIntegerProperty time = new SimpleIntegerProperty(0);
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);

    public Film() {
    }

    public Film(String title, String director, String type, double price, int time) {

        super(title, director, type);
        this.time = new SimpleIntegerProperty(time);
        this.price = new SimpleDoubleProperty(price);
    }



    public int getTime() {
        return time.get();
    }

    public SimpleIntegerProperty timeProperty() {
        return time;
    }

    public void setTime(int time) {
        this.time.set(time);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }


    @Override
    public String toString() {
        return getTitle() +  ", " + getDirector() + ", " + getType() + ", " + price.get() + "KM";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return getTitle().equals(film.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDirector(), getType(), time, price);
    }
}
