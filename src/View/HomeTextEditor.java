package View;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class HomeTextEditor {
    private TextArea editorArea;
    private ScrollPane scrollPane;
    private HBox hbox;
    private FrequentlyUsedWindow frequentlyUsedWindow;


    public void setTextEditor(){
        editorArea = new TextArea();
        frequentlyUsedWindow = new FrequentlyUsedWindow();
        scrollPane = new ScrollPane();

        editorArea.setPrefWidth(900);
        editorArea.setPrefHeight(450);
        scrollPane.setContent(editorArea);
        frequentlyUsedWindow.setFrequentlyUsedBox();

        hbox = new HBox(scrollPane, frequentlyUsedWindow.getFrequentlyUsedBox());
    }

    public String getText(){
        return editorArea.getText();
    }

    public TextArea getTextArea(){
        return editorArea;
    }

    public HBox getTextEditor(){
        return hbox;
    }
}
