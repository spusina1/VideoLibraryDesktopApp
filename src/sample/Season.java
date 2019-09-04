package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Season {
    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleIntegerProperty time = new SimpleIntegerProperty(0);
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);
    private SimpleStringProperty serialId = new SimpleStringProperty("");

    public Season() {
    }

    public Season(String title, int time, double price, String serialId) {
        this.title = new SimpleStringProperty(title);
        this.time = new SimpleIntegerProperty(time);
        this.price = new SimpleDoubleProperty(price);
        this.serialId = new SimpleStringProperty(serialId);
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
        return title.get()+ ", " + time.get() + "min, " + price.get() + "KM";
    }

    public String getSerialId() {
        return serialId.get();
    }

    public SimpleStringProperty serialIdProperty() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId.set(serialId);
    }
}
