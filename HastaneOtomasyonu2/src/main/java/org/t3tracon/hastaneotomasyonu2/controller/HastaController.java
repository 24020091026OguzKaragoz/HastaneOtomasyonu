package org.t3tracon.hastaneotomasyonu2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.t3tracon.hastaneotomasyonu2.db.DatabaseManager;
import org.t3tracon.hastaneotomasyonu2.model.Hasta;
import org.t3tracon.hastaneotomasyonu2.model.Kullanici;
import org.t3tracon.hastaneotomasyonu2.util.SceneChanger;

public class HastaController implements SceneChanger.UserAware {

    @FXML private Label welcomeLabel;
    @FXML private Label sikayetLabel;

    private Kullanici currentUser;
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public void setKullanici(Kullanici kullanici) {
        this.currentUser = kullanici;
        welcomeLabel.setText("Hoşgeldiniz, " + kullanici.getAd() + " " + kullanici.getSoyad());
        checkRegistration();
    }

    private void checkRegistration() {
        Hasta hastaKaydi = dbManager.getHastaByTc(currentUser.getTc());
        if (hastaKaydi != null) {
            sikayetLabel.setText("Kayıtlı Şikayetiniz: " + hastaKaydi.getSikayet());
        } else {
            sikayetLabel.setText("Henüz bir kaydınız bulunmamaktadır.");
        }
    }

    @FXML
    protected void onCikis() {
        Stage currentStage = (Stage) welcomeLabel.getScene().getWindow();
        SceneChanger.changeScene(currentStage, "login.fxml", "Hastane Otomasyonu - Giriş", null);
    }
}
