package readability.tests;

import readability.Text;

public abstract class Test {
    float score;
    int recommendedAge;

    public Test(Text text) {
        calculateScore(text);
        calculateRecommendedAge();
    }

    public abstract void calculateScore(Text text);
    public abstract void calculateRecommendedAge();

    public abstract void displayResults();

    //region Getters
    public float getScore() {
        return score;
    }

    public int getRecommendedAge() {
        return recommendedAge;
    }
    //endregion
}
