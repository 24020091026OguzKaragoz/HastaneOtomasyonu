package org.t3tracon.hastaneotomasyonu2.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.t3tracon.hastaneotomasyonu2.db.DatabaseManager;
import org.t3tracon.hastaneotomasyonu2.model.Hasta;
import org.t3tracon.hastaneotomasyonu2.model.Kullanici;
import org.t3tracon.hastaneotomasyonu2.util.SceneChanger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoktorController implements SceneChanger.UserAware {

    @FXML private TableView<Hasta> hastalarTable;
    @FXML private TableColumn<Hasta, String> adSoyadColumn;
    @FXML private TableColumn<Hasta, String> tcColumn;
    @FXML private TableColumn<Hasta, String> sikayetColumn;

    private Kullanici currentUser;
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public void setKullanici(Kullanici kullanici) {
        this.currentUser = kullanici;
        loadData();
    }

    @FXML
    public void initialize() {
        adSoyadColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getAd() + " " + cellData.getValue().getSoyad()));
        tcColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTc()));
        sikayetColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getSikayet()));
    }

    @FXML
    protected void onCikis() {
        Stage currentStage = (Stage) hastalarTable.getScene().getWindow();
        SceneChanger.changeScene(currentStage, "login.fxml", "Hastane Otomasyonu - Giriş", null);
    }

    @FXML
    protected void onHastaSil() {
        Hasta selectedHasta = hastalarTable.getSelectionModel().getSelectedItem();
        if (selectedHasta == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen silinecek bir hasta seçin.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hasta Silme Onayı");
        alert.setHeaderText("Hastayı silmek istediğinize emin misiniz?");
        alert.setContentText("Seçilen hasta: " + selectedHasta.getAd() + " " + selectedHasta.getSoyad());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                dbManager.deleteHasta(selectedHasta.getTc());
                loadData(); // Tabloyu yenile
                showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Hasta başarıyla silindi.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Hata", "Silme işlemi sırasında hata oluştu: " + e.getMessage());
            }
        }
    }

    private void loadData() {
        List<Hasta> hastalar = dbManager.getAllHastalar();
        ObservableList<Hasta> observableList = FXCollections.observableArrayList(hastalar);
        hastalarTable.setItems(observableList);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
