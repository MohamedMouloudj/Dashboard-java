package com.example.view;

import atlantafx.base.controls.ToggleSwitch;
import javafx.geometry.HorizontalDirection;
import javafx.scene.image.ImageView;

public class Switch extends ToggleSwitch{
    private final String text1;
    private final String text2;

    public Switch(String text1, String text2) {
        super(text1);
        this.text1 = text1;
        this.text2 = text2;
        this.selectedProperty().addListener(
                (obs, old, val) -> this.setText(val ? this.text2 : this.text1)
        );
        this.setLabelPosition(HorizontalDirection.RIGHT);
        this.setSelected(false);
    }
    public void setGraphic(ImageView img1, ImageView img2) {
        this.selectedProperty().addListener((obs, old, val) -> this.setGraphic(val ? img1 : img2)
        );
    }
}
