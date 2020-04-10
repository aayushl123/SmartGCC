package View;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/**
 * Class for the text editor on the home screen.
 * @author sagarbhatia, karansharma
 */
public class HomeTextEditor {
    private TextArea editorArea;
    private ScrollPane scrollPane;
    private HBox hbox;
    private FrequentlyUsedWindow frequentlyUsedWindow;

    /**
     * Method to set the text editor properties.
     */
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

    /**
     * Method to get the text from the text area
     * @return text in the text area
     */
    public String getText(){
        return editorArea.getText();
    }

    /**
     * Method to get the text area.
     * @return text editor area.
     */
    public TextArea getTextArea(){
        return editorArea;
    }

    /**
     * Method to get the horizontal text box
     * @return text box
     */
    public HBox getTextEditor(){
        return hbox;
    }
}
