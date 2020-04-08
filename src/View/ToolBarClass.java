package View;

import Backend.TermTester;
import Backend.UserClass;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ToolBarClass {

    private static ToolBar toolBar;
    private static Button compileButton;
    private static Button linkButton;
    private static Button executeButton;
    private static Button debugButton;
    private static MenuButton typicalOptions;
    private static MenuButton developerOptionsButton;

    private static MenuItem codeGenerationButton;
    private static MenuItem codeOptimizationButton;
    private static MenuItem profileReportItem;
    private static MenuItem stackReportItem;
    private static File file;
    private static HomeTextEditor homeTextEditor;

    public ToolBarClass(HomeTextEditor homeTextEditor){
        toolBar = new ToolBar();
        initializeButtons();
        setToolBarNovice();
        if(UserClass.getUserType().equals("Typical"))
            setToolBarTypical();
        else if(UserClass.getUserType().equals("Expert")) {
            setToolBarTypical();
            setToolBarExpert();
        }
        this.homeTextEditor = homeTextEditor;
    }

    public static void initializeButtons(){
        try {
            FileInputStream input = new FileInputStream("src/Resources/CompileButtonIcon.png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            compileButton = new Button("Compile", imageView);
            input = new FileInputStream("src/Resources/LinkIcon.png");
            image = new Image(input);
            imageView = new ImageView(image);
            linkButton = new Button("Linking", imageView);
            input = new FileInputStream("src/Resources/ExecuteIcon.png");
            image = new Image(input);
            imageView = new ImageView(image);
            executeButton = new Button("Execute", imageView);
            input = new FileInputStream("src/Resources/DebugIcon.png");
            image = new Image(input);
            imageView = new ImageView(image);
            debugButton = new Button("Debug", imageView);
            input = new FileInputStream("src/Resources/CodeOptimizationIcon.jpg");
            image = new Image(input);
            imageView = new ImageView(image);
            typicalOptions = new MenuButton("Typical Options", imageView);
            input = new FileInputStream("src/Resources/DeveloperOptionsIcon.jpg");
            image = new Image(input);
            imageView = new ImageView(image);
            developerOptionsButton = new MenuButton("Developer Options", imageView);

            codeGenerationButton = new MenuItem("Generate Code");
            codeOptimizationButton = new MenuItem("Optimize Code");
            profileReportItem = new MenuItem("Profile Report");
            stackReportItem = new MenuItem("Stack Report");

            typicalOptions.getItems().add(codeOptimizationButton);
            typicalOptions.getItems().add(codeGenerationButton);
            developerOptionsButton.getItems().add(profileReportItem);
            developerOptionsButton.getItems().add(stackReportItem);

            setToolBarButtonActionNovice(compileButton);
            setToolBarButtonActionNovice(executeButton);
            setToolBarButtonActionNovice(linkButton);
            setToolBarButtonActionTypical(codeGenerationButton);
            setToolBarButtonActionTypical(codeOptimizationButton);
            setToolBarActionsDeveloper(profileReportItem);
            setToolBarActionsDeveloper(stackReportItem);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setToolBarNovice(){
        toolBar.getItems().add(compileButton);
        toolBar.getItems().add(linkButton);
        toolBar.getItems().add(debugButton);
        toolBar.getItems().add(executeButton);
    }

    public static void setToolBarTypical(){
        toolBar.getItems().add(typicalOptions);
    }

    public static void setToolBarExpert(){
        toolBar.getItems().add(developerOptionsButton);
    }

    public static ToolBar getToolBar() {
        return toolBar;
    }

    public static void setToolBarButtonActionNovice(Button button){
        if(button.getText().equals("Compile")) {
            button.setOnAction(event -> {
                onOptionSelected(  1);

               /* FrequentlyUsedWindow.setActionTiming(button.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(1, file.getName());

*/
            });
        }else if(button.getText().equals("Execute")){
            button.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(button.getText());
                onOptionSelected(4);
                /*FrequentlyUsedWindow.setActionTiming(button.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(4, file.getName());*/
            });
        } else if(button.getText().equals("Linking")){
            button.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(button.getText());
                onOptionSelected(2);
               /* FrequentlyUsedWindow.setActionTiming(button.getText());
                System.out.println("here is click");
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                file = HomeScene.getFile();
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(2, file.getName());*/
            });
        } else if(button.getText().equals("Debug")){
            button.setOnAction(event -> {
               // FrequentlyUsedWindow.setActionTiming(button.getText());
                //onOptionSelected(button,3);

            });
        }
    }

    public static void checkIfFileExists(){
        file = HomeScene.getFile();
        if(file==null) {
            file=createNewFile();
        }
        MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
    }
    public static void onOptionSelected( int option){
        checkIfFileExists();
        ConsoleOutput.getOutputArea().setText("");
        TermTester.ToolBarActions(option, file.getName());
    }

    // creates a new file in resources if user writes in editor
    public static File createNewFile()  {

        File localFile = new File("src/Resources/temp"+System.currentTimeMillis()+".cpp" );
        try {
            if (localFile.createNewFile()) {
                System.out.println("File created: " + localFile.getName());
            } else {
                System.out.println("File already exists.");
            }

            System.out.println("new in resources  "+localFile.getAbsolutePath());
            PrintWriter writer = new PrintWriter(localFile);
            writer.println(homeTextEditor.getText());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Saved file in Resources");
        return localFile;
    }

    public static void setToolBarButtonActionTypical(MenuItem menuItem){
        if(menuItem.getText().equalsIgnoreCase("Generate Code")){
            menuItem.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(menuItem.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(8, file.getName());
            });
        }else if (menuItem.getText().equalsIgnoreCase("Optimize Code")){
            menuItem.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(menuItem.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(5, file.getName());
            });
        }
    }

    public static void setToolBarActionsDeveloper(MenuItem menuItem){
        if(menuItem.getText().equalsIgnoreCase("profile report")){
            menuItem.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(menuItem.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(6, file.getName());
            });
        }else if(menuItem.getText().equalsIgnoreCase("stack report")){
            menuItem.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(menuItem.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(7, file.getName());
            });
        }

    }

    public static void removeToolBarTypical(){
        toolBar.getItems().remove(codeGenerationButton);
        toolBar.getItems().remove(codeOptimizationButton);
    }

    public static void removeToolBarExpert(){
        toolBar.getItems().remove(developerOptionsButton);
    }

}
