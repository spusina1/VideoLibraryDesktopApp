package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsersController {

    private VideoLibraryModel model;

    public TextField userName;
    public TextField userSurname;
    public TextField userAddress;
    public TextField userMobileNumber;
    public TextField userMembershipDate;
    public TextField userMembershipExpirationDate;
    public ListView orderList;
    public ListView historyList;

    public UsersController(VideoLibraryModel m) {
        model = m;
    }

    @FXML
    public void initialize() throws SQLException {


        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        userName.setText(model.getCurrentUser().getName());
        userSurname.setText(model.getCurrentUser().getSurname());
        userAddress.setText(model.getCurrentUser().getAddress());
        userMobileNumber.setText(model.getCurrentUser().getMobileNumber());
        userMembershipDate.setText(simpleDateFormat.format(model.getCurrentUser().getMembershipDate()));

        int godinaIsteka = Integer.parseInt(userMembershipDate.getText().substring(0,4));
        godinaIsteka = godinaIsteka+1;
        String godina = String.valueOf(godinaIsteka);
        String mjesecIsteka = userMembershipDate.getText().substring(5,7);
        String danIsteka = userMembershipDate.getText().substring(8,10);

        try {
            Date datumIsteka = simpleDateFormat.parse(godina + "-" + mjesecIsteka + "-" + danIsteka);

            userMembershipExpirationDate.setText(simpleDateFormat.format(datumIsteka));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        model.getCurrentUser().getOrderList().clear();
        model.getCurrentUser().getHistoryList().clear();

        model.getRents();

        orderList.setItems(model.getCurrentUser().getOrderList());
        historyList.setItems(model.getCurrentUser().getHistoryList());

        historyList.setMouseTransparent(true);


    }

    /*
    Ova metoda pozivom metode iz klase VideotekaLibraryModel postavlja aktivnost izabranog sadrzaja na 0
    te ga brise iz liste iznajmljenih sadrzaja.
     */
    public void returnFilm(ActionEvent actionEvent) throws SQLException {
        orderList.getSelectionModel().getSelectedItem();
        model.returnRent(orderList.getSelectionModel().getSelectedItem());
        orderList.getItems().remove( orderList.getSelectionModel().getSelectedItem());
    }

    public void print(ActionEvent actionEvent) throws SQLException {
        try {
            new PrintReport().showReport(model.getConn(), model.getCurrentUser().getUserName());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

}
