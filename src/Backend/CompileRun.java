package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileRun {

    public static void main(String[] args) {

        File exeFile = new File("test.exe");
        if(exeFile.exists()) {
            exeFile.delete();
        }

        File compileFile = new File("src/Resources/test.cpp");
        String absolutePath = compileFile.getAbsolutePath();
        String compile = "g++ " + absolutePath + " -o " + "test";
        String execute = "test.exe";

        executeCommand(compile);
        executeCommand(execute);
    }

    public static void executeCommand(String command) {
        String line;
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Exception = " + e.getMessage());
        }
    }
}
