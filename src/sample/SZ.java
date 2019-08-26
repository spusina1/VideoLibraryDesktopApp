package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SZ implements Serializable {
    private List<Serija> serije = new ArrayList<>();

    public List<Serija> getSerije() {
        return serije;
    }

    public void setSerije(List<Serija> sezone) {
        this.serije = sezone;
    }
}
