package Backend;
import View.ConsoleOutput;

import javax.swing.*;
import java.io.*;

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

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public StringBuilder getOutput() {
        return output;
    }

    /*
    Generates the command for the appropriate command option.
     */
    public void commandGen(){
        String os = System.getProperty("os.name");

        if(option == 1){                                                         // Compile
            command = "cd src; cd Resources; rm -f tempOut";                        // Deletes previous compilations
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
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
                        " g++ -Wall " + fileName + " " + curPath + "/src/Resources/LibDirectory/" +
                        ""+libFileName.substring(0,libFileName.indexOf("."))+".a -o tempOut";
            }
            else{
                command = "cd src; cd Resources; g++ " + curPath + "/src/Resources/"+fileName+";" +" -Wall "+ " -o "+ "tempOut";

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
                command = "cd src; cd Resources; g++ " + curPath + "/src/Resources/"+fileName+";" +" -Wall -g"+ " -o "+ "tempOut";

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
            File exeFile = new File("src/Resources/tempOut.exe");
            if(!exeFile.exists()) {                                             //Compile the file before executing
                setOption(1);
                commandGen();
            }

            if(os.startsWith("Win")) {
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
                command = command.replace(";"," &");
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
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
        }
    }

    /*
    Firing the stored command on the system terminal
     */
    public void fireCommand() {
        String line;
        String os = System.getProperty("os.name");
        ProcessBuilder builder;
        if(os.startsWith("Win")) {
            builder = new ProcessBuilder("cmd.exe", "/c", command);
        }
        else{
            builder = new ProcessBuilder("bash", "-c", command);
        }
        try {

            process = builder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                if(line.contains("Input the value")) {
                    //any other user input in non debug execution
                    String value = JOptionPane.showInputDialog(line);
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(process.getOutputStream()));
                    writer.write(value, 0, value.length());
                    writer.newLine();
                    writer.close();
                }
                else if(output.toString().contains("Copyright (C) 2020 Free Software Foundation, Inc.") ||
                        output.toString().contains("This GDB was configured")){
                    if(line.contains("Reading symbols from /")) {
                        System.out.println(line);
                        gdbSession(reader);
                    }
                    else{
                        System.out.println(line);
                        output.append(line).append("\n");
                    }
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

    /*
    Provides Error codes and shows Formatted Error Display
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
    /*
    Display any terminal responses to the console
     */
    public void display(String str){
        ConsoleOutput.getOutputArea().appendText(str+"\n");
        System.out.println(str);
    }

    /*
    Execute the activity and interaction in a debug session.
     */
    public void debug(){
        //GDB MI is a external interface for managing the interaction without inter process communication
        command = "cd src; cd Resources; set startup-with-shell off; gdb --interpreter=mi tempOut";
        fireCommand();

    }

    /*
    Create a session for the entire debug execution
     */
    public void gdbSession(BufferedReader reader) throws IOException {
        display("You have initiated your program in a GDB debug mode\n");
        String head = "Enter your choice in the popup window\n"+
                "1. Run the program in debug mode\n" +
                "2. Add break points\n" +
                "3. Get existing breakpoints\n" +
                "4. Delete existing breakpoints\n" +
                "5. Go to next breakpoint\n" +
                "6. Add breakpoint at the beginning of a function\n"+
                "7. Quit\n";
        display(head);
        String selOption = takeInputGDB();
        while(!selOption.equals("7")){
            if(selOption.equals("1")){
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(process.getOutputStream()));
                String value = "run\n";
                writer.write(value, 0, value.length());
                writer.newLine();
                writer.close();
                String line;
                while ((line = reader.readLine()) != null) {
                    display(line);
                }

            }
            display(head);
            selOption = takeInputGDB();
        }

    }

    public String takeInputGDB(){
        return JOptionPane.showInputDialog("Choose from the options provided in the console");
    }


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
