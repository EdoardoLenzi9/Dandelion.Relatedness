package eu.dandelion.Implementations;

import eu.dandelion.Domain.RelatednessHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

public class HashMapImplementation {

    public static RelatednessHashMap data;

    public HashMapImplementation(String dump) throws Exception {
        data = ReadDump(dump);
    }

    public float Relatedness(int a, int b) throws Exception {

        if (a == b) {
            return 1f;
        }

        int nodeA = data.map[a];
        int nodeB = data.map[b];

        if (nodeA < 0 || nodeB < 0) {
            return 0f;
        }

        String key = (nodeA > nodeB) ? (nodeA + "." + nodeB) : (nodeB + "." + nodeA);
        try {
            byte value = (byte) data.hm.get(key);

            if (value == 0) {
                return 0f;
            } else {
                int byint = value + 128;
                float byteRel = byint / 255f;
                return data.configuredMinRel + byteRel * (1 - data.configuredMinRel);
            }
        } catch (Exception e) {
            return 0f;
        }
    }

    public RelatednessHashMap ReadDump(String dump) throws Exception {
        URL url = getClass().getResource(dump);
        File file = new File(url.getPath());
        BufferedReader fbr = new BufferedReader(new FileReader(file));
        String line = new String();

        try {

            RelatednessHashMap data = new RelatednessHashMap();
            int max_id = Integer.parseInt(fbr.readLine().trim());
            int nodesSize = Integer.parseInt(fbr.readLine().trim());
            data.configuredMinRel = Float.parseFloat(fbr.readLine().trim());
            data.configuredMinIntersection = Integer.parseInt(fbr.readLine().trim());
            data.threshold = Float.parseFloat(fbr.readLine().trim());

            data.map = new int[max_id + 1];
            data.hm = new HashMap();

            String END_OF_FILE = "" + '\0';

            while ((line = fbr.readLine()) != null) {
                if (line.trim().equals(END_OF_FILE.trim())) {
                    break;
                }

                String[] splittedLine = line.split("\\s+");
                if (splittedLine.length != 3) {
                    throw new Exception("Wrong format relatedness file for the line: " + line);
                }

                int wid = Integer.parseInt(splittedLine[0]);
                int node = Integer.parseInt(splittedLine[1]);
                data.map[wid] = node;

                if (!splittedLine[2].equals("null")) {
                    byte[] postingList = Base64.getDecoder().decode(splittedLine[2].toString());

                    int idx_value = ((postingList[0] & 0xFF) << 16)
                            + ((postingList[1] & 0xFF) << 8)
                            + ((postingList[2] & 0xFF) << 0);

                    String key = idx_value + "." + node;
                    data.hm.put(key, postingList[3]);
                }
            }

            return data;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
