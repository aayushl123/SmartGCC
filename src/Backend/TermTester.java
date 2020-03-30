package Backend;


import java.util.HashMap;

public class TermTester {
    public static void main(String[] args) {
        HashMap<Integer, String> cmdInfo = new HashMap<Integer, String>();
        cmdInfo.put(1, "COMPILE");
        cmdInfo.put(2, "LINK");
        cmdInfo.put(3, "DEBUG");
        cmdInfo.put(4, "EXECUTE");
        cmdInfo.put(5, "OPTIMIZE");
        cmdInfo.put(6, "PROFILE_REPORT");
        cmdInfo.put(7, "STACK_USAGE");
        //Terminal t2 = new Terminal(4, "ProgFileSimple.cpp");
        Terminal t = new Terminal();
        t.setOption(2);
        t.setFileName("ProgFile.cpp");
        t.commandGen();
        Terminal t2 = new Terminal(4, "ProgFile.cpp");
        //Terminal t3 = new Terminal(7, "ProgFileSimple.cpp");


    }
}
