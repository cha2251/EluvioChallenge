import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String [] args) throws IOException {
        ArrayList<Offset> offsets = new ArrayList<Offset>();

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

    private static Offset[] compareLists(ByteFile file1, ByteFile file2) {
        int offset1 = 0;
        int offset2 = 0;
        int length = 0;
        int currOffset1 = 0;
        int currOffset2 = 0;
        int currLength = 0;
        boolean inSame = false; //flag
        for (int i = 0; i < file1.bytes.length; i++){
            for (int j = 0; j < file2.bytes.length; j++){
                if (file1.bytes[i] == file1.bytes[j]){ //found two equeal bytes
                    if (inSame){//aready in a substring? add to length
                        currLength++;
                    }else { //otherwise, start one
                        currOffset1 = i;
                        currOffset2 = j;
                        currLength = 0;
                        inSame = true;
                    }
                }else if (inSame){ //unequal bytes only matter if in a sub, where we have to end it
                    inSame = false;
                    if(currLength > length){ //found a longer substring, update the stored vals
                        offset1 = currOffset1;
                        offset2 = currOffset2;
                        length = currLength;
                    }
                }
            }
        }
        return new Offset[]{new Offset(file1,offset1,length), new Offset(file2,offset2,length)};
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

    public static class Offset{
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
