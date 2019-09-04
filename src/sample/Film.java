package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Film {

    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleStringProperty director = new SimpleStringProperty("");
    private SimpleStringProperty type = new SimpleStringProperty("");
    private SimpleIntegerProperty time = new SimpleIntegerProperty(0);
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);

    public Film() {
    }

    public Film(String title, String director, String type, double price, int time) {
        this.title =new SimpleStringProperty(title);
        this.director = new SimpleStringProperty(director);
        this.type = new SimpleStringProperty(type);
        this.time = new SimpleIntegerProperty(time);
        this.price = new SimpleDoubleProperty(price);
    }


    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDirector() {
        return director.get();
    }

    public SimpleStringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
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
        return title.get()+ ", " + director.get() + ", " + type.get() + ", " + price.get() + "KM";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return title.equals(film.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, director, type, time, price);
    }
}
