package sample;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Serial extends Content {

    private ObservableList<Season> season = FXCollections.observableArrayList();


    public Serial() {
    }

    public Serial(String  naziv, String director, String type) {
        super(naziv, director, type);
    }



    public ObservableList<Season> getSeason() {
        return season;
    }

    public void setSeason(ObservableList<Season> season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return getTitle()+ ", " + getDirector() + ", " + getType();
    }
}
