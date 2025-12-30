package org.t3tracon.hastaneotomasyonu2.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.t3tracon.hastaneotomasyonu2.HelloApplication;
import org.t3tracon.hastaneotomasyonu2.model.Kullanici;

import java.io.IOException;

public class SceneChanger {
    
    public static void changeScene(Stage currentStage, String fxmlFile, String title, Kullanici kullanici) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            
            // If the controller needs the user data, we can pass it here
            // This requires controllers to implement an interface or have a specific method
            Object controller = fxmlLoader.getController();
            if (controller instanceof UserAware) {
                ((UserAware) controller).setKullanici(kullanici);
            }
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface UserAware {
        void setKullanici(Kullanici kullanici);
    }
}
