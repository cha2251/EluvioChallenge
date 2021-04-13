import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String [] args) throws IOException {


        ArrayList<ByteFile> byteLists = new ArrayList<ByteFile>();
        for (int i = 0; i < args.length; i++){ //read in all lists of bytes. Make more sense to do comparisons as they are read in??
            byteLists.add(new ByteFile(readFile(args[i]),args[i]));
        }

        for (int i = 0; i < byteLists.size()-1; i++){ //compare each file to each other file
            for (int j = i+1; j < byteLists.size(); j++){
                compareLists(byteLists.get(i), byteLists.get(j));
            }
        }
    }

    private static byte[] readFile(String filename) throws IOException {
        FileInputStream in = new FileInputStream(filename);
        return in.readAllBytes();
    }

    private static void compareLists(ByteFile file1, ByteFile file2) {

    }

    private static byte[] substring(byte[] bytes, int start, int end){
        return Arrays.copyOfRange(bytes,start,end);
    }

    static class ByteFile{
        public byte[] bytes;
        public String fileName;
        public ByteFile(byte[] b, String s){
            bytes = b;
            fileName = s;
        }
    }

    class Offset{
        public int offset = 0;
        public int length = 0;
        public ByteFile file;
        public Offset(ByteFile f, int o, int l){
            file = f;
            offset = o;
            length = l;
        }
        public byte[] getBytes(){
            return substring(file.bytes, offset, offset+length);
        }
    }

}
