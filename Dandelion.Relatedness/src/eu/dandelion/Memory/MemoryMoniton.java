package eu.dandelion.Memory;

import eu.dandelion.Domain.RelatednessMatrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import static java.lang.System.out;
import java.net.URL;
import java.util.Base64;
import objectsizefetcher.ObjectSizeFetcher;

public class MemoryMoniton {

    public static void PrintAllocatedMemory(long before, long after) {
        out.println("Allocated Memory: " + (after - before));
    }

    public static long getAllocatedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public static String SizeOf(String name, Object o) {
        return "SizeOf " + name + " Type " + o.getClass() + " -> " + ObjectSizeFetcher.getObjectSize(o) + " Byte";
    }

    public void AverageDump() throws Exception {
        URL url = getClass().getResource("dump.txt");
        File file = new File(url.getPath());
        BufferedReader fbr = new BufferedReader(new FileReader(file));
        String line = new String();

        try {
            RelatednessMatrix data = new RelatednessMatrix();
            long nullCounter = 0;
            long relatednessCounter = 0;
            int max_id = Integer.parseInt(fbr.readLine().trim());
            int nodesSize = Integer.parseInt(fbr.readLine().trim());
            data.configuredMinRel = Float.parseFloat(fbr.readLine().trim());
            data.configuredMinIntersection = Integer.parseInt(fbr.readLine().trim());
            data.threshold = Float.parseFloat(fbr.readLine().trim());

            data.map = new int[max_id + 1];
            data.matrix = new byte[nodesSize][];

            String END_OF_FILE = "" + '\0';

            while ((line = fbr.readLine()) != null) {
                if (line.trim().equals(END_OF_FILE.trim())) {
                    break;
                }
                String[] splittedLine = line.split("\\s+");
                if (splittedLine.length != 3) {
                    throw new Exception("Wrong format relatedness file for the line: " + line);
                }

                if (splittedLine[2].equals("null")) {
                    nullCounter++;
                } else {
                    byte[] a = Base64.getDecoder().decode(splittedLine[2].toString());
                    relatednessCounter += a.length / 3;
                }
            }
            System.out.println("Numero di entita' completamente scorrelate" + nullCounter);
            System.out.println("Numero totale di relatedness nel dump" + (relatednessCounter));
            System.out.println("Numero medio di relatedness per entita" + (relatednessCounter / nodesSize));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
