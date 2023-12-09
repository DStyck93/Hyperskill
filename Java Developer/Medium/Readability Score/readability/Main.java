package readability;

import readability.tests.*;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // Input
        Scanner scanner = new Scanner(new File(args[0]));
        Text text = new Text(scanner.nextLine());
        scanner.close();

        // Output
        System.out.println("The text is:");
        System.out.println(text.getText() + "\n");

        System.out.println("Words: " + text.getNumWords());
        System.out.println("Sentences: " + text.getNumSentences());
        System.out.println("Characters: " + text.getNumChars());
        System.out.println("Syllables: " + text.getNumSyllables());
        System.out.println("Polysyllables: " + text.getNumPolysyllables());
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): all");

        // Output
        displayResults(text, "all");
    }

    private static void displayResults(Text text, String input) {
        System.out.println();
        Test test;
        switch(input) {
            case "ARI":
                test = new ARI(text);
                test.displayResults();
                break;

            case "FK":
                test = new FK(text);
                test.displayResults();
                break;

            case "SMOG":
                test = new SMOG(text);
                test.displayResults();
                break;

            case "CL":
                test = new CL(text);
                test.displayResults();
                break;

            case "all":
                float[] recommendedAges = new float[4];

                test = new ARI(text);
                recommendedAges[0] = test.getRecommendedAge();
                test.displayResults();

                test = new FK(text);
                recommendedAges[1] = test.getRecommendedAge();
                test.displayResults();

                test = new SMOG(text);
                recommendedAges[2] = test.getRecommendedAge();
                test.displayResults();

                test = new CL(text);
                recommendedAges[3] = test.getRecommendedAge();
                test.displayResults();

                float avgRecommendedAge = (recommendedAges[0] + recommendedAges[1] + recommendedAges[2] + recommendedAges[3]) / 4f;
                System.out.println("\nThis text should be understood in average by " + avgRecommendedAge + "-year-olds.");
                break;

            default:
                System.out.println("ERROR - unable to handle: " + input);
        }
        System.out.println();
    }
}
