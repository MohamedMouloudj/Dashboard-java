package com.example;

import atlantafx.base.theme.PrimerLight;
import com.example.view.GetImage;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws Exception {

        CSSFX.start(); // For hot reload of css files
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/dashboard.fxml"));
        Parent root = loader.load();
        root.requestFocus();
        Scene scene = new Scene(root);

        stage.getIcons().add(GetImage.getImage(this, "images/randomLogo.png"));
        stage.setTitle("Dashboard");

        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        launch(args);
    }

}
