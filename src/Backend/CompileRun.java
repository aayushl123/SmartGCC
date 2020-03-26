package Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileRun {

    public static void main(String[] args) {
        String command1 = "g++ " + "..\\Backend\\Resources\\test.cpp " + "-o " + "test";
        String command2 = "..\\Backend\\Resources\\test.exe";
        System.out.println(command1);
        executeCommand(command1);
        executeCommand(command2);
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
