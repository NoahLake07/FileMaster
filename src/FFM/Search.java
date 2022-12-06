package FFM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;

public class Search<T> {

    private static ArrayList<File> foundFiles = new ArrayList<>(1);
    static int currentLine = 0;
    static PrintWriter out;

    static void RecursivePrint(File[] arr, int level) {

        // for-each loop for main directory files
        for (File f : arr) {
            // tabs for internal levels
            for (int i = 0; i < level; i++)
                out.print("\t");
            System.out.print("\t");

            if (f.isFile()) {
                out.println(f.getName());
                System.out.println(f.getName());

            } else if (f.isDirectory()) {
                out.println("[" + f.getName() + "]");
                System.out.println("[" + f.getName() + "]");

                // recursion for sub-directories
                RecursivePrint(f.listFiles(), level + 1);
            }
        }
    }

    public static void searchDirectory(String directoryPath, String outputLoc) {
        File y = new File(outputLoc + search(directoryPath));
    }

    public static File search(String directoryPath){
        File output = null;

        try {
            out = new PrintWriter(output);

            out.println(("\t DIRECTORY SEARCH : " + directoryPath));
            System.out.println(("\t DIRECTORY SEARCH : " + directoryPath));

            out.println("\t Performed on: " + LocalTime.now());
            System.out.println("\t Performed on: " + LocalTime.now());
            out.println("============================");
            System.out.println("============================");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String directoryToSearch = directoryPath;

        // File object
        File mainDir = new File(directoryToSearch);

        if (mainDir.exists() && mainDir.isDirectory()) {
            // array for files and subdirectories
            // of directory pointed by mainDir
            File arr[] = mainDir.listFiles();

            // Calling recursive method
            RecursivePrint(arr, 0);
            return output;
        } else {
            System.out.println("FileMaster:: SEARCH FAILED - SPECIFIED LOCATION INVALID");
            return null;
        }
    }

}
