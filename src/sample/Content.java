package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Content {


    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleStringProperty director = new SimpleStringProperty("");
    private SimpleStringProperty type = new SimpleStringProperty("");

    public Content() {
    }

    public Content(String title, String director, String type) {
        this.title =new SimpleStringProperty(title);
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
}
