package sample;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Serial {
    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleStringProperty director = new SimpleStringProperty("");
    private SimpleStringProperty type = new SimpleStringProperty("");
    private ObservableList<Season> season = FXCollections.observableArrayList();


    public Serial() {
    }

    public Serial(String  naziv, String director, String type) {
        this.title = new SimpleStringProperty(naziv);
        this.director = new SimpleStringProperty(director);
        this.type = new SimpleStringProperty(type);
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


    public ObservableList<Season> getSeason() {
        return season;
    }

    public void setSeason(ObservableList<Season> season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return title.get()+ ", " + director.get() + ", " + type.get();
    }
}
