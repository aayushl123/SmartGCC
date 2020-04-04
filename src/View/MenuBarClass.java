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

                File originalFile = null;
                //Show save file dialog
                if (selectedOption.equals("Save as") ) {
                    originalFile = fileChooser.showSaveDialog(Main.getStage());
                } else if (selectedOption.equals("Open")) {
                    originalFile = fileChooser.showOpenDialog(Main.getStage());
                } else if(selectedOption.equals("Save")) {
                    saveAsTextToFile(homeTextEditor.getText(),HomeScene.getFile());
                }
                if (originalFile != null) {
                    if (selectedOption.equals("Save as") ) {
                        saveAsTextToFile(homeTextEditor.getText(), originalFile);
                    } else if (selectedOption.equals("Open")) {
                        readTextFromFile(originalFile);
                    }
                }
            });
        }
    }

    public void saveAsTextToFile(String content, File originalFile) {
        try {
            PrintWriter writer;

            //code for saving the file in user's location
            System.out.println(originalFile.getAbsolutePath());
            writer = new PrintWriter(originalFile);
            writer.println(content);
            writer.close();

            File localFile=null;
            //for saving the file in resources
            if(!originalFile.getAbsolutePath().contains("/src/Resources")) {
                System.out.println("Location to the original file :"+originalFile.getAbsolutePath());
                String pathname = "/";
                localFile = new File("src/Resources/"+originalFile.getName() );
                if (localFile.createNewFile()) {
                    System.out.println("File created: " + localFile.getName());
                } else {
                    System.out.println("File already exists.");
                }
                System.out.println("new File "+localFile.getAbsolutePath());
                writer = new PrintWriter(localFile);
                writer.println(content);
                writer.close();
                System.out.println("Saved file in Resources");
            }

            HomeScene homeScene = new HomeScene();
            homeScene.setFile(localFile,originalFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public void readTextFromFile(File originalFile) {
        try{
            Scanner s = new Scanner(originalFile);
            homeTextEditor.getTextArea().setText("");
            while (s.hasNext()) {

                homeTextEditor.getTextArea().appendText(s.nextLine()+"\n");
            }
            if(!originalFile.getName().contains("/src/Resources")) {
                System.out.println(originalFile.getAbsolutePath());
                saveAsTextToFile(homeTextEditor.getTextArea().getText(), originalFile);
            }
            else {
                HomeScene homeScene = new HomeScene();
                homeScene.setFile(originalFile,originalFile);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
