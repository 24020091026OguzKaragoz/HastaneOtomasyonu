package org.t3tracon.hastaneotomasyonu2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.t3tracon.hastaneotomasyonu2.db.DatabaseManager;
import org.t3tracon.hastaneotomasyonu2.model.Kullanici;
import org.t3tracon.hastaneotomasyonu2.util.SceneChanger;

import java.sql.SQLException;

public class DanismaController implements SceneChanger.UserAware {

    @FXML private TextField adField;
    @FXML private TextField soyadField;
    @FXML private TextField tcField;
    @FXML private TextArea sikayetArea;
    @FXML private Label statusLabel;

    private Kullanici currentUser;
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public void setKullanici(Kullanici kullanici) {
        this.currentUser = kullanici;
    }

    @FXML
    protected void onKaydet() {
        String ad = adField.getText();
        String soyad = soyadField.getText();
        String tc = tcField.getText();
        String sikayet = sikayetArea.getText();

        if (ad.isEmpty() || soyad.isEmpty() || tc.isEmpty()) {
            statusLabel.setText("Lütfen zorunlu alanları doldurun.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            dbManager.insertHasta(ad, soyad, tc, sikayet);
            statusLabel.setText("Hasta kaydedildi. Giriş şifresi: " + tc);
            statusLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } catch (SQLException e) {
            statusLabel.setText("Kayıt sırasında hata oluştu: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    protected void onCikis() {
        Stage currentStage = (Stage) statusLabel.getScene().getWindow();
        SceneChanger.changeScene(currentStage, "login.fxml", "Hastane Otomasyonu - Giriş", null);
    }

    private void clearFields() {
        adField.clear();
        soyadField.clear();
        tcField.clear();
        sikayetArea.clear();
    }
}
