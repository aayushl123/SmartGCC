package View;

import Backend.TermTester;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.time.Instant;
import java.util.*;

public class FrequentlyUsedWindow {
    private static VBox frequentlyUsedBox;
    private static HashMap<String, Long> actionTimeMap = new HashMap<>();
    private static Button[] actionButtons;
    private static boolean appStarted = true;
    private static File file;


    public void setFrequentlyUsedBox() {
        Label boxTitle = new Label("Last used actions");
        frequentlyUsedBox = new VBox(boxTitle);
        frequentlyUsedBox.setPrefWidth(300);
        frequentlyUsedBox.setPrefHeight(450);
    }

    public VBox getFrequentlyUsedBox(){
        return frequentlyUsedBox;
    }

    public static void setActionTiming(String action){
        long now = Instant.now().toEpochMilli();
        actionTimeMap.put(action, now);
        List<String> list =
                new LinkedList<>(actionTimeMap.keySet());
        Collections.sort(list, (String s1, String s2) ->
        {
                return (actionTimeMap.get(s2)).compareTo(actionTimeMap.get(s1));
        });
        if(!appStarted) {
            frequentlyUsedBox.getChildren().removeAll(actionButtons);
        }
        actionButtons = new Button[list.size()];
        for(int i = 0; i < list.size(); i++){
            actionButtons[i] = new Button(list.get(i));
        }
//        List children = frequentlyUsedBox.getChildren();
        setButtonActions();
        frequentlyUsedBox.getChildren().addAll(actionButtons);
        appStarted = false;
    }

    public static void setButtonActions(){
        for(int i = 0; i < actionButtons.length; i++){
            Button button = actionButtons[i];
            if(button.getText().equals("Compile")) {
                button.setOnAction(event -> {
                    FrequentlyUsedWindow.setActionTiming(button.getText());
                    file = HomeScene.getFile();
                    ConsoleOutput.getOutputArea().setText("");
                    TermTester.ToolBarActions(1, file.getName());
                    //file.getName();
                    System.out.printf(""+file.getName());
                });
            }else if(button.getText().equals("Execute")){
                actionButtons[i].setOnAction(event -> {
                    FrequentlyUsedWindow.setActionTiming(button.getText());
                    file = HomeScene.getFile();
                    ConsoleOutput.getOutputArea().setText("");
                    TermTester.ToolBarActions(4, file.getName());
                });
            } else if(button.getText().equalsIgnoreCase("Generate Code")){
                button.setOnAction(event -> {
                    FrequentlyUsedWindow.setActionTiming(button.getText());
                    file = HomeScene.getFile();
                    ConsoleOutput.getOutputArea().setText("");
                    TermTester.ToolBarActions(8, file.getName());
                });
            }else if (button.getText().equalsIgnoreCase("Optimize Code")){
                button.setOnAction(event -> {
                    FrequentlyUsedWindow.setActionTiming(button.getText());
                    file = HomeScene.getFile();
                    ConsoleOutput.getOutputArea().setText("");
                    TermTester.ToolBarActions(5, file.getName());
                });
            } else if(button.getText().equalsIgnoreCase("profile report")){
                button.setOnAction(event -> {
                    FrequentlyUsedWindow.setActionTiming(button.getText());
                    file = HomeScene.getFile();
                    ConsoleOutput.getOutputArea().setText("");
                    TermTester.ToolBarActions(6, file.getName());
                });
            }else if(button.getText().equalsIgnoreCase("stack report")){
                button.setOnAction(event -> {
                    FrequentlyUsedWindow.setActionTiming(button.getText());
                    file = HomeScene.getFile();
                    ConsoleOutput.getOutputArea().setText("");
                    TermTester.ToolBarActions(7, file.getName());
                });
            }
        }
    }
}
