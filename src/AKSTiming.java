import java.io.FileWriter;

public class AKSTiming {
    int initbits;

    public AKSTiming(int initbits) throws Exception{
        this.initbits = initbits;
        for(int i = 0; i<5; i++){
            FileWriter log = new FileWriter("paso"+(i+1)+(initbits)+".csv", false);
            log.write("n,seconds\n");
            log.close();
        }
        FileWriter log = new FileWriter("AKS"+(initbits)+".csv", false); // end - measures[0]
        log.write("n,seconds\n");
        log.close();
    }


    public void toFiles(double[] measures, int[] iterations, int bits) throws Exception{
        for(int i = 0; i<5; i++){
            FileWriter log = new FileWriter("paso"+(i+1)+(initbits)+".csv", true);
            log.write(toLine(bits, (measures[i])/(1000*iterations[i])));
            log.close();
        }
        FileWriter log = new FileWriter("AKS"+(initbits)+".csv", true); // end - measures[0]
        log.write(toLine(bits, (measures[5])/(1000*iterations[5])));
        log.close();
    }

    private String toLine(int bits, double measure ){
        String s1 = String.valueOf(bits);
        String s2 = String.valueOf(measure);
        return s1+","+s2+"\n";
    }
}
