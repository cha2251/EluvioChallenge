import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String [] args) throws IOException {
        ArrayList<String> fileNames = new ArrayList<String>(); //inital values for longest strands
        int length = 0;
        int offset = 0;

        ArrayList<byte[]> byteLists = new ArrayList<byte[]>();
        for (int i = 0; i < args.length; i++){ //read in all lists of bytes. Make more sense to do comparisons as they are read in??
            byteLists.add(readFile(args[i]));
        }
    }

    private static byte[] readFile(String filename) throws IOException {
        FileInputStream in = new FileInputStream(filename);
        return in.readAllBytes();
    }

}
