public class BitTiming {

    public double[] getCumulativeSums() {
        return cumulativeSums;
    }

    public int[] getIterations() {
        return iterations;
    }

    public int getBits() {
        return bits;
    }


    double[] cumulativeSums;
    int[] iterations;
    int bits;

    public BitTiming(int bits){
        this.bits = bits;
        cumulativeSums = new double[6]; // [paso1, paso2, paso3, paso4, paso5, total]
        iterations = new int[6]; // [paso1, paso2, paso3, paso4, paso5, total]
    }

    public void addMeasures(double[] measures,  int final_measure){
        for(int i=1; i<=final_measure; i++){
            cumulativeSums[i-1]+=(measures[i]-measures[i-1]);
            iterations[i-1]++;
        }
        if(final_measure!=0){
            cumulativeSums[5] += (measures[final_measure] - measures[0]);
            iterations[5]++;
        }
    }

}
