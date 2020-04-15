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

/**
 * Class for the toolbar of SmartGCC.
 * @author sagarbhatia, karansharma
 */
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

    /**
     * Tool bar class constructor.
     * @param homeTextEditor object of text editor
     */
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

    /**
     * Method to intialize the buttons on the toolbar.
     */
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

    /**
     * Method to add the buttons of novice users.
     */
    public static void setToolBarNovice(){
        toolBar.getItems().add(compileButton);
        toolBar.getItems().add(linkButton);
        toolBar.getItems().add(debugButton);
        toolBar.getItems().add(executeButton);
    }
    /**
     * Method to add the buttons of typical users.
     */
    public static void setToolBarTypical(){
        toolBar.getItems().add(typicalOptions);
    }
    /**
     * Method to add the buttons of expert users.
     */
    public static void setToolBarExpert(){
        toolBar.getItems().add(developerOptionsButton);
    }
    /**
     * Method to get the toolbar.
     * @return toolBar object of Toolbar
     */
    public static ToolBar getToolBar() {
        return toolBar;
    }

    /**
     *Method to set the actions for buttons of novice users.
     * @param button novice buttons
     */
    public static void setToolBarButtonActionNovice(Button button){
        if(button.getText().equals("Compile")) {
            button.setOnAction(event -> {
                onOptionSelected(  1);

                FrequentlyUsedWindow.setActionTiming(button.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(1, file.getName());


            });
        }else if(button.getText().equals("Execute")){
            button.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(button.getText());
                onOptionSelected(4);
                FrequentlyUsedWindow.setActionTiming(button.getText());
                file = HomeScene.getFile();
                if(file==null) {
                    file=createNewFile();
                }
                MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
                ConsoleOutput.getOutputArea().setText("");
                TermTester.ToolBarActions(4, file.getName());
            });
        } else if(button.getText().equals("Linking")){
            button.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(button.getText());
                onOptionSelected(2);
                FrequentlyUsedWindow.setActionTiming(button.getText());
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
                TermTester.ToolBarActions(2, file.getName());
            });
        } else if(button.getText().equals("Debug")){
            button.setOnAction(event -> {
                FrequentlyUsedWindow.setActionTiming(button.getText());
                onOptionSelected(3);
               // FrequentlyUsedWindow.setActionTiming(button.getText());
                //onOptionSelected(button,3);

            });
        }
    }

    /**
     *Method to check if the file exists
     */
    public static void checkIfFileExists(){
        file = HomeScene.getFile();
        if(file==null) {
            file=createNewFile();
        }
        MenuBarClass.saveAsTextToFile(homeTextEditor.getText(),file);
    }

    /**
     * Method to set the option selected for backend.
     * @param option option selected
     */
    public static void onOptionSelected( int option){
        checkIfFileExists();
        ConsoleOutput.getOutputArea().setText("");
        TermTester.ToolBarActions(option, file.getName());
    }

    /**
     * creates a new file in resources if user writes in editor
     * @return localFile object of local file
     */
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

    /**
     * Method to set the actions for typical user's buttons.
     * @param menuItem menu item in typical user options
     */
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

    /**
     * Method to set the actions for expert user's buttons.
     * @param menuItem menu item in expert user options
     */
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

    /**
     * Method to remove the typical user's buttons.
     */
    public static void removeToolBarTypical(){
        toolBar.getItems().remove(typicalOptions);
    }

    /**
     * Method to remove the expert user's buttons.
     */
    public static void removeToolBarExpert(){
        toolBar.getItems().remove(developerOptionsButton);
    }

}
