package Backend;


import javax.swing.*;
import java.io.File;
import java.util.HashMap;

public class TermTester {

    public static void main(String[] args) throws InterruptedException {
        HashMap<Integer, String> cmdInfo = new HashMap<Integer, String>();
        cmdInfo.put(1, "COMPILE");
        cmdInfo.put(2, "LINK");
        cmdInfo.put(3, "DEBUG");
        cmdInfo.put(4, "EXECUTE");
        cmdInfo.put(5, "OPTIMIZE");
        cmdInfo.put(6, "PROFILE_REPORT");
        cmdInfo.put(7, "STACK_USAGE");
        cmdInfo.put(8, "ENABLE_EXCEPTIONS");

        JFileChooser fileChooser = new JFileChooser();
        String curPath = System.getProperty("user.dir");
        System.out.println(curPath);
        curPath = curPath+"/src/Resources";
        fileChooser.setCurrentDirectory(new File(curPath));
        JFrame holder = new JFrame();
        int result = fileChooser.showOpenDialog(holder);
        String fileName = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            fileName = selectedFile.getName();
        }
        Terminal t = new Terminal();
        t.setOption(2);
        t.setFileName(fileName);
        t.commandGen();
        t.setOption(4);
        t.setFileName(fileName);
        t.commandGen();
    }

    /**
     * method created to handle the call from front end
     * @param option
     * @param fileName
     * @author sagar
     */
    public static void ToolBarActions(int option, String fileName) {
        HashMap<Integer, String> cmdInfo = new HashMap<Integer, String>();
        cmdInfo.put(1, "COMPILE");
        cmdInfo.put(2, "LINK");
        cmdInfo.put(3, "DEBUG");
        cmdInfo.put(4, "EXECUTE");
        cmdInfo.put(5, "OPTIMIZE");
        cmdInfo.put(6, "PROFILE_REPORT");
        cmdInfo.put(7, "STACK_USAGE");
        //Terminal t2 = new Terminal(4, "ProgFileSimple.cpp");
        /*Terminal t = new Terminal();
        t.setOption(3);
        t.setFileName("ProgFile.cpp");
        t.commandGen();*/
        Terminal t2 = new Terminal(option, fileName);
        //Terminal t3 = new Terminal(7, "ProgFileSimple.cpp");


    }
}
