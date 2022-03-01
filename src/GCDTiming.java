
// Java Program to find n'th fibonacci Number
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;

class GCDTiming {

    static int fib(int n) {
        double phi = (1 + Math.sqrt(5)) / 2;
        return (int) Math.round(Math.pow(phi, n)
                / Math.sqrt(5));
    }

    static int gcd(int p, int q) {
        if (q == 0) return p;
        else return gcd(q, p % q);
    }

    // Driver Code
    public static void main(String[] args) throws IOException {
        FileWriter file = new FileWriter("gcd_worst.csv", false );
        file.write("n,seconds\n");
        int n = 1;
        int minBits = Integer.parseInt(args[0]);
        int maxBits = Integer.parseInt(args[1]);

        for(int i = minBits; i <maxBits; i+=2){
            while(Integer.bitCount(fib(n+1))<i){
                n++;
            }
            int cumsum = 0;
            double before, after;
            for(int j=0; j<60; j++){
                before = System.currentTimeMillis();
                gcd(fib(n), fib(n-1));
                after = System.currentTimeMillis();
                cumsum += (after-before);
            }
            file.write(i+","+(cumsum/(60*1000))+"\n");
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
