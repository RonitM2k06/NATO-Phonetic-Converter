import java.util.*;
import java.io.*;

// Custom Exception
class InvalidWordException extends Exception {
    public InvalidWordException(String message) {
        super(message);
    }
}

public class NATOFile {

    // NATO Maps
    static HashMap<String, String> map = new HashMap<>();
    static HashMap<String, String> reverse = new HashMap<>();

    // Initialize NATO Dictionary
    public static void initializeMap() {

        String[][] nato = {
                {"A","Alpha"}, {"B","Bravo"}, {"C","Charlie"},
                {"D","Delta"}, {"E","Echo"}, {"F","Foxtrot"},
                {"G","Golf"}, {"H","Hotel"}, {"I","India"},
                {"J","Juliett"}, {"K","Kilo"}, {"L","Lima"},
                {"M","Mike"}, {"N","November"}, {"O","Oscar"},
                {"P","Papa"}, {"Q","Quebec"}, {"R","Romeo"},
                {"S","Sierra"}, {"T","Tango"}, {"U","Uniform"},
                {"V","Victor"}, {"W","Whiskey"}, {"X","Xray"},
                {"Y","Yankee"}, {"Z","Zulu"},

                // Numbers
                {"0","Zero"}, {"1","One"}, {"2","Two"},
                {"3","Three"}, {"4","Four"}, {"5","Five"},
                {"6","Six"}, {"7","Seven"}, {"8","Eight"},
                {"9","Nine"}
        };

        for(String[] pair : nato) {
            map.put(pair[0], pair[1]);
            reverse.put(pair[1], pair[0]);
        }
    }

    // Encryption Method
    public static String encrypt(String text) throws InvalidWordException {

        StringBuilder result = new StringBuilder();

        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {

            String letter = "" + text.charAt(i);

            // Space handling
            if(letter.equals(" ")) {
                result.append("/ ");
                continue;
            }

            // Punctuation handling
            if(letter.equals(".")) {
                result.append("FullStop ");
                continue;
            }

            if(letter.equals(",")) {
                result.append("Comma ");
                continue;
            }

            if(letter.equals("!")) {
                result.append("Exclamation ");
                continue;
            }

            if(letter.equals("?")) {
                result.append("QuestionMark ");
                continue;
            }

            if(!map.containsKey(letter)) {
                throw new InvalidWordException(
                        "Invalid character found: " + letter
                );
            }

            result.append(map.get(letter)).append(" ");
        }

        return result.toString();
    }

    // Decryption Method
    public static String decrypt(String text) throws InvalidWordException {

        StringBuilder result = new StringBuilder();

        String[] words = text.split(" ");

        for(String w : words) {

            // Space handling
            if(w.equals("/")) {
                result.append(" ");
                continue;
            }

            // Punctuation handling
            if(w.equalsIgnoreCase("FullStop")) {
                result.append(".");
                continue;
            }

            if(w.equalsIgnoreCase("Comma")) {
                result.append(",");
                continue;
            }

            if(w.equalsIgnoreCase("Exclamation")) {
                result.append("!");
                continue;
            }

            if(w.equalsIgnoreCase("QuestionMark")) {
                result.append("?");
                continue;
            }

            // Case-insensitive matching
            w = w.substring(0,1).toUpperCase() +
                    w.substring(1).toLowerCase();

            if(!reverse.containsKey(w)) {
                throw new InvalidWordException(
                        "Invalid NATO word found: " + w
                );
            }

            result.append(reverse.get(w));
        }

        return result.toString();
    }

    public static void main(String[] args) {

        initializeMap();

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println(" NATO Phonetic File Converter");
        System.out.println("=================================");
        System.out.println("1. Encrypt File");
        System.out.println("2. Decrypt File");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        sc.nextLine();

        try {

            // Read complete file
            File input = new File("input.txt");
            Scanner fileReader = new Scanner(input);

            StringBuilder content = new StringBuilder();

            while(fileReader.hasNextLine()) {
                content.append(fileReader.nextLine()).append("\n");
            }

            fileReader.close();

            String result = "";

            // Encrypt
            if(choice == 1) {
                result = encrypt(content.toString());
            }

            // Decrypt
            else if(choice == 2) {
                result = decrypt(content.toString());
            }

            else {
                System.out.println("Invalid Choice!");
                return;
            }

            // Write to output file
            FileWriter writer = new FileWriter("output.txt");
            writer.write(result);
            writer.close();

            System.out.println("\nProcess Completed Successfully!");
            System.out.println("Check output.txt");

        }

        catch(FileNotFoundException e) {
            System.out.println("Error: input.txt not found.");
        }

        catch(InvalidWordException e) {
            System.out.println("Custom Error: " + e.getMessage());
        }

        catch(IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }

        finally {
            sc.close();
        }
    }
}