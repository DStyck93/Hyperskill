package readability.tests;

import readability.Text;

public class SMOG extends Test {

    public SMOG(Text text) {
        super(text);
    }

    @Override
    public void calculateScore(Text text) {
        int numPolysyllables = text.getNumPolysyllables(), numSentences = text.getNumSentences();
        score = 1.043f * (float)Math.sqrt(numPolysyllables * (30f / (float)numSentences)) + 3.1291f;
    }

    @Override
    public void calculateRecommendedAge() {
        int rounded = (int)Math.ceil(score);
        if (rounded <= 12) {
            recommendedAge = rounded + 6;
        } else recommendedAge = 22;
    }

    @Override
    public void displayResults() {
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about " + recommendedAge + "-year-olds).\n", score);
    }
}
