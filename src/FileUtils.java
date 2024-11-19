import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class FileUtils {

    public static ArrayList<String> readFile(String filename) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Path path = Paths.get(filename + ".txt");
        list.addAll(Files.readAllLines(path));
        return list;
    }

    public static void writeFile(String filename, ArrayList<String> list) throws IOException {
        Path path = Paths.get(filename + ".txt");
        Files.write(path, list);
    }
}
