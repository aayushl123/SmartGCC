package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {

    StringBuilder output = new StringBuilder(); // holds the generated output
    String command; // holds the firing command;

    public StringBuilder getOutput() {
        return output;
    }

    Terminal(int option, String fileName){
        commandGen(option, fileName);
        //File exeFile = new File("test.exe");
//        if(exeFile.exists()) {
//            exeFile.delete();
//        }

    }


    /*
    Generates the command for the appropriate command option.
     */
    public void commandGen(int option, String fileName){

        if(option == 1){ //compile
            File compileFile = new File("src/Resources/test.cpp"); // adjust the path to resources
            String absolutePath = compileFile.getAbsolutePath();
            command = null;
            command = "cd Resources; g++ " + absolutePath + " -o " + "test";
            executeCommand();
        }

        else if( option == 4){ //execute
            String os = System.getProperty("os.name");
            String execute;
            if(os.startsWith("Win")) {
                command = null;
                command = "test.exe";
            }
            else{
                command = null;
                command = "./test.out";
            }
            executeCommand();
        }

    }
    public void executeCommand() {
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

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
