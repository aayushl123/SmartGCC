package View;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FrequentlyUsedWindow {
    private VBox frequentlyUsedBox;

    public void setFrequentlyUsedBox() {
        Label boxTitle = new Label("Last used actions");
        frequentlyUsedBox = new VBox(boxTitle);
        frequentlyUsedBox.setPrefWidth(300);
        frequentlyUsedBox.setPrefHeight(600);
    }

    public VBox getFrequentlyUsedBox(){
        return frequentlyUsedBox;
    }
}
