package readability.tests;

import readability.Text;

public class ARI extends Test {

    public ARI(Text text) {
        super(text);
    }

    @Override
    public void calculateScore(Text text) {
        int numChars = text.getNumChars(), numWords = text.getNumWords(), numSentences = text.getNumSentences();
        score = 4.71f * (numChars / (float)numWords) + 0.5f * (numWords / (float) numSentences) - 21.43f;
    }

    @Override
    public void calculateRecommendedAge() {
        int s = (int)Math.ceil(score); // Round score up

        if (s < 14) {
            recommendedAge = s + 5;
        } else {
            recommendedAge = 22;
        }
    }

    @Override
    public void displayResults() {
        System.out.printf("Automated Readability Index: %.2f (about " + recommendedAge + "-year-olds).\n", score);
    }
}
