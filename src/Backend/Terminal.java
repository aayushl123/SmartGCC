package Backend;

import java.io.*;

public class Terminal {

    StringBuilder output = new StringBuilder();                              // holds the generated output
    StringBuilder outputErr = new StringBuilder();
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
        String os = System.getProperty("os.name");
        if(option == 1){                                                     // Compile
            command = "cd src; cd Resources; rm tempOut";
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
            }
            fireCommand();                                                   // Deletes previous compilations

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
        else if(option == 2){                                                //Link

            String curPath = System.getProperty("user.dir");
            command = null;
            command = "cd src; cd Resources; rm LibDirectory; mkdir LibDirectory;" +
                    " g++ -c LibFile.cpp;" + " mv LibFile.o " + curPath +"/src/Resources/LibDirectory;"+
                    " cd LibDirectory; ar rcs LibFile.a LibFile.o; cd ..;"+
                    " g++ -Wall ProgFile.cpp " + curPath + "/src/Resources/LibDirectory/" +
                    "LibFile.a -o tempOut";
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
                command = command.replace("rm","rmdir /q /s");
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
        }

        else if( option == 4){                                               // Execute
            if(os.startsWith("Win")) {
                command = null;
                command = "cd src; cd Resources; tempOut.exe";
                if(os.startsWith("Win")){
                    command = command.replace(";"," &");
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
            command = "cd src; cd Resources; rm tempOut; g++ -O2 " + absolutePath + " -o "+ "tempOut";
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
            command = "cd src; cd Resources; rm tempOut.exe; rm tempProfReport.txt; g++ -fprofile-report " + absolutePath + " -o "+
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
            command = "cd src; cd Resources; rm tempOut.exe; rm "+suFileName+"; g++ -fstack-usage " + absolutePath + " -o "+ "tempOut.exe";
            if(os.startsWith("Win")){
                command = command.replace(";"," &");
            }
            output.setLength(0);
            outputErr.setLength(0);
            fireCommand();
            readFile(suFileName);
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
                //System.out.println(line);
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                String lineErr;
                BufferedReader readerErr = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                while ((lineErr = readerErr.readLine()) != null) {
                    outputErr.append(lineErr + "\n");
                }
                //System.out.println(exitVal);
                //System.out.println(outputErr); //Display the uncatched errors
            }

        } catch (IOException e) {
            System.out.println("There was a problem with the I/O");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("There was a interruption with the execution");
            e.printStackTrace();
        }
        if(!outputErr.toString().isEmpty())
        errorFormatDisplay(); //display Error output function
    }

    /*
    Provides Error codes and shows Formatted Error Display
     */
    public void errorFormatDisplay(){
        if(outputErr.toString().contains("file not found")){
            System.out.println("ERROR 1:The header Files are not in the appropriate directory");
        }
        if((outputErr.toString().contains("undeclared identifier"))||(outputErr.toString().contains("linker"))){
            System.out.println("WARNING 1: Compilation has been done but, use the linker to add your libraries");
        }
        if((outputErr.toString().contains("No such file"))){
            System.out.println("Warning 2: The file on which the operation was performed does not exist");
        }
    }

    public void readFile(String fileName){
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/Resources/" + fileName));
            String line;
            while((line = in.readLine()) != null)
            {
                System.out.println(line);
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
