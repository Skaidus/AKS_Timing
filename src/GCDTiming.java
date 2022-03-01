
// Java Program to find n'th fibonacci Number
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.io.FileWriter;

class GCDTiming {


    static int fib(int n) {
        double phi = (1 + Math.sqrt(5)) / 2;
        return (int) Math.round(Math.pow(phi, n) / Math.sqrt(5));}

    // Driver Code
    public static void main(String[] args) throws IOException {
        FileWriter file = new FileWriter("gcd_worst.csv", false );
        file.write("n,seconds\n");

        int minBits = Integer.parseInt(args[0]);
        int maxBits = Integer.parseInt(args[1]);

        BigInteger fib_n = BigInteger.ONE;
        BigInteger fib_n_1 = BigInteger.ZERO;
        BigInteger temp;

        for(int i = minBits; i <maxBits; i+=2){

            temp = fib_n.add(fib_n_1);

            while(temp.bitCount()<i){
                fib_n_1 = fib_n;
                fib_n = temp;
                temp = fib_n.add(fib_n_1);
            }
            double cumsum = 0;
            double before, after;
            for(int j=0; j<60; j++){
                before = System.currentTimeMillis();
                fib_n.gcd(fib_n_1);
                after = System.currentTimeMillis();
                cumsum += (after-before);
            }
            file.write(i+","+(cumsum/(60000.0))+"\n");
        }

        file.close();
        /*
        open file
        file.write(n,seconds)
        n = 0
        For bit = inicial
            while f(n+1).bits < bit
                n++
            cumsum = 0
            for i in range(60)
                now1
                gcd(f(n), f(n-1))
                now2
                cumsum += now2-now1
            file.write(bits, cumsum/(60*1000))
         file.close
         */

    }
}
