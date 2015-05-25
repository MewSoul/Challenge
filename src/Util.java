/*
 * Utility class
 */
public class Util
{
    //Array to store the mapping of the letters
    static private final String[] _mapping = new String[]{
                                                    "2", "2", "2",
                                                    "3", "3", "3",
                                                    "4", "4", "4",
                                                    "5", "5", "5",
                                                    "6", "6", "6",
                                                    "7", "7", "7", "7",
                                                    "8", "8", "8",
                                                    "9", "9", "9", "9"};
    
    /*
     * Escape the punctuation and the spaces from a string
     */
    static public String escape(String word) {
        word = word.replaceAll("[\\p{Punct}\\p{Space}]", "");
        return word;
    }
    
    /*
     * Return true if the input is a number, otherwise false
     */
    static public boolean isNumber(String word) {
        return word.matches("^[0-9]+$");
    }
    
    /*
     * Return true if the input is a word, otherwise false
     */
    static public boolean isWord(String word) {
        return word.matches("^[a-zA-Z]+$");
    }
    
    /*
     * Convert a word to a number according to the mapping defined
     */
    static public String convertStringToNumber(String word) {
        String toNumber = "";
        for (char c : word.toCharArray()) {
            toNumber = toNumber + _mapping[(int)c - 'A'];
        }
        return toNumber;
    }
}
