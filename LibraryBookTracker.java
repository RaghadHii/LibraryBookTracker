import java.io.*;
import java.util.*;

class BookCatalogException extends Exception { public BookCatalogException(String m) { super(m); } }
class InsufficientArgumentsException extends Exception { public InsufficientArgumentsException(String m) { super(m); } }
class InvalidFileNameException extends Exception { public InvalidFileNameException(String m) { super(m); } }

public class LibraryBookTracker {
    private static List<String> catalogLines = new ArrayList<>();
    private static String fileName;

    public static void main(String[] args) {
        try {

            if (args.length < 2) throw new InsufficientArgumentsException("Fewer than 2 arguments.");
            fileName = args[0];
            if (!fileName.endsWith(".txt")) throw new InvalidFileNameException("File must end with .txt");

            File file = new File(fileName);
            if (!file.exists()) file.createNewFile();
            
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                catalogLines.add(reader.nextLine());
            }
            reader.close();

            String operation = args[1];
            if (operation.contains(":")) {
                catalogLines.add(operation);
                Collections.sort(catalogLines); // ترتيب أبجدي
                saveToFile();
                System.out.println("Book added and catalog sorted.");
            } else {
                System.out.printf("%-30s %-20s %-15s %5s\n", "Title", "Author", "ISBN", "Copies");
                for (String line : catalogLines) {
                    if (line.toLowerCase().contains(operation.toLowerCase())) {
                        String[] p = line.split(":");
                        System.out.printf("%-30s %-20s %-15s %5s\n", p[0], p[1], p[2], p[3]);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // الجملة التي ستنطبع في كل الأحوال
            System.out.println("Thank you for using the Library Book Tracker.");
        }
    }

    private static void saveToFile() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));
        for (String line : catalogLines) {
            writer.println(line);
        }
        writer.close();
    }
}