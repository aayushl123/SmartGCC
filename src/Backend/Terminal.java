package Backend;
import Controller.DebugController;
import View.ConsoleOutput;
import javafx.scene.control.TextInputDialog;

import javax.swing.*;
import java.io.*;
import java.util.Optional;

import javafx.scene.control.*;

/**
 * A Class to run the commands that would interact with the GCC Compiler.
 * @author Aayush, Rohit
 */
public class Terminal {

    StringBuilder output = new StringBuilder();                                  // holds the generated output
    StringBuilder outputErr = new StringBuilder();
    String command;                                                             // holds the firing command;
    int option;
    String fileName;
    Process process;

    public Terminal() {

    }

    Terminal(int option, String fileName){
        this.option = option;
        this.fileName = fileName;
        commandGen();
    }

    /**
     * A method to return the option chosen by the user.
     * @return option
     */
    public int getOption() {
        return option;
    }

    /**
     * A method to set the option chosen by the user.
     * @param option compile, execute, etc.
     */
    public void setOption(int option) {
        this.option = option;
    }

    /**
     * A method to get the name of the file on which
     * the operations will be performed.
     * @return fileName name of file being used
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * A method to set the name of the file at the backend.
     * This is the file chosen by the user.
     * @param fileName name of the file user is working upon
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * A method to get the output of the operation performed.
     * @return output output for the operation
     */
    public StringBuilder getOutput() {
        return output;
    }

    String libFileName="";
    /**
     * A method to generate the command options based on
     * the operation chosen chosen by the user.
     */
    public void commandGen(){
        String os = System.getProperty("os.name");

        if(option == 1){                                                         // Compile
            command = "cd src; cd Resources; rm -f tempOut";                        // Deletes previous compilations
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
                command = command.replace("rm -f tempOut"," del tempOut.exe 2>nul");
            }
            fireCommand();

            File compileFile = new File("src/Resources/"+fileName); // Adjust the path to resources
            String absolutePath = compileFile.getAbsolutePath();
            command = null;
            command = "cd src; cd Resources; g++ " + absolutePath +" -Wall "+ " -o "+ "tempOut";
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
        }
        else if(option == 2){                                                   //Link
            command = "cd src; cd Resources; rm -f tempOut";                        // Deletes previous compilations
            if(os.startsWith("Win")){
                command = command.replace(";","&");
                command = command.replace("rm -f tempOut","del tempOut.exe 2>nul");
            }
            fireCommand();
            String curPath = System.getProperty("user.dir");
            command = null;

          /*  int reply = JOptionPane.showConfirmDialog(null, "Have you used a personal " +
                    " library in this program?"
                    , "Add own library", JOptionPane.YES_NO_OPTION);*/

            ButtonType Yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType No = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Have you used a personal " +
                    " library in this program?"
                  , Yes,
                    No);



            alert.setTitle("Date format warning");
            Optional<ButtonType> result = alert.showAndWait();

