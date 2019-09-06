package sample;

import java.sql.SQLException;

public interface Documents {

    public void loadXML() throws SQLException;
    public void  loadTXT() throws SQLException ;
    public void recordXML() throws SQLException ;
}
