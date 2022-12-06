/**
File Master (version 1.2)
Made and Developed by Noah Lake, 2022
 */

package FFM;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileMaster simplifies the creation of files and directories,
 * and includes other intuitive features such as directory searches and printing
 * files to the console/txt document. Also includes other tools that help converting
 * files easy.
 */
public class FileMaster extends Component {

    public static final String DOCUMENTS_FOLDER = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Documents/";
    public static final String DESKTOP_FOLDER = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Desktop/";
    public static final String APPLICATIONS_FOLDER = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Applications/";
    public static final String DOWNLOADS_FOLDER = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Downloads/";

    public static final int DIRECTORIES_ONLY = JFileChooser.DIRECTORIES_ONLY;
    public static final int FILES_ONLY = JFileChooser.FILES_ONLY;
    public static final int ALL_FILES = JFileChooser.FILES_AND_DIRECTORIES;

    public static File saveObject(String filename, Object obj) {
        File file = new File(filename);
        try {
            // create a new file with an ObjectOutputStream
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // write something in the file
            oos.writeObject(obj);

            // close the object stream
            oos.close();
            log("Saved Object data to " + file.getPath());

        } catch (Exception ex) {
            log("Couldn't save object to " + filename);
            ex.printStackTrace();
        }
        return file;
    }

    public static Object loadObject(String filename) throws IOException {
        File objFile = new File(filename);
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(filename);
        } catch (IOException e){
            log("Could not read file data.");
            e.printStackTrace();
        }

        // TODO - HOW TO RETRIEVE CLASS DATA?
        return null;
    }

    public static ArrayList<String> fileToList(String filename){
        File file = new File(filename);
        if (file.exists()){
            Scanner s;
            try {
                s = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            int count = 0;
            ArrayList<String> list = new ArrayList<String>();
            while (s.hasNextLine()){
                list.add(s.nextLine());
                count++;
            }
            s.close();
            log("Converted " + count + " lines of '" + file.getName() + "' to ArrayList<String>");
            return list;
        } else {
            log("Couldn't find file '" + file.getName() + "'");
            return null;
        }
    }

    public String[] fileToArray(String filename){
        File file = new File(filename);
        if (file.exists()){
            Scanner s;
            try {
                s = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            int count = 0;
            ArrayList<String> list = new ArrayList<String>();
            while (s.hasNextLine()){
                list.add(s.nextLine());
                count++;
            }
            s.close();
            log("Converted " + count + " lines of '" + file.getName() + "' to String[]");
            return list.toArray(new String[0]);
        } else {
            log("Couldn't find file '" + file.getName() + "'");
            return null;
        }
    }

    public static void listToFile(ArrayList<String> stringArrayList, String filename){
        File file = new File(filename);

            try {
                FileWriter writer = new FileWriter(filename);
                for (String str : stringArrayList) {
                    writer.write(str + System.lineSeparator());
                }
                writer.close();
                log("Successfully streamed ArrayList to '" + file.getName() + "'");
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static void arrayToFile(String[] arr, String filename){
        File file = new File(filename);

        try {
            FileWriter writer = new FileWriter(filename);
            for (String str : arr) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            log("Successfully streamed String[] to '" + file.getName() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printList(List<?> list){
        System.out.println("=========");
        for (int i = 0; i < list.size(); i++) {
            log("LISTPRINT: " + list.get(i));
        }
        System.out.println("=========");
        log("Successfully Printed List");
    }

    public static File createFile(String filename){
        File myObj = null;
        try {
            myObj = new File(filename);

            if (myObj.createNewFile()) {
                log("Created File: " + myObj.getName());
            } else {
                log("File '" + myObj.getName() + "' already exists.");
            }

        } catch (IOException e) {
            log("An error occurred when creating new File");
            e.printStackTrace();
        }
        return myObj;
    }

    public static File createDirectory(String pathname, String newDirectories){
        File newDir = new File("/" + pathname + "/" + newDirectories);

        if(newDir.exists()){
            log("Directory '" + newDir.getName() + "' already exists");
        } else {
            newDir.mkdirs();
            log("Created Directory: " + newDir.getName());
        }
        return newDir;
    }

    public static File createDirectory(String fullPath){
        File newDir = new File(fullPath);

        if(newDir.exists()){
            log("Directory '" + newDir.getName() + "' already exists");
        } else {
            newDir.mkdirs();
            log("Created Directory: " + newDir.getName());
        }
        return newDir;
    }

    public static void delete(String pathname) {
        File file = new File(pathname);
        File[] contents = file.listFiles();
        if(file.exists()) {
            if (contents != null) {
                for (File f : contents) {
                    delete(String.valueOf(f));
                    log("Deleted File: " + f);
                }
            }
            file.delete();
            log("Deleted File: " + file);
        } else {
            log("File couldn't be deleted: File not found");
        }
    }

    public static String getPathFor(String userDirectory){
        return FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + userDirectory;
    }

    public static boolean fileExists(String pathname){
        return new File(pathname).exists();
    }

    public static File chooseFile(){
        return new FileMaster().chooseFileFromNonStatic();
    }

    public static File chooseDir(){
        return new FileMaster().chooseDirFromNonStatic();
    }

    public static File chooseCustomFile(int fileType){
        return new FileMaster().chooseCustomFileFromNonStatic(fileType);
    }

    private File chooseFileFromNonStatic(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile;
        }else{
            return null;
        }
    }

    private File chooseDirFromNonStatic(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile;
        }else{
            return null;
        }
    }

    private File chooseCustomFileFromNonStatic(int selectionMode){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(selectionMode);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile;
        }else{
            return null;
        }
    }

    private static void log(String s){
        System.out.println("FFM.FileMaster:: " + s);
    }



}

