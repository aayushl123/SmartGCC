package View;

import Backend.TermTester;
import Backend.UserClass;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;

public class ToolBarClass {

    private static ToolBar toolBar;
    private static Button compileButton;
    private static Button linkButton;
    private static Button executeButton;
    private static Button debugButton;
    private static Button codeGenerationButton;
    private static Button codeOptimizationButton;
    private static Button developerOptionsButton;
    private static File file;

    public ToolBarClass(){
        toolBar = new ToolBar();
        initializeButtons();
        setToolBarNovice();
        if(UserClass.getUserType().equals("Typical"))
            setToolBarTypical();
        else if(UserClass.getUserType().equals("Expert")) {
            setToolBarTypical();
            setToolBarExpert();
        }
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
            input = new FileInputStream("src/Resources/CodeGenerationIcon.png");
            image = new Image(input);
            imageView = new ImageView(image);
            codeGenerationButton = new Button("Generate Code", imageView);
            input = new FileInputStream("src/Resources/CodeOptimizationIcon.jpg");
            image = new Image(input);
            imageView = new ImageView(image);
            codeOptimizationButton = new Button("Optimize Code", imageView);
            input = new FileInputStream("src/Resources/DeveloperOptionsIcon.jpg");
            image = new Image(input);
            imageView = new ImageView(image);
            developerOptionsButton = new Button("Developer Options", imageView);

            setToolBarButtonAction(compileButton);
            setToolBarButtonAction(executeButton);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setToolBarNovice(){
        toolBar.getItems().add(compileButton);
        toolBar.getItems().add(linkButton);
        toolBar.getItems().add(executeButton);
        toolBar.getItems().add(debugButton);
    }

    public static void setToolBarTypical(){
        toolBar.getItems().add(codeGenerationButton);
        toolBar.getItems().add(codeOptimizationButton);
    }

    public static void setToolBarExpert(){
        toolBar.getItems().add(developerOptionsButton);
    }

    public static ToolBar getToolBar() {
        return toolBar;
    }

    public static void setToolBarButtonAction(Button button){
        HomeScene homeScene = new HomeScene();
        file = homeScene.getFile();
        if(button.getText().equals("Compile")) {
            button.setOnAction(event -> {
                TermTester.ToolBarActions(1, file.getName());
                //file.getName();
                System.out.printf(""+file.getName());
            });
        }else if(button.getText().equals("Execute")){
            button.setOnAction(event -> {
                TermTester.ToolBarActions(4, file.getName());
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
