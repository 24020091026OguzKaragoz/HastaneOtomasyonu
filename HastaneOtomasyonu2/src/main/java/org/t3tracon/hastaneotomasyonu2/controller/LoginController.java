package org.t3tracon.hastaneotomasyonu2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.t3tracon.hastaneotomasyonu2.db.DatabaseManager;
import org.t3tracon.hastaneotomasyonu2.model.Kullanici;
import org.t3tracon.hastaneotomasyonu2.model.KullaniciTipi;
import org.t3tracon.hastaneotomasyonu2.util.SceneChanger;

public class LoginController {

    @FXML private TextField danismaTcField;
    @FXML private PasswordField danismaParolaField;
    @FXML private Label danismaErrorLabel;

    @FXML private TextField hastaTcField;
    @FXML private PasswordField hastaParolaField;
    @FXML private Label hastaErrorLabel;

    @FXML private TextField doktorTcField;
    @FXML private PasswordField doktorParolaField;
    @FXML private Label doktorErrorLabel;

    private DatabaseManager dbManager = DatabaseManager.getInstance();

    @FXML
    protected void onDanismaLogin() {
        handleLogin(danismaTcField.getText(), danismaParolaField.getText(), KullaniciTipi.DANISMA, danismaErrorLabel, "danisma.fxml", "Danışma Paneli");
    }

    @FXML
    protected void onHastaLogin() {
        handleLogin(hastaTcField.getText(), hastaParolaField.getText(), KullaniciTipi.HASTA, hastaErrorLabel, "hasta.fxml", "Hasta Paneli");
    }

    @FXML
    protected void onDoktorLogin() {
        handleLogin(doktorTcField.getText(), doktorParolaField.getText(), KullaniciTipi.DOKTOR, doktorErrorLabel, "doktor.fxml", "Doktor Paneli");
    }

    private void handleLogin(String tc, String parola, KullaniciTipi tip, Label errorLabel, String fxml, String title) {
        if (tc.isEmpty() || parola.isEmpty()) {
            errorLabel.setText("Lütfen tüm alanları doldurun.");
            return;
        }

        Kullanici kullanici = dbManager.login(tc, parola, tip);
        if (kullanici != null) {
            Stage currentStage = (Stage) errorLabel.getScene().getWindow();
            SceneChanger.changeScene(currentStage, fxml, title, kullanici);
        } else {
            errorLabel.setText("Hatalı TC veya Parola!");
        }
    }
}
