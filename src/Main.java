import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String [] args) throws IOException {
        ArrayList<ByteStrand> strands = new ArrayList<ByteStrand>();

        ArrayList<ByteFile> byteLists = new ArrayList<ByteFile>();
        for (int i = 0; i < args.length; i++){ //read in all lists of bytes. Make more sense to do comparisons as they are read in??
            byteLists.add(new ByteFile(readFile(args[i]),args[i]));
        }

        for (int i = 0; i < byteLists.size()-1; i++){ //compare each file to each other file
            for (int j = i+1; j < byteLists.size(); j++){
                ByteStrand[] newStrands = compareLists(byteLists.get(i), byteLists.get(j));
                if(strands.size() == 0 || strands.get(0).length < newStrands[0].length){//Found new longest strand
                    strands.clear();
                    strands.add(newStrands[0]);
                    strands.add(newStrands[1]);
                }
                if(strands.get(0).getBytes() == newStrands[0].getBytes()){ //Found another file that matches our current longest string
                    strands.add(newStrands[0]);
                    strands.add(newStrands[1]);
                }
            }
        }

        System.out.println("Length: "+strands.get(0).length);
        for (ByteStrand strand: strands) { //print output
            System.out.println(strand);
        }
    }

    private static byte[] readFile(String filename) throws IOException {
        FileInputStream in = new FileInputStream(filename);
        return in.readAllBytes();
    }

    private static ByteStrand[] compareLists(ByteFile file1, ByteFile file2) {
        int offset1 = 0;
        int offset2 = 0;
        int length = 0;
        int currOffset1 = 0;
        int currOffset2 = 0;
        int currLength = 0;
        boolean inSame = false; //flag
        for (int i = 0; i < file1.bytes.length-1; i++){
            for (int j = 0; j < file2.bytes.length-1; j++){
                if (file1.bytes[i] == file2.bytes[j]){ //found two equeal bytes
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
        System.out.println(offset1);
        System.out.println(offset2);
        System.out.println(length);
        return new ByteStrand[]{new ByteStrand(file1,offset1,length), new ByteStrand(file2,offset2,length)};
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

    public static class ByteStrand{
        public int offset = 0;
        public int length = 0;
        public ByteFile file;
        public ByteStrand(ByteFile f, int o, int l){
            file = f;
            offset = o;
            length = l;
        }
        public byte[] getBytes(){
            return substring(file.bytes, offset, offset+length);
        }

        @Override
        public String toString() {
            return "File: "+file.fileName+" Offset: "+offset;
        }
    }

}
