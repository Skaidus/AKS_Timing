import java.math.BigInteger;


/**
 *
 */

/**
 * @author Vincent
 *
 */
public class AKSHeuristics
{

    public double[] getMeasures() {
        return measures;
    }

    public int getLast_measure() {
        return last_measure;
    }


    BigInteger n;
    boolean n_isprime;
    BigInteger factor;

    double[] measures; // [start, paso1Acaba, paso2Acaba, paso3Acaba, paso4Acaba, paso5Acaba]
    int last_measure;

    /***
     * Constructor--just save the number
     * @param n
     */
    public AKSHeuristics(BigInteger n)
    {
        this.n = n;
        this.measures = new double[6];
        this.last_measure = -1;
    }

    /***
     * Run AKS.isprime as a thread
     */
    public void run()
    {
        this.isPrime();
    }

    public void addTime(){
        double measure = System.currentTimeMillis();
        this.last_measure++;
        this.measures[this.last_measure] = measure;
    }


    /***
     * Run the AKS primality test
     *
     * @return true if n is prime
     */
    public boolean isPrime()
    {
        // PASO 1
        // TODO: Do this in linear time http://www.ams.org/journals/mcom/1998-67-223/S0025-5718-98-00952-1/S0025-5718-98-00952-1.pdf
        // If ( n = a^b for a in natural numbers and b > 1), output COMPOSITE
        BigInteger base = BigInteger.valueOf(2);
        BigInteger aSquared;

        do
        {
            BigInteger result;

            int power = Math.max((int) (log()/log(base) - 2),1);
            int comparison;

            do
            {
                power++;
                result = base.pow(power);
                comparison = n.compareTo(result);
            }
            while( comparison > 0 && power < Integer.MAX_VALUE );

            if( comparison == 0 )
            {
                //if (verbose) System.out.println(n + " is a perfect power of " + base);
                factor = base;
                n_isprime = false;
                return n_isprime;
            }

            //if (verbose) System.out.println(n + " is not a perfect power of " + base);

            base = base.add(BigInteger.ONE);
            aSquared = base.pow(2);
        }
        while (aSquared.compareTo(this.n) <= 0);
        //if (verbose) System.out.println(n + " is not a perfect power of any integer less than its square root");
        this.addTime();

        // PASO 2
        // Find the smallest r such that o_r(n) > log^2 n
        // o_r(n) is the multiplicative order of n modulo r
        // the multiplicative order of n modulo r is the
        // smallest positive integer k with	n^k = 1 (mod r).
        double log = this.log();
        double logSquared = log*log;
        BigInteger k = BigInteger.ONE;
        BigInteger r = BigInteger.ONE;
        do
        {
            r = r.add(BigInteger.ONE);
            //if (verbose) System.out.println("trying r = " + r);
            k = multiplicativeOrder(r);
        }
        while( k.doubleValue() < logSquared );
        //if (verbose) System.out.println("r is " + r);
        this.addTime();

        // PASO 3
        // If 1 < gcd(a,n) < n for some a <= r, output COMPOSITE
        for( BigInteger i = BigInteger.valueOf(2); i.compareTo(r) <= 0; i = i.add(BigInteger.ONE) )
        {
            BigInteger gcd = n.gcd(i);
            //if (verbose) System.out.println("gcd(" + n + "," + i + ") = " + gcd);
            if ( gcd.compareTo(BigInteger.ONE) > 0 && gcd.compareTo(n) < 0 )
            {
                factor = i;
                n_isprime = false;
                return false;
            }
        }

        n_isprime = true;
        return n_isprime;
    }


    /***
     * Calculate the multiplicative order of n modulo r
     * This is defined as the smallest positive integer k
     * for which n^k = 1 (mod r).
     *
     * @param r modulus for mutliplicative order
     * @return multiplicative order or -1 if none exists
     */
    BigInteger multiplicativeOrder(BigInteger r)
    {
        // TODO Consider implementing an alternative algorithm http://rosettacode.org/wiki/Multiplicative_order
        BigInteger k = BigInteger.ZERO;
        BigInteger result;

        do
        {
            k = k.add(BigInteger.ONE);
            result = this.n.modPow(k,r);
        }
        while( result.compareTo(BigInteger.ONE) != 0 && r.compareTo(k) > 0);

        if (r.compareTo(k) <= 0)
            return BigInteger.ONE.negate();
        else
        {
            //if (verbose) System.out.println(n + "^" + k + " mod " + r + " = " + result);
            return k;
        }
    }


    // Save log n here
    double logSave = -1;

    /***
     *
     * @return log base 2 of n
     */
    double log()
    {
        if ( logSave != -1 )
            return logSave;

        // from http://world.std.com/~reinhold/BigNumCalcSource/BigNumCalc.java
        BigInteger b;

        int temp = n.bitLength() - 1000;
        if (temp > 0)
        {
            b=n.shiftRight(temp);
            logSave = (Math.log(b.doubleValue()) + temp)*Math.log(2);
        }
        else
            logSave = (Math.log(n.doubleValue()))*Math.log(2);

        return logSave;
    }


    /**
     * log base 2 method that takes a parameter
     * @param x
     * @return
     */
    double log(BigInteger x)
    {
        // from http://world.std.com/~reinhold/BigNumCalcSource/BigNumCalc.java
        BigInteger b;

        int temp = x.bitLength() - 1000;
        if (temp > 0)
        {
            b=x.shiftRight(temp);
            return (Math.log(b.doubleValue()) + temp)*Math.log(2);
        }
        else
            return (Math.log(x.doubleValue())*Math.log(2));
    }

    public BigInteger getFactor()
    {
        return factor;
    }



}
