package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {

    StringBuilder output = new StringBuilder();                              // holds the generated output
    String command;                                                          // holds the firing command;
    int option;
    String fileName;

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

        if(option == 1){                                                     // Compile
            command = "cd src; cd Resources; rm tempOut";
            fireCommand();                                                   // Deletes previous compilations

            File compileFile = new File("src/Resources/ProgFile.cpp"); // Adjust the path to resources
            String absolutePath = compileFile.getAbsolutePath();
            command = null;
            command = "cd src; cd Resources; g++ " + absolutePath +" -Wall "+ " -o "+ "tempOut";
            output.setLength(0);
            fireCommand();
        }

        else if( option == 4){                                               // Execute
            String os = System.getProperty("os.name");
            if(os.startsWith("Win")) {
                command = null;
                command = "cd src; cd Resources;tempOut.exe";
            }
            else{
                command = null;
                command = "cd src; cd Resources;./tempOut";
            }
            output.setLength(0);
            fireCommand();
        }

    }

    //Firing the stored command on the system terminal
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

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
            } else {
                System.out.println(exitVal);
            }

        } catch (IOException e) {
            System.out.println("There was a problem with the I/O");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("There was a interruption with the execution");
            e.printStackTrace();
        }
    }
}
