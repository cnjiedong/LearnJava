import java.io.FileWriter;

public class TestStream {

    public static void main(String[] argv){

        String s = "贵港1";

        int len = s.length();
        for(int i=0; i<len; i++){
         char c = s.charAt(i);
            System.out.println(c);
        }

        byte[] array = s.getBytes();
        System.out.println(array);
        }

        FileWriter fw = new FileWriter()

}

