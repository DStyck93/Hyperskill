package readability.tests;

import readability.Text;

public class CL extends Test {

    public CL(Text text) {
        super(text);
    }

    @Override
    public void calculateScore(Text text) {
        int numLetters = text.getNumLetters(), numWords = text.getNumWords(), numSentences = text.getNumSentences();
        float L = (numLetters / (float)numWords) * 100f;
        float S = (numSentences / (float)numWords) * 100f;
        score = 0.0588f * L - 0.296f * S - 15.8f;
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
        System.out.printf("Coleman-Liau index: %.2f (about " + recommendedAge + "-year-olds).\n", score);
    }
}
