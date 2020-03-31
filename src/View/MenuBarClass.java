package View;

import Driver.Main;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MenuBarClass {

    private Menu fileMenu;
    private Menu editMenu;
    private Menu helpMenu;
    private MenuBar menuBar;
    private HomeTextEditor homeTextEditor;

    MenuBarClass(HomeTextEditor homeTextEditor){
        this.menuBar = new MenuBar();
        this.homeTextEditor = homeTextEditor;
        setMenus();
        addMenus();
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void addMenus(){
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(editMenu);
        menuBar.getMenus().add(helpMenu);
    }

    public void setMenus(){
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");
        helpMenu = new Menu("Help");

        MenuItem fileMenuItem1 = new MenuItem("New");
        MenuItem fileMenuItem2 = new MenuItem("Open");
        MenuItem fileMenuItem3 = new MenuItem("Save");
        MenuItem fileMenuItem4 = new MenuItem("Save as");

        fileMenu.getItems().add(fileMenuItem1);
        fileMenu.getItems().add(fileMenuItem2);
        fileMenu.getItems().add(fileMenuItem3);
        fileMenu.getItems().add(fileMenuItem4);

        MenuItem editMenuItem1 = new MenuItem("Cut");
        MenuItem editMenuItem2 = new MenuItem("Copy");
        MenuItem editMenuItem3 = new MenuItem("Undo");

        editMenu.getItems().add(editMenuItem1);
        editMenu.getItems().add(editMenuItem2);
        editMenu.getItems().add(editMenuItem3);

        MenuItem helpMenuItem1 = new MenuItem("View Documentation");

        setFileMenuActions(fileMenuItem2);
        setFileMenuActions(fileMenuItem3);
        setFileMenuActions(fileMenuItem4);

        helpMenu.getItems().add(helpMenuItem1);
    }

    public void setFileMenuActions(MenuItem fileMenuItem){
        String selectedOption = fileMenuItem.getText();
        if(selectedOption.equals("Open") || selectedOption.equals("Save") ||selectedOption.equals("Save as")) {
            fileMenuItem.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CPP files(.cpp)", "*.cpp");
                fileChooser.getExtensionFilters().add(extFilter);

                File file = null;
                //Show save file dialog
                if (selectedOption.equals("Save as") || selectedOption.equals("Save")) {
                    file = fileChooser.showSaveDialog(Main.getStage());
                } else if (selectedOption.equals("Open")) {
                    file = fileChooser.showOpenDialog(Main.getStage());
                }
                if (file != null) {
                    if (selectedOption.equals("Save as") || selectedOption.equals("Save")) {
                        saveTextToFile(homeTextEditor.getText(), file);
                    } else if (selectedOption.equals("Open")) {
                        readTextFromFile(file);
                    }
                }
            });
        }
    }

    public void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;

            //code for saving the file in user's location
            System.out.println(file.getAbsolutePath());
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();

            //for saving the file in resources
            if(!file.getAbsolutePath().contains("/src/Resources")) {
                System.out.println(file.getAbsolutePath());
                String pathname = "/Users/sagarbhatia/Desktop/Masters_in_Applied_Computer_Science/Winter-2020/SOEN6751-HCI/Project/src/Resources/";
                file = new File(pathname + file.getName());
                System.out.println("new File "+file.getAbsolutePath());
                writer = new PrintWriter(file);
                writer.println(content);
                writer.close();
                System.out.println("Saved file in Resources");
            }

            HomeScene homeScene = new HomeScene();
            homeScene.setFile(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void readTextFromFile(File file) {
        try{
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                homeTextEditor.getTextArea().appendText(s.nextLine()+"\n");
            }
            if(!file.getName().contains("/src/Resources")) {
                System.out.println(file.getAbsolutePath());
                saveTextToFile(homeTextEditor.getTextArea().getText(), file);
            }
            else {
                HomeScene homeScene = new HomeScene();
                homeScene.setFile(file);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
