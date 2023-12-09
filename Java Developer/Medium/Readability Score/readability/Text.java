package readability;

public class Text {
    private String text;
    private int numWords, numSentences, numChars, numSyllables, numPolysyllables, numLetters;

    public Text(String text) {
        this.text = text;

        countWords();
        countSentences();
        countChars();
        countSyllables();
        countLetters();
    }

    //region Getters
    public String getText() {
        return text;
    }

    public int getNumWords() {
        return numWords;
    }

    public int getNumSentences() {
        return numSentences;
    }

    public int getNumChars() {
        return numChars;
    }

    public int getNumSyllables() {
        return numSyllables;
    }

    public int getNumPolysyllables() {
        return numPolysyllables;
    }

    public int getNumLetters() {return numLetters;}
    //endregion

    private void countWords() {
        numWords = text.split(" ").length;
    }

    private void countSentences() {
        numSentences = text.split("[.!?]").length;
    }

    private void countChars() {
        char[] chars = text.toCharArray();
        int count = 0;
        for (char c : chars) {
            if (c != ' ' && c != '\n' && c != '\t') count++;
        }
        numChars = count;
    }

    private void countLetters() {
        char[] chars = text.toCharArray();
        int count = 0;
        for (char c : chars) {
            if (Character.isAlphabetic(c)) {
                count++;
            }
        }
        numLetters = count;
    }

    public void countSyllables() {
        numSyllables = 0; numPolysyllables = 0;
        String[] words = text.split(" ");

        for (String word : words) {
            // Count the number of vowels in the word;
            int numVowels = 0;
            for (int i = 0; i < word.length(); i++) {
                if (isVowel(word.charAt(i))) {
                    numVowels++;

                    // Do not count double vowels
                    while (true) {
                        if (i < word.length() - 1 && isVowel(word.charAt(i + 1))) {
                            i++;
                        } else break;
                    }
                }
            }

            // Don't count 'e' if its the last char
            if (word.charAt(word.length() - 1) == 'e') numVowels--;

            if (numVowels <= 1) {
                numSyllables++;
            } else if (numVowels > 2) {
                numPolysyllables++;
                numSyllables += numVowels;
            } else {
                numSyllables += 2;
            }
        }
    }

    private boolean isVowel(char c) {
        char[] vowels = "aeiouyAEIOUY".toCharArray();
        for (char vowel : vowels) {
            if (c == vowel) return true;
        }
        return false;
    }
}
