import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Class to store the words of a dictionnary
 */

public class Dictionnary
{
    
    private Map<String, String> _words = new HashMap<String, String>();
    final private String _defaultDictionnary = "src/dict/default.txt";
    
    /*
     * Constructor of the class
     * Try to fetch the content of the dictionnary if specified
     * Otherwise fetch the content from the default dictionnary
     */
    public Dictionnary(String path) {
        Scanner scanner;
        
        if (path.length() > 0) {
            try {
                scanner = new Scanner(new File(path));
            } catch (FileNotFoundException e) {
                loadDefault();
                return;
            }
            fetchContent(scanner);            
        } else {
            loadDefault();
        }
    }
    
    /*
     * Fetch the content from the default dictionnary
     */
    private void loadDefault() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(_defaultDictionnary));
        } catch (FileNotFoundException e1) {
            System.err.println("Error: Neither the specified dictionnary nor the default one have been found. Program stopped");
            System.exit(1);
        }
        fetchContent(scanner);
    }
    
    /*
     * Fetch the content form a file
     */
    private void fetchContent(Scanner scanner) {
        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String word = Util.escape(scanner.nextLine()).toUpperCase();
                if (Util.isWord(word)) {
                    _words.put(word, Util.convertStringToNumber(word));
                } else {
                    System.out.println("Current input in dictionnary '" + word + "' is not valid");
                }
            }
            scanner.close();
        }
    }
    
    /*
     * Return the list of the words from the dictionnary loaded
     */
    public Map<String, String> getWords() {
        return _words;
    }
}
