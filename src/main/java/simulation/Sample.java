package simulation;

/**
 * Can be extended to implement MPT or other models.
 * This may be changed to an interface if required by possible future models.
 */
public abstract class Sample {
    /**
     * Return a score to be used by a simulation suite.
     * @return Result score in double
     */
    public abstract double getResultScore();
}
