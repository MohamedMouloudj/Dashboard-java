package com.example.view;

import javafx.scene.image.Image;

import java.util.Objects;

public class GetImage {
    public static Image getImage(Object startObj, String path) {
        return new Image(Objects.requireNonNull(startObj.getClass().getResource(path)).toExternalForm());
    }
}
