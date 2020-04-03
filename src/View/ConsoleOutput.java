package View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ConsoleOutput  {
    private static TextArea outputArea;
    private ScrollPane scrollPane;
    private VBox vBox,nestedVBox;
    private Label consoleOutputLabel;


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

    public String getText(){
        return outputArea.getText();
    }

    public static TextArea getOutputArea(){
        return outputArea;
    }

    public VBox getConsoleOutput(){
        return vBox;
    }
}
