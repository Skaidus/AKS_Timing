import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AKSTimingLog {
    FileWriter[] log;

    public AKSTimingLog() throws Exception{
        log = new FileWriter[6];
        log[0] = new FileWriter("paso1.csv", false); // measures[1]-measures[0]
        log[1] = new FileWriter("paso2.csv", false); // measures[2]-measures[1]
        log[2] = new FileWriter("paso3.csv", false); // measures[3]-measures[2]
        log[3] = new FileWriter("paso4.csv", false); // measures[4]-measures[3]
        log[4] = new FileWriter("paso5.csv", false); // end-measures[4]
        log[5] = new FileWriter("AKS.csv", false); // end - measures[0]


    }

    public void closeFiles() throws Exception
    {
        for(int i=0; i<6;i++) {
            log[i].close();
        }
    }

    public void toFiles(double[] measures, int[] iterations, int bits) throws Exception{
        for(int i=0; i<6; i++){
            if(iterations[i]>0){
                this.log[i].write(toLine(bits, (measures[i])/(1000*iterations[i])));
            }
        }
    }

    private String toLine(int bits, double measure ){
        String s1 = String.valueOf(bits);
        String s2 = String.valueOf(measure);
        return s1+","+s2+"\n";
    }
}
