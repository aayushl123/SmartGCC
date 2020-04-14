package Backend;

import java.util.ArrayList;
import java.util.Collections;

public class Debugger {

    ArrayList<Integer> breakPoints;
    StringBuilder consoleContent;
    StringBuilder outputErr;
    int currentCheckpoint=0;

    public int getCurrentCheckpoint() {
        return currentCheckpoint;
    }

    public void setCurrentCheckpoint(int currentCheckpoint) {
        this.currentCheckpoint = currentCheckpoint;
    }

    public StringBuilder getOutputError() {
        return outputErr;
    }

    public void setOutputError(StringBuilder outputError) {
        this.outputErr = outputError;
    }

    public ArrayList<Integer> getBreakPoints() {
        return breakPoints;
    }

    public void setBreakPoints(ArrayList<Integer> breakPoints) {
        this.breakPoints = breakPoints;
    }

    public StringBuilder getConsoleContent() {
        return consoleContent;
    }

    public void setConsoleContent(StringBuilder consoleContent) {
        this.consoleContent = consoleContent;
    }

    //add line no
    //delete line no
    //redundancy check of line number
    //non existance delete
    //clear console

    public Debugger(){
        breakPoints = new ArrayList<>();

        consoleContent = new StringBuilder();
    }

    public String addCheckpoint(int lineNo){
        if(!breakPoints.contains(lineNo)){
            breakPoints.add(lineNo);
            Collections.sort(breakPoints);
            return " The checkpoint "+ lineNo +" has been marked ";
        }
        else{
            return " The checkpoint "+ lineNo + " is already marked ";
        }
    }
    public int delCheckpoint(int lineNo){
        if(breakPoints.contains(lineNo)){
            return breakPoints.indexOf(lineNo)+1;
        }
        else{
            return 0;
        }
    }
}