              if (result.orElse(No) == Yes) {

                  TextInputDialog dialog = new TextInputDialog("");
                  // create a event handler
                  dialog.setTitle("Enter the name of your \"library.cpp\" file. \n" +
                          "Note: The library file should be in the same folder with the executing program File.");
                  dialog.setHeaderText("File Name:");
                  //dialog.setWidth(700);
                  dialog.getDialogPane().setMinWidth(800);
                  dialog.showAndWait();


                  // set the text of the label
                  libFileName=dialog.getEditor().getText();


                 // libFileName = JOptionPane.showInputDialog();
                  System.out.println("here is the name of the file "+libFileName);
                command = "cd src; cd Resources; rm -r LibDirectory; mkdir LibDirectory;" +
                        " g++ -c "+libFileName+";" + " mv "+libFileName.substring(0,libFileName.indexOf("."))+".o "
                        + curPath + "/src/Resources/LibDirectory;" +
                        " cd LibDirectory; ar rcs "+libFileName.substring(0,libFileName.indexOf("."))+".a " +
                        ""+libFileName.substring(0,libFileName.indexOf("."))+".o; cd ..;" +
                        " g++ -Wall " + fileName + " " + curPath + "/src/Resources/LibDirectory/" +
                        ""+libFileName.substring(0,libFileName.indexOf("."))+".a -o tempOut";
            }
            else{
                command = "cd src; cd Resources; g++ " + curPath + "/src/Resources/"+fileName+" -Wall "+ " -o "+ "tempOut";

            }
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
                command = command.replace("rm -r","rmdir /q /s");
            }

            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
        }
        else if(option ==3){
            command = "cd src; cd Resources; rm -f tempOut";                        // Deletes previous compilations
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
            }
            fireCommand();
            String curPath = System.getProperty("user.dir");
            command = null;
            String libFileName;
            int reply = JOptionPane.showConfirmDialog(null, "Have you used a personal " +
                            " library in this program?"
                    , "Add own library", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                libFileName = JOptionPane.showInputDialog("Enter the name of your \"library.cpp\" file. \n" +
                        "Note: The library file should be in the same folder with the executing program File.");

                command = "cd src; cd Resources; rm -r LibDirectory; mkdir LibDirectory;" +
                        " g++ -c "+libFileName+";" + " mv "+libFileName.substring(0,libFileName.indexOf("."))+".o "
                        + curPath + "/src/Resources/LibDirectory;" +
                        " cd LibDirectory; ar rcs "+libFileName.substring(0,libFileName.indexOf("."))+".a " +
                        ""+libFileName.substring(0,libFileName.indexOf("."))+".o; cd ..;" +
                        " g++ -Wall -g " + fileName + " " + curPath + "/src/Resources/LibDirectory/" +
                        ""+libFileName.substring(0,libFileName.indexOf("."))+".a -o tempOut";
            }
            else{
                command = "cd src; cd Resources; g++ " + curPath + "/src/Resources/"+fileName +" -Wall -g"+ " -o "+ "tempOut";

            }
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
                command = command.replace("rm -r","rmdir /q /s");
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
            debug();
        }

        else if( option == 4){                                                   // Execute

            if(os.startsWith("Win")) {
                File exeFile = new File("src/Resources/tempOut.exe");
                if(!exeFile.exists()) {                                             //Compile the file before executing
                    setOption(1);
                    commandGen();
                }
                command = null;
                command = "cd src; cd Resources; tempOut.exe";
                if(os.startsWith("Win")) {
                    command = command.replace(";", " &");
                }
            }
            else{
                command = null;
                command = "cd src; cd Resources; ./tempOut";
                if(os.startsWith("Win")){
                    command = command.replace(";"," &");
                }
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
        }

        else if(option == 5){                                                       //Optimize
            File compileFile = new File("src/Resources/"+fileName);
            String absolutePath = compileFile.getAbsolutePath();
            command = null;
            command = "cd src; cd Resources; rm -f tempOut; g++ -O2 " + absolutePath + " -o "+ "tempOut";
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
                command = command.replace("rm -f tempOut","del tempOut.exe 2>nul");
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
        }

        else if(option == 6){                                                       //Generate Profile Report
            File compileFile = new File("src/Resources/"+fileName);
            String absolutePath = compileFile.getAbsolutePath();
            command = null;
            command = "cd src; cd Resources; rm -f tempOut.exe; rm -f tempProfReport.txt; g++ -fprofile-report " + absolutePath + " -o "+
                    "tempOut" + " 2> tempProfReport.txt";
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
                command = command.replace("rm -f tempOut.exe","del tempOut.exe 2>nul");
                command = command.replace("rm -f tempProfReport.txt","del tempProfReport.txt 2>nul");
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
            readFile("tempProfReport.txt");
        }

        else if(option == 7){                                                       //Check stack usage
            File compileFile = new File("src/Resources/"+fileName);
            String suFileName = fileName.replace(".cpp", ".su");
            String absolutePath = compileFile.getAbsolutePath();
            command = null;
            command = "cd src; cd Resources; rm -f tempOut.exe; rm -f "+suFileName+"; g++ -fstack-usage " + absolutePath + " -o "+ "tempOut.exe";
            if(os.startsWith("Win")){
                command = "cd src & cd Resources & del tempOut.exe 2>nul & del " +suFileName+ " 2>nul & g++ -fstack-usage " + absolutePath + " -o "+ "tempOut.exe";
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
            readFile(suFileName);
        }
        else if(option == 8){                                                       //Enable exceptions
            File compileFile = new File("src/Resources/"+fileName);
            String absolutePath = compileFile.getAbsolutePath();
            command = null;
            command = "cd src; cd Resources; rm -f tempOut.exe; g++ -fexceptions " + absolutePath + " -o "+ "tempOut.exe";
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
                command = command.replace("rm -f tempOut.exe","del tempOut.exe 2>nul");
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
        }
    }

    /**
     * The command that is chosen by the user will fired from this method.
     */
    public void fireCommand() {
        String line;
        String os = System.getProperty("os.name");
        ProcessBuilder builder;
        if(os.startsWith("Win")) {
            builder = new ProcessBuilder("cmd.exe", "/c", command);
        }
        else{
            builder = new ProcessBuilder("sh", "-c", command);
        }
        try {

            process = builder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(process.getOutputStream()));

            while ((line = reader.readLine()) != null) {
                if(line.contains("Input the value")) {
                    //any other user input in non debug execution
                    TextInputDialog td = new TextInputDialog("enter input here");
                    td.showAndWait();
                    System.out.println(td.getDefaultValue());
                    //String value = JOptionPane.showInputDialog(line);
//                    writer.write(value, 0, value.length());
                    writer.write(td.getEditor().getText(), 0, td.getEditor().getText().length());
                    //writer.newLine();
                    writer.flush();
                    writer.close();
                }
                else if(output.toString().contains("Current executable set to")){
                    System.out.println(line);
                    System.out.print("(lldb) ");
                    lldbSession(reader);
                }
                else {
                    output.append(line).append("\n");
                }
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
//                display("Success!");
                if(option == 8){
                    output.append("Code generated with exceptions");
                }else if(option == 5){
                    output.append("Code optimised");
                }
                else if(option ==2){
                    output.append("The libraries have been linked");
                }
                display(output.toString());
            } else {
                String lineErr;
                BufferedReader readerErr = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                while ((lineErr = readerErr.readLine()) != null) {
                    outputErr.append(lineErr).append("\n");
                }
                //display(exitVal);
                display(outputErr.toString()); //Display the uncatched errors
            }

        } catch (IOException e) {
            display("There was a problem with the I/O");
            e.printStackTrace();
        } catch (InterruptedException e) {
            display("There was a interruption with the execution");
            e.printStackTrace();
        }
        if(!outputErr.toString().isEmpty())
        errorFormatDisplay();                                                   //display Error output function
    }

    /**
     * Provides formatted error output for the operations
     */
    public void errorFormatDisplay(){
        if(outputErr.toString().contains("file not found")){
            display("ERROR 1:The header Files are not in the appropriate directory");
        }
        if((outputErr.toString().contains("undeclared identifier"))||(outputErr.toString().contains("linker"))){
            display("WARNING 1: Compilation has been done, but, use the linker to add your libraries");
        }
        if((outputErr.toString().contains("No such file"))){
            display("Warning 2: The file on which the operation was performed does not exist");
        }
    }

    /**
     * Display any terminal responses to the console
     * @param str string to display in the output area
     */
    public void display(String str){
        ConsoleOutput.getOutputArea().appendText(str+"\n");
        System.out.println(str);
    }

    /**
     * Execute the activity and interaction in a debug session.
     */
    public void debug(){
        //GDB MI is a external interface for managing the interaction without inter process communication
        command = "cd src; cd Resources; set startup-with-shell disable; lldb --interpreter=mi tempOut";
        fireCommand();

    }

    /**
     * A method to start the lldb session
     * @param reader BufferedReader object
     * @throws IOException for files
     */
    public void lldbSession(BufferedReader reader) throws IOException {
        String path = System.getProperty("user.dir");
        path = path + "/src/Resources/"+fileName;

        display("You have initiated your program in a LLDB debug mode\n");
        DebugController debugControl = new DebugController(path, reader, process);


    }

    /**
     * This method will show the option pane to take input for GDB debug.
     * @return input Dialog box
     */
    public String takeInputGDB(){
        return JOptionPane.showInputDialog("Choose from the options provided in the console");
    }

    /**
     * This method wll read from the file to display the result.
     * It reads from the file generated by the option chosen by the user.
     * @param fileName name of the file which has optional results
     */
    public void readFile(String fileName){
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/Resources/" + fileName));
            String line;
            while((line = in.readLine()) != null)
            {
                System.out.println(line);
                ConsoleOutput.getOutputArea().appendText(line+"\n");
            }
            in.close();
            System.out.println("\n");
        } catch (FileNotFoundException e) {
            System.out.println("The mentioned file does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was a problem with the I/O");
            e.printStackTrace();
        }
    }
}