module com.arkeo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.activation; // Add this line to require javax.activation module

    requires fr.brouillard.oss.cssfx;
    requires atlantafx.base;
    requires javafx.graphics;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.logging;
    requires java.desktop;
    requires com.calendarfx.view;
    requires ical4j.core;

    opens com.example.controller to javafx.fxml;

    exports com.example;
}
