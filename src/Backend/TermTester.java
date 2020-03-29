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
        Terminal t = new Terminal(5, "ProgFileSimple.cpp");
        Terminal t2 = new Terminal(4, "ProgFileSimple.cpp");

    }
}
