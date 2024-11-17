package com.example.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.example.view.GetImage;
import com.example.view.Switch;

import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Controller implements Initializable {
    public StackPane root;
    @FXML
    public AnchorPane childContainerEmployeeForm;
    public FlowPane cardContainer;
    public AnchorPane cardStudents;
    public AnchorPane cardWorkers;
    public AnchorPane cardIncome;
    public ImageView cardIncomeImage;
    public ImageView cardWorkersImage;
    public ImageView cardStudentsImage;
    public Label cardDescriptionStudents;
    public Label cardDescriptionWorkers;
    public Label cardDescriptionIncome;
    public LineChart incomeGraph;
    public BarChart ageGraph;
    public ColumnConstraints ageGridColumn;
    public ColumnConstraints incomeGridColumn;
    public GridPane graphsContainer;
    public VBox calenderContainer;
    public Button openCalender;
    public AnchorPane calenderPane;
    public NumberAxis YAxisAge;
    public NumberAxis YAxisIncome;
    public Button saveTimeTable;
    //////////////////////////////////////////


    @FXML
    private AnchorPane dashboardPane;

    private String selectedPanel = "dashboard";

    private final PseudoClass theme = PseudoClass.getPseudoClass("dark");
    private Switch themeButton ;

    @FXML
    private NavbarController navbar;

    private Button dashboardButton;
    private Button logOutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(navbar.getButtons());
        for (String buttonName : navbar.getButtons().keySet()){
            switch (buttonName){
                case "dashboard":
                    this.dashboardButton=navbar.getButtons().get(buttonName);
                    navbar.getButtons().get(buttonName).setOnAction(this::switchPanel);
                    break;
                case "logOut":
                    this.logOutButton=navbar.getButtons().get(buttonName);
                    navbar.getButtons().get(buttonName).setOnAction(this::logOut);
                    break;
            }
        }
        selectPanel("dashboard");
        themeButton=navbar.getThemeBtn();
        themeButton.selectedProperty().addListener((obs, old, val) -> {
            if (val) {
                Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
                cardStudents.pseudoClassStateChanged(theme, true);
                cardWorkers.pseudoClassStateChanged(theme, true);
                cardIncome.pseudoClassStateChanged(theme, true);
                cardIncomeImage.setImage(GetImage.getImage(this, "/com/example/images/payement.png"));
                cardWorkersImage.setImage(GetImage.getImage(this, "/com/example/images/employe.png"));
                cardStudentsImage.setImage(GetImage.getImage(this, "/com/example/images/user.png"));
            } else {
                Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
                cardStudents.pseudoClassStateChanged(theme, false);
                cardWorkers.pseudoClassStateChanged(theme, false);
                cardIncome.pseudoClassStateChanged(theme, false);
                cardIncomeImage.setImage(GetImage.getImage(this, "/com/example/images/payementdark.png"));
                cardWorkersImage.setImage(GetImage.getImage(this, "/com/example/images/employedark.png"));
                cardStudentsImage.setImage(GetImage.getImage(this, "/com/example/images/userdark.png"));
            }
        });


        int numberOfClients = 60;
        int numberOfWorkers = 10;
        double thisMonthlyIncome = 50000.0;
        cardDescriptionStudents.setText(numberOfClients > 1 ? numberOfClients + " clients" : numberOfClients + " client");
        cardDescriptionWorkers.setText(numberOfWorkers > 1 ?  numberOfWorkers+ " workers" : numberOfWorkers + " worker");
        cardDescriptionIncome.setText(String.format("%.2f", thisMonthlyIncome) + " DA");

        incomeGraph.getYAxis().setLabel("Amount (DA)");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        series.setName("Monthly income");
        series.getData().add(new XYChart.Data<>(Month.JANUARY.toString(),20000));
        series.getData().add(new XYChart.Data<>(Month.FEBRUARY.toString(),50000));
        series.getData().add(new XYChart.Data<>(Month.MARCH.toString(),30000));
        series.getData().add(new XYChart.Data<>(Month.APRIL.toString(),40000));
        incomeGraph.getData().add(series);
        incomeGraph.setTitle("Monthly income");
        YAxisIncome.setLowerBound(0);
        YAxisIncome.setTickUnit(5000);

        ageGraph.getYAxis().setLabel("Clients number");
        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        series1.setName("Ages of clients");
        series1.getData().add(new XYChart.Data<>("18", 10));
        series1.getData().add(new XYChart.Data<>("22", 20));
        series1.getData().add(new XYChart.Data<>("25", 30));
        ageGraph.getData().add(series1);
        ageGraph.setTitle("Number of clients by age");
        YAxisAge.setLowerBound(0);
        YAxisAge.setTickUnit(20);

        TimeTable.initCalenderView(calenderContainer, saveTimeTable);
    }

    @FXML
    public void switchPanel(ActionEvent event) {
        if (event.getSource() == dashboardButton) {
            selectPanel("dashboard");
        }
    }

    @FXML
    public void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Voulez-vous vraiment vous déconnecter ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            ((Stage)dashboardButton.getScene().getWindow()).close();
        }
    }

    @FXML
    private void handleReturn(ActionEvent e) {
        selectPanel(selectedPanel);
    }


    @FXML private void handleOpenCalender(ActionEvent e) {
        selectPanel("calender");
    }

    private void selectPanel(String panel) {
        PseudoClass pressed = PseudoClass.getPseudoClass("focused");
        dashboardPane.setVisible(false);
        calenderPane.setVisible(false);

        dashboardButton.pseudoClassStateChanged(pressed, false);

        switch (panel) {
            case "dashboard":
                dashboardPane.setVisible(true);
                dashboardButton.pseudoClassStateChanged(pressed, true);
                selectedPanel = "dashboard";
                break;
            case "calender":
                calenderPane.setVisible(true);
                break;
        }
    }
}
