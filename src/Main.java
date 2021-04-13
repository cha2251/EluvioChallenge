import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) throws IOException {
        byte[] list1 = readFile(args[0]);
    }

    private static byte[] readFile(String filename) throws IOException {
        FileInputStream in = new FileInputStream(filename);
        return in.readAllBytes();
    }

}
