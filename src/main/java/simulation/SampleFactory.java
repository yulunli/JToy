package simulation;

import java.nio.channels.IllegalChannelGroupException;

/**
 * Factory class to generate samples.
 */
public class SampleFactory {
    public static Sample getSample(SampleType sampleType, String[] parameters) {
        Sample sample = null;
        switch (sampleType) {
            case MPT:
                try {
                    sample = new MptSample(Double.parseDouble(parameters[0]), Double.parseDouble(parameters[1]));
                } catch (NumberFormatException e) {
                    // System.out.println("Ill-formatted parameters.");
                }
                break;

            case MPT_FULL:
                try {
                    sample = new MptSample(Double.parseDouble(parameters[0]),
                            Double.parseDouble(parameters[1]),
                            Double.parseDouble(parameters[2]),
                            Double.parseDouble(parameters[3]),
                            Integer.parseInt(parameters[4]),
                            Long.getLong(parameters[5]));
                } catch (NumberFormatException e) {
                    // System.out.println("Ill-formatted parameters.");
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid sample type.");
        }
        return sample;
    }
}
