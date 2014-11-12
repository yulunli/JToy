package simulation;

import java.util.*;

// Run Monte Carlo Simulation given sample type and related parameters.
public class MonteCarlo {

    public static final int DEFAULT_ITERATIONS = 10 * 1000;

    public int getIterations() {
        return iterations;
    }

    private int iterations;
    private SampleType sampleType;
    private String[] sampleParameters; // Helper parameters to generate the model.

    private void checkParams() {
        Sample testSample = SampleFactory.getSample(sampleType, sampleParameters);
        if (testSample == null) {
            throw new IllegalArgumentException("Bad arguments. Can't run simulation.");
        }
    }

    public MonteCarlo(SampleType sampleType, String[] sampleParameters) {
        this(DEFAULT_ITERATIONS, sampleType, sampleParameters);
    }

    /**
     * Creates Monte Carlo simulation using sample type and parameters.
     * @param iterations the number of simulation iterations
     * @param sampleType the type of the simulation sample
     * @param sampleParameters the parameters to help generate the sample
     */
    public MonteCarlo(int iterations, SampleType sampleType, String[] sampleParameters) {
        this.iterations = iterations;
        this.sampleType = sampleType;
        this.sampleParameters = sampleParameters;
        checkParams();
    }

    // Returns the simulation results as an array.
    public double[] runSimulation() {
        double[] results = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            Sample s = SampleFactory.getSample(sampleType, sampleParameters);
            results[i] = s.getResultScore();
        }
        Arrays.sort(results);
        return results;
    }

    // Returns the index for a particular percentile assuming the result array is sorted.
    public int getPercentileIndex(int percentile) {
        if (percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("Invalid percentile.");
        } else {
            return ((int)(percentile * 0.01 * iterations)) - 1;
        }
    }

    // Run this method to answer prompt question.
    public static void main(String[] args) {
        SampleType sampleType = SampleType.MPT;
        String resultFormat = "Median: %,.2f, 10 Percentile: %,.2f, 90 Percentile:  %,.2f\n";

        String[] aggParameters = new String[]{String.valueOf(9.4324 * 0.01), String.valueOf(15.675 * 0.01)};
        MonteCarlo aggMonteCarlo = new MonteCarlo(sampleType, aggParameters);
        double[] r = aggMonteCarlo.runSimulation();
        System.out.println("Aggressive");
        System.out.printf(resultFormat, r[aggMonteCarlo.getPercentileIndex(50)],
                r[aggMonteCarlo.getPercentileIndex(10)],
                r[aggMonteCarlo.getPercentileIndex(90)]);

        String[] conParameters = new String[]{String.valueOf(6.189 * 0.01), String.valueOf(6.3438 * 0.01)};
        MonteCarlo conMonteCarlo = new MonteCarlo(sampleType, conParameters);
        double[] conResult = conMonteCarlo.runSimulation();
        System.out.println("Conservative");
        System.out.printf(resultFormat, conResult[conMonteCarlo.getPercentileIndex(50)],
                conResult[conMonteCarlo.getPercentileIndex(10)],
                conResult[conMonteCarlo.getPercentileIndex(90)]);
    }
}
