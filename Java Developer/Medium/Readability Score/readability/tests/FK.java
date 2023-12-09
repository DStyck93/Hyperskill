package readability.tests;

import readability.Text;

public class FK extends Test {

    public FK(Text text) {
        super(text);
    }

    @Override
    public void calculateScore(Text text) {
        int numWords = text.getNumWords(), numSentences = text.getNumSentences(), numSyllables = text.getNumSyllables();
        score = 0.39f * (numWords / (float)numSentences) + 11.8f * (numSyllables / (float)numWords) - 15.59f;
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
        System.out.printf("Flesch-Kincaid readability tests: %.2f (about " + recommendedAge + "-year-olds).\n", score);
    }
}
