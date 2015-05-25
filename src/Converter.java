import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Class to convert a phone number to words according to a dictionnary
 */

public class Converter
{

    private static Converter _instance = null;
    private Dictionnary _dictionnary = null;
    
    /*
     * Constructor of the class
     */
    private Converter() {
        
    }
    
    /*
     * Singleton class
     */
    public static Converter getInstance() {
        if (_instance == null) {
            _instance = new Converter();
        }
        return _instance;
    }
    
    /*
     * Set the content from a dictionnary text file
     */
    public void setDictionnary(String path) {
        _dictionnary = new Dictionnary(path);
    }
    
    /*
     * Fetch the numbers from files given as arguments
     * and compute them
     */
    public void computeFiles(List<String> files) {
        Scanner scanner;
        for (String file : files) {
            try {
                scanner = new Scanner(new File(file));
                System.out.println("Computing words for file '" + file + "'");
                while (scanner.hasNextLine()) {
                    compute(scanner.nextLine());
                }
                System.out.println("End of file");
            } catch (FileNotFoundException e) {
                System.err.println("Error: File " + file + " has not been found. Proceding to next one.");
            }
        }

    }
    
    /*
     * Compute a number to words
     */
    public void compute(String number) {
        number = Util.escape(number);
        if (!Util.isNumber(number)) {
            System.err.println("Error: Input contains character(s) not allowed.");
        } else {
            //Compute here
            if (_dictionnary.getWords().size() == 0)
                System.out.println("--Warning-- No input in the dictionnary.");
            System.out.println("MATCHES FOR THE NUMBER '" + number + "' are:");
            List<String> converted = new ArrayList<String>();
            findWord(number, converted, "", 0);
            System.out.println("--END--\n");
        }        
    }
    
    /*
     * Display the number once it has been converted to words
     * If it is only one number jumped, displays nothing
     */
    public boolean displaySolution(List<String> converted) {
        if (converted.size() == 1 && Util.isNumber(converted.get(0))) {
            return true;
        }
        
        boolean first = true;
        for (String word : converted) {
            if (first) {
                first = false;
            } else {
                System.out.print("-");
            }
            System.out.print(word);
        }
        System.out.println();
        return true;
    }
    
    /*
     * Looking for all the matchs possible for a number in a recursive way
     */
    public boolean findWord(String number, List<String> converted, String toAdd, Integer left) {
        //Add a word that is a match with the number provided
        if (toAdd.length() > 0) {
            converted.add(toAdd);
        }
        
        //Display a solution that matches the number provided
        if (number.length() == 0 && converted.size() > 0) {
            return displaySolution(converted);
        }
        else {
            boolean found = false;
            boolean added;
            
            //Iterate over all the words available
            for (Map.Entry<String, String> entry : _dictionnary.getWords().entrySet()) {
                added = false;
                Pattern pattern = Pattern.compile("^" + entry.getValue());
                Matcher matcher = pattern.matcher(number);
                
                //If there is a match with a word, add it to the list and starts again
                //with the string left
                if (matcher.find()) {
                    added = true;
                    if (findWord(number.replaceFirst(entry.getValue(), ""), converted, entry.getKey(), 0)) {
                        found = true;
                    }
                }
                
                //Once all solutions with a word have been tested, remove it from the list
                if (added) {
                    converted.remove(converted.size() - 1);
                }
            }
            
            //If no match occured, jump one number, if it is the second number jumped
            //consecutively, stop the match for this word
            if (!found) {
                if (left == 0) {
                    findWord(number.substring(1), converted, number.substring(0, 1), 1);
                    converted.remove(converted.size() - 1);
                } 
            }
        }
        return false;
    }
}
