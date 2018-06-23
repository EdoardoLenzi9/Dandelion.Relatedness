package eu.dandelion.Business.Services;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class OrderedDumpGenerator {

    public static void CreateDump(int maxId, int nodeSize, double nullPercentage, double avgRel) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("src/eu/dandelion/generatedDump.txt", "UTF-8");
        writer.println(maxId);
        writer.println(nodeSize);
        writer.println(0.01);
        writer.println(2);
        writer.println(1.0);

        int relForNode = (int) (avgRel * nodeSize);
        int nullNumber = (int) (nullPercentage * nodeSize);

        for (int i = 0; i < nullNumber; i++) {
            writer.println(maxId - i + " " + i + " null");
        }

        for (int i = nullNumber; i < nodeSize; i++) {
            writer.println(maxId - i + " " + i + " " + RandomPostingList(nodeSize, relForNode, i));
        }

        writer.close();
    }

    private static String RandomPostingList(int nodeSize, int relForNode, int currentIndex) {
        relForNode = relForNode > currentIndex ? (currentIndex - 1) : relForNode;

        byte[] tuple = new byte[relForNode * 4];
        
        for (int i = 0; i < relForNode; i++) {
            int index = currentIndex - (i + 1);
            
            tuple[(i * 4) + 0] = (byte) (index >> 16);
            tuple[(i * 4) +1] = (byte) (index >> 8);
            tuple[(i * 4) +2] = (byte) (index);
            tuple[(i * 4) +3] = (byte) ((int) (Math.random() * 255));
        }

        return new String(Base64.getEncoder().encode(tuple));
    }
}
