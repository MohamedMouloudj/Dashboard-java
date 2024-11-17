package com.example.controller;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.example.view.GetImage;
import com.example.view.Switch;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Map;

public class NavbarController extends VBox {

    private final PseudoClass extended=PseudoClass.getPseudoClass("extended");
    private boolean isAnimating = false;
    private boolean isExtended = false;
    private final Image sunIcon=GetImage.getImage(this,"/com/example/images/sun.png");
    private final Image moonIcon=GetImage.getImage(this,"/com/example/images/moon.png");
    private final ImageView themeIcon=new ImageView(sunIcon);

    @FXML
    private VBox centerContainer;

    @FXML
    private HBox logoContainer;

    @FXML
    private Label logoTitle;

    @FXML
    private VBox navbarContainer;

    @FXML
    private VBox bottomContainer;

    @FXML
    private Line separatorLine;

    @FXML
    private AnchorPane dashboardForm;

    @FXML
    private Button dashboardButton;
    @FXML
    private Button logOutButton;

    private final Switch themeBtn=new Switch("Light","Dark");

    public NavbarController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/com/example/views/navbar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        initializeNavbar();
    }

    private void initializeNavbar() {
        ImageView logo=new ImageView(GetImage.getImage(this,"/com/example/images/randomLogo.png"));
        logo.setFitHeight(50);
        logo.setFitWidth(50);
        logoTitle.setGraphic(logo);

        extendNavBar();

        themeIcon.setFitWidth(20);
        themeIcon.setFitHeight(20);
        themeBtn.selectedProperty().addListener((obs, old, val) -> {
            if (val) {
                themeIcon.setImage(moonIcon);
                Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            } else {
                themeIcon.setImage(sunIcon);
                Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
            }
        });
    }

    private void extendNavBar() {
        String dashboardBtnText=dashboardButton.getText();
        String logoTitleText=logoTitle.getText();
        String logOutBtnText=logOutButton.getText();

        dashboardButton.setText("");
        logoTitle.setText("");
        logOutButton.setText("");
        separatorLine.setEndX(10);
        bottomContainer.getChildren().remove(themeBtn);
        bottomContainer.getChildren().add(themeIcon);

        navbarContainer.setOnMouseEntered(e -> {
            if (!isAnimating && !isExtended) {
                isAnimating = true;
                isExtended = true;
                enterAnimation(dashboardBtnText,logOutBtnText, logoTitleText);
            }
        });

        navbarContainer.setOnMouseExited(e -> {
            if (!isAnimating && isExtended) {
                isAnimating = true;
                isExtended = false;
                exitAnimation();
            }
        });
    }
    private void enterAnimation(String dashboardBtnText, String logOutBtnText, String logoTitleText) {
        dashboardButton.setText(dashboardBtnText);
        dashboardButton.pseudoClassStateChanged(extended, true);
        logoTitle.setText(logoTitleText);
        logOutButton.setText(logOutBtnText);
        logoTitle.pseudoClassStateChanged(extended, true);
        logOutButton.pseudoClassStateChanged(extended, true);
        lineTransition(separatorLine, 10, 190, () -> {
            bottomContainer.getChildren().add(themeBtn);
            bottomContainer.getChildren().remove(themeIcon);
            navbarContainer.pseudoClassStateChanged(extended, true);
            isAnimating = false;
        });
    }
    private void exitAnimation() {
        dashboardButton.setText("");
        dashboardButton.pseudoClassStateChanged(extended, false);
        logoTitle.setText("");
        logoTitle.pseudoClassStateChanged(extended, false);
        logOutButton.setText("");
        logOutButton.pseudoClassStateChanged(extended, false);
        navbarContainer.pseudoClassStateChanged(extended, false);
        bottomContainer.getChildren().remove(themeBtn);
        bottomContainer.getChildren().add(themeIcon);
        lineTransition(separatorLine, 190, 10, () -> {
            isAnimating = false;
        });
    }

    private void lineTransition(Line line, double fromEndX, double toEndX, Runnable onFinished) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue startKeyValue = new KeyValue(line.endXProperty(), fromEndX);
        KeyValue endKeyValue = new KeyValue(line.endXProperty(), toEndX);
        KeyFrame startFrame = new KeyFrame(Duration.ZERO, startKeyValue);
        KeyFrame endFrame = new KeyFrame(Duration.millis(150), endKeyValue);
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        timeline.setOnFinished(event -> onFinished.run());
        timeline.play();
    }
    public Map<String,Button> getButtons() {
        return Map.of("dashboard", dashboardButton,"logOut",logOutButton);
    }

    public Switch getThemeBtn() {
        return themeBtn;
    }
}
