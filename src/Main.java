import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Main class for command-line program which reads
 * phone numbers, and translates them to words according
 * to a dictionnaty
 */

public class Main
{    
    /*
     * Starter method
     */
    public static void main(String[] args)
    {     
        if (args.length == 0) {
            launchInputMode();
        }
        else {
            readFilesMode(args);
        }
    }
    
    /*
     * Method to read from input if no arg when program executed
     */
    private static void launchInputMode() {
        Converter.getInstance().setDictionnary("");
        Scanner input = new Scanner(System.in);
        String arg = "";
        System.out.println("Ready to read from input. Type 'exit' to end the program");
        while (true) {
            arg = input.nextLine();
            if (arg.equals("exit")) {
                break;
            } else {
                Converter.getInstance().compute(arg);
            }
        }
        input.close();
    }
    
    /*
     * Method to read from files if arg when program executed
     * If specified dictionnary not found, the default one will be loaded
     */
    private static void readFilesMode(String[] args) {
        boolean dictionnarySpecified = false;
        List<String> files = new ArrayList<String>();
        
        for (String arg : args) {
            if (dictionnarySpecified) {
                Converter.getInstance().setDictionnary(arg);
                dictionnarySpecified = false;
            }
            else if (arg.equals("-d")) {
                dictionnarySpecified = true;
            } else {
                files.add(arg);
            }
        }
        Converter.getInstance().computeFiles(files);
    }
}
