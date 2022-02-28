import java.io.FileWriter;

public class AKSTiming {
    FileWriter[] log;

    public AKSTiming() throws Exception{
        log = new FileWriter[6];
        for(int i = 0; i<5; i++){
            log[i] = new FileWriter("paso"+(i+1)+".csv", false);
        }
        log[5] = new FileWriter("AKS.csv", false); // end - measures[0]
        for(int i = 0; i<6; i++) {
            log[i].write("n,seconds\n");
        }
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
