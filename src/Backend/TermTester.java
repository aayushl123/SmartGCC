package Backend;

import java.util.HashMap;

public class TermTester {
    public static void main(String[] args) {
        HashMap<Integer, String> cmdInfo = new HashMap<Integer, String>();
        cmdInfo.put(1, "COMPILE");
        cmdInfo.put(2, "LINK");
        cmdInfo.put(3, "DEBUG");
        cmdInfo.put(4, "EXECUTE");
        Terminal t = new Terminal();
        t.setOption(2);
        t.setFileName("ProgFile.cpp");
        t.commandGen();
        Terminal t2 = new Terminal(4, "ProgFile.cpp");


    }
}
