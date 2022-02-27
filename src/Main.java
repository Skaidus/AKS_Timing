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
    static int minBits = 8;
    static int maxBits = 64;
    static int iterations = 60;
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {

        SecureRandom r = new SecureRandom();
        AKSTiming myLog = new AKSTiming();


        for( int bits = minBits; bits <= maxBits; bits += 2 )
        {
            BitTiming bitLog = new BitTiming(bits);

            for( int i = 0; i < iterations; i++ )
            {
                BigInteger n = BigInteger.probablePrime(bits, r);
                AKS a = new AKS(n);
                a.addTime();

                a.isPrime();

                a.addTime();

                bitLog.addMeasures(a.getMeasures(), a.getLast_measure());
            }

            for( int i = 0; i < iterations; i++ )
            {
                BigInteger n;
                do
                {
                    n = new BigInteger(bits/2, r).multiply(new BigInteger(bits/2,r));
                } while( n.compareTo(BigInteger.ZERO) <= 0 );

                AKS a = new AKS(n);

                a.addTime();
                a.isPrime();
                a.addTime();

                bitLog.addMeasures(a.getMeasures(), a.getLast_measure());

            }

            myLog.toFiles(bitLog.getCumulativeSums(), bitLog.getIterations(), bitLog.getBits());

        }

        myLog.closeFiles();

    }



}
