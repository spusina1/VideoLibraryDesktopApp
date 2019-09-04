package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SZ implements Serializable {
    private List<Serial> serials = new ArrayList<>();

    public List<Serial> getSerials() {
        return serials;
    }

    public void setSerials(List<Serial> sezone) {
        this.serials = sezone;
    }
}
