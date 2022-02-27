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



public class AKSTiming
{
    static int minBits = 8;
    static int maxBits = 16;
    static int iterations = 32;
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {

        SecureRandom r = new SecureRandom();
        AKSTimingLog myLog = new AKSTimingLog();


        for( int bits = minBits; bits <= maxBits; bits += 2 )
        {
            AKSTimingBit bitLog = new AKSTimingBit(bits);

            System.out.print("Detecting primes....");
            for( int i = 0; i < iterations; i++ )
            {
                BigInteger n = BigInteger.probablePrime(bits, r);
                AKS a = new AKS(n);
                System.out.print(".");
                a.addTime();
                if( !a.isPrime() ){
                    System.out.print("Wrong answer! " + n);
                }
                a.addTime();

                bitLog.addMeasures(a.getMeasures(), a.getLast_measure());
            }


            System.out.print("Detecting composites");
            for( int i = 0; i < iterations; i++ )
            {
                BigInteger n;
                do
                {
                    n = new BigInteger(bits/2, r).multiply(new BigInteger(bits/2,r));
                } while( n.compareTo(BigInteger.ZERO) <= 0 );

                AKS a = new AKS(n);
                System.out.print(".");
                if( a.isPrime() ){
                    System.out.print("Wrong answer! " + n);
                }
                a.addTime();

                bitLog.addMeasures(a.getMeasures(), a.getLast_measure());

            }

            myLog.toFiles(bitLog.getCumulativeSums(), bitLog.getIterations(), bitLog.getBits());

        }

        myLog.closeFiles();

    }



}
