package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;


public class User {


    private SimpleStringProperty name = new SimpleStringProperty("");
    private  SimpleStringProperty surname = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty mobileNumber = new SimpleStringProperty("");
    private Date membershipDate = new Date();
    private SimpleStringProperty userName = new SimpleStringProperty("");
    private SimpleStringProperty password = new SimpleStringProperty("");

    ObservableList<Object> orderList = FXCollections.observableArrayList();
    ObservableList<Object> historyList = FXCollections.observableArrayList();


    public User() {
    }

    public User(String name, String surname, String address, String mobileNumber, Date membershipDate, String userName, String password) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.address = new SimpleStringProperty(address);
        this.mobileNumber = new SimpleStringProperty(mobileNumber);
        this.membershipDate = membershipDate;
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);

    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getMobileNumber() {
        return mobileNumber.get();
    }

    public SimpleStringProperty mobileNumberProperty() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber.set(mobileNumber);
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }


    @Override
    public String toString() {
        return name.get() + " " + surname.get() + ", " + address.get() + ", " + mobileNumber.get() + ", " + membershipDate.toString() + userName.get() + password.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    

    public void addInOrderList(Object o){
        this.orderList.add(o);
    }

    public void addInHistoryList(Object o){
        this.historyList.add(o);
    }

    public ObservableList<Object> getOrderList() {
        return orderList;
    }

    public void setOrderList(ObservableList<Object> orderList) {
        this.orderList = orderList;
    }

    public ObservableList<Object> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(ObservableList<Object> historyList) {
        this.historyList = historyList;
    }
}
