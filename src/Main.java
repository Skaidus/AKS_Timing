import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 */

/**
 * Time our AKS implementation
 *
 * @author Vincent
 *
 */



public class Main
{
    static int iterations = 60;
    /**
     * @param args
     *  arg[0]: minimum number of bits to run
     *  arg[1]: maximum number of bits to run
     */
    public static void main(String[] args) throws Exception
    {
        int minBits = Integer.parseInt(args[0]);
        int maxBits = Integer.parseInt(args[1]);

        SecureRandom r = new SecureRandom();
        AKSTiming myLog = new AKSTiming();

            for( int bits = minBits; bits <= maxBits; bits += 2 )
            {
                BitTiming bitLog = new BitTiming(bits);

                for( int i = 0; i < iterations; )
                {
                    BigInteger n = BigInteger.probablePrime(bits, r);
                    AKS a = new AKS(n);
                    a.addTime();

                    if(a.isPrime()){
                        a.addTime();
                        bitLog.addMeasures(a.getMeasures(), a.getLast_measure());
                        i++;
                    }
                }

                myLog.toFiles(bitLog.getCumulativeSums(), bitLog.getIterations(), bitLog.getBits());

            }


        myLog.closeFiles();

    }



}
