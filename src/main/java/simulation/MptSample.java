package simulation;

import java.util.*;

/**
 * Implements Modern Portfolio Theory.
 */
public class MptSample extends Sample {

    public static final double DEFAULT_INFLATION = 3.5 * 0.01;
    public static final double DEFAULT_INIT_CAPITAL = 100.0 * 1000;
    public static final int DEFAULT_PERIODS = 20;

    private double mu;
    private double sigma;
    private double inflation;
    private double initCapital;
    private int periods;
    private Long seed; // Can be set only during instantiation, else will use Java default seed generation
    private Random random;

    /**
     * Creates a sample using given mean and standard deviation. Other parameters use class default.
     * @param mu Mean of portfolio return
     * @param sigma Standard of portfolio return
     */
    public MptSample(double mu, double sigma) {
        this(mu, sigma, DEFAULT_INFLATION, DEFAULT_INIT_CAPITAL, DEFAULT_PERIODS, null);
    }

    /**
     * Creates a sample using given parameters.
     * @param inflation Inflation rate.
     * @param initCapital Money invested initially.
     * @param periods Number of investing periods.
     * @param seed Use this to reproduce sample result.
     */
    public MptSample(double mu, double sigma, double inflation, double initCapital, int periods, Long seed) {
        this.mu = mu;
        this.sigma = sigma;
        this.inflation = inflation;
        this.initCapital = initCapital;
        this.periods = periods;
        random = new Random();
        if (seed != null) {
            random.setSeed(seed);
        }
    }

    // Returns the yield at the end of investing period, assuming initial investment is 1.
    public double getFinalReturn() {
        double inflationFactor = StrictMath.pow(1 + inflation, periods);
        double result = 1 / inflationFactor;
        // double[] returnByPeriod = new double[years];
        for (int i = 0; i < periods; i++) {
            result *= 1 + getRandom();
            // returnByPeriod[i] = getRandom();
        }
        return result;
    }

    // Returns the random number with customized mean and standard deviation.
    private double getRandom() {
        return mu + sigma * random.nextGaussian();
    }

    /** {@inheritDoc} */
    @Override
    public double getResultScore() {
        return getFinalReturn() * initCapital;
    }

//    public static void main(String[] args) {
//        MptSample c = new MptSample(9.4324 * 0.01, 15.675 * 0.01);
//        System.out.println(c.getFinalReturn());
//    }
}
