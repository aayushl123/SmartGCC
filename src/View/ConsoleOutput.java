package View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class to show the console output.
 * @author sagarbhatia, karansharma
 */
public class ConsoleOutput  {
    private static TextArea outputArea;
    private ScrollPane scrollPane;
    private VBox vBox,nestedVBox;
    private Label consoleOutputLabel;

    /**
     * Class to set the properties of the output console.
     * @author karansharma
     */
    public void setTextEditor(){
        outputArea = new TextArea();
        scrollPane = new ScrollPane();
        consoleOutputLabel = new Label("Console Output");
        consoleOutputLabel.setPrefWidth(1264);

        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 0.5;\n" +
                "-fx-border-style: stroke;\n";


        consoleOutputLabel.setStyle(cssLayout);
        consoleOutputLabel.setPadding(new Insets(5,5,5,5));

        outputArea.setEditable(false);
        outputArea.setText("Here is the output area ");
        outputArea.setMouseTransparent(true);
        outputArea.setFocusTraversable(true);
        outputArea.setPrefWidth(1264);
        outputArea.setPrefHeight(325);
        scrollPane.setContent(outputArea);

        nestedVBox=new VBox(scrollPane);

        vBox = new VBox(consoleOutputLabel,nestedVBox);

    }

    /**
     * Method to get the text of the console output.
     * @return text of the console output
     */
    public String getText(){
        return outputArea.getText();
    }

    /**
     * Method to get the text area of the console output
     * @return text area of console
     */
    public static TextArea getOutputArea(){
        return outputArea;
    }

    /**
     * Method to get the console output object.
     * @return console output object
     */
    public VBox getConsoleOutput(){
        return vBox;
    }
}
