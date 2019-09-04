package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;




import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class LoginController implements Initializable{


    public TextField userName;
    public PasswordField password;
    public TextField name;
    public TextField surname;
    public TextField mobileNumber;
    public TextField address;
    public TextField newUserName;
    public PasswordField newPassword;
    public ComboBox language;
    public GridPane main;


    public static Locale appLanguage = new Locale("bs", "BA");;


    private VideotekaModel model;


    public LoginController(VideotekaModel m) {
        model = m;
    }




    @FXML
    public void initialize() {
        userName.getStyleClass().add("poljeNijeIspravno");

    userName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (n.isEmpty()) {
                    userName.getStyleClass().add("poljeNijeIspravno");
                } else {
                    userName.getStyleClass().removeAll("poljeNijeIspravno");
                }
            }
        });
    }



    public void logIn(ActionEvent actionEvent) throws SQLException {


        //otvaranje novog prozora
        //System.out.println(userName.getText());


        User currentUser = model.findUser(userName.getText(), password.getText());
        model.setCurrentUser(currentUser);
        if(currentUser ==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Greška!");
            alert.setContentText("Nepostojeci korisnicki racun!");
            alert.show();
        }
        else{

            model.setCurrentUser(currentUser);

        if(!logInValidation()){ userName.getStyleClass().add("poljeNijeIspravno");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Greška!");
            alert.setContentText("Korisničko name mora počinjati s velikim slovom abecede!");
            alert.show();

        }else {
            userName.getStyleClass().removeAll("poljeNijeIspravno");
            try{

               System.out.println(model.getCurrentUser());

               Locale.setDefault(appLanguage);
                System.out.println("Trenutni language " + appLanguage);
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Parent root= FXMLLoader.load(getClass().getResource("menu.fxml"), bundle);
                Stage stage=new Stage();
                stage.setTitle("Glavni izbornik");
                stage.setScene(new Scene(root, 300, 300));
                //stage.setResizable(false);
                stage.show();


                userName.clear();
                password.clear();
                name.clear();
                surname.clear();
                address.clear();
                mobileNumber.clear();
                newPassword.clear();
                newUserName.clear();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }

    }

    private boolean logInValidation(){

        String kIme = userName.getText();
        if (kIme.isEmpty() || kIme.charAt(0)<'A' || kIme.charAt(0)>'Z') {
            return false;

        }
        return true;
    }


    private boolean logicka1=false, logicka2=false, logicka3=false, logicka4=false, logicka5=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {




        language.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if(newValue.equals("Bosanski")) bos();
                else  if(newValue.equals("English")) eng();
                else if (newValue.equals("Deutsch")) njem();
                System.out.println("Value is: "+newValue);
            }
        });

        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {

                if (validName(n)) {
                    name.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka1=true;


                } else {
                    name.getStyleClass().add("poljeNijeIspravno");
                    logicka1=false;

                }

            }
        });

        surname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validSurname(n)) {
                    surname.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka2=true;

                } else {
                    surname.getStyleClass().add("poljeNijeIspravno");
                    logicka2=false;

                }
            }
        });





        mobileNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validNumber(n)) {
                    mobileNumber.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka3=true;

                } else {
                    mobileNumber.getStyleClass().add("poljeNijeIspravno");
                    logicka3=false;

                }
            }
        });

        newPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (newPassword.getText().length()<8) {
                    newPassword.getStyleClass().add("poljeNijeIspravno");
                    logicka4=false;

                } else {
                    newPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    logicka4=true;

                }
            }
        });




    }

    private boolean validName(String n) {
        if (n.trim().isEmpty() || n.length() > 20 || n.charAt(0) < 65 || n.charAt(0) > 90){ return false; }
        for (int i = 1; i < n.length(); i++)
            if (n.charAt(i) < 97 || n.charAt(i) > 122){  return false; }

        return true;
    }

    private boolean validSurname(String n) {
        if (n.trim().isEmpty() || n.length() > 20 || n.charAt(0) < 65 || n.charAt(0) > 90) { return false;}
        for (int i = 1; i < n.length(); i++)
            if (n.charAt(i) < 97 || n.charAt(i) > 122) { return false;}

        return true;
    }


    private boolean validBirthDate(LocalDate d) {
        LocalDate td = LocalDate.now();
        if (d.isAfter(td) || d.isEqual(td)) {  return false;}

            return  true;
    }

    private boolean validNumber(String n) {
        if(n.length()<9 || n.length()>10) return false;
        String prviDio=n.substring(0,3);
        String drugiDio=n.substring(3);

        try{
            int prvi= Integer.parseInt(prviDio);
            int drugi = Integer.parseInt(drugiDio);
            if(prvi>99){  return  false;}
        }catch (Exception e){

            return false;
        }


        return true;
    }


    public void signUp(javafx.event.ActionEvent actionEvent) {


        if(!( logicka1 && logicka2 && logicka3 && logicka4)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Podaci nisu validni!");
            alert.setContentText("Provjerite da li ste ispravno unijeli sve podatke!");
            alert.show();}
        else if(model.getCurrentUserNames().contains(newUserName.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Korisnicko name vec postoji!");
            alert.setContentText("Pokušajte unijeti neko drugo korisnicko name!");
            alert.show();}

        else{

            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            User newUser =  new User(name.getText(), surname.getText(), address.getText(), mobileNumber.getText(), new Date(), newUserName.getText(), newPassword.getText());
            System.out.println(newUser);
            try {
                model.addNewUser(newUser);
                model.setCurrentUser(newUser);
                Locale.setDefault(appLanguage);
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Parent root= FXMLLoader.load(getClass().getResource("menu.fxml"), bundle);
                Stage stage=new Stage();
                stage.setTitle("Glavni izbornik");
                stage.setScene(new Scene(root, 300, 300));
                stage.show();


                userName.clear();
                password.clear();
                name.clear();
                surname.clear();
                address.clear();
                mobileNumber.clear();
                newPassword.clear();
                newUserName.clear();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}

    private void selectLanguage(Locale j) {
        Stage primaryStage = (Stage)main.getScene().getWindow();
        appLanguage = j;
        System.out.println(appLanguage);
        Locale.setDefault(appLanguage);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"), bundle);
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }

    public void bos() {
        selectLanguage(new Locale("bs","BA"));
    }

    public void eng() {
        selectLanguage(new Locale("en","US"));
    }


    public void njem() {
        selectLanguage(new Locale("de", "DE"));
    }

}
