package eu.dandelion.Implementations;

import eu.dandelion.Domain.RelatednessMatrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import static java.lang.System.out;
import java.net.URL;
import java.util.Base64;

public class NativeImplementation {

    public static RelatednessMatrix data;

    public NativeImplementation(String dump) throws Exception {
        data = ReadDump(dump);
    }

    public static void printPostingList(byte[] postingList) {
        out.println("-------------------------------------------");
        for (int i = 0; i < postingList.length; i++) {
            int key = ((postingList[i++] & 0xFF) << 16)
                    + ((postingList[i++] & 0xFF) << 8)
                    + ((postingList[i++] & 0xFF) << 0);
            float value = decodeRelatedness(postingList[i]);
            out.println("\t" + key + " ->" + value);
        }
        out.println("-------------------------------------------");
    }

    public static float decodeRelatedness(byte value) {
        int byint = value + 128;
        float byteRel = byint / 255f;
        return data.configuredMinRel + byteRel * (1 - data.configuredMinRel);
    }

    public static float Relatedness(int a, int b) throws Exception {

        if (a == b) {
            return 1f;
        }

        int nodeA = data.map[a];
        int nodeB = data.map[b];

        if (nodeA < 0 || nodeB < 0) {
            return 0f;
        }

        byte[] array = a > b ? data.matrix[nodeA] : data.matrix[nodeB];
        out.println("node: " + (a > b ? nodeA : nodeB));
        //printPostingList(array);
        if (array == null) {
            return 0f;
        }

        int start = 0;
        int end = (array.length / 4) - 1;
        int pos = -1;
        int key = a > b ? nodeB : nodeA;

        while (pos == -1 && start <= end) {
            int middle = ((start + end)) / 2;
            int idx_value = ((array[middle * 4 + 0] & 0xFF) << 16)
                    + ((array[middle * 4 + 1] & 0xFF) << 8)
                    + ((array[middle * 4 + 2] & 0xFF));

            if (idx_value == key) {
                pos = middle;
            } else {
                if (key > idx_value) {
                    start = (middle + 1);
                } else {
                    end = (middle - 1);
                }
            }
        }

        if (pos == -1) {
            return 0f;
        } else {
            byte by = array[pos * 4 + 3]; 
            int byint = by + 128;
            float byteRel = byint / 255f;
            return data.configuredMinRel + byteRel * (1 - data.configuredMinRel);
        }
    }

    /*
    public float Relatedness(int a, int b) {
        if (a == b) {
            return 1f;
        }
        int nodeA = data.map[a];
        int nodeB = data.map[b];
        if (nodeA < 0 || nodeB < 0) {
            return 0f;
        }

        int key = a > b ? nodeB : nodeA;
        byte[] array = a > b ? data.matrix[nodeA] : data.matrix[nodeB];

        if (array == null) {
            return 0f;
        }

        int start = 0;
        int end = array.length - RelatednessMatrix.EL_SIZE;
        int pos = -1;
        while (pos == -1 && start <= end) {
            int idx = ((start + end) / RelatednessMatrix.EL_SIZE) / 2;

            int idx_value = ((array[idx * RelatednessMatrix.EL_SIZE] & 0xFF) << 16)
                    + ((array[idx * RelatednessMatrix.EL_SIZE + 1] & 0xFF) << 8)
                    + (array[idx * RelatednessMatrix.EL_SIZE + 2] & 0xFF);

            if (idx_value == key) {
                pos = idx;
            } else {
                if (key > idx_value) {
                    start = (idx + 1) * RelatednessMatrix.EL_SIZE;
                } else {
                    end = (idx - 1) * RelatednessMatrix.EL_SIZE;
                }
            }
        }

        if (pos == -1) {
            return 0f;
        } else {
            byte by = array[pos * RelatednessMatrix.EL_SIZE + 3];
            int byint = by + 128;
            float byteRel = byint / 255f;
            return data.configuredMinRel + byteRel * (1 - data.configuredMinRel);
        }
    }*/
    public RelatednessMatrix ReadDump(String dump) throws Exception {
        URL url = getClass().getResource(dump);
        File file = new File(url.getPath());
        BufferedReader fbr = new BufferedReader(new FileReader(file));
        String line = new String();

        try {   
          
            int max_id = Integer.parseInt(fbr.readLine().trim());                  
            int nodesSize = Integer.parseInt(fbr.readLine().trim());
            float minRel = Float.parseFloat(fbr.readLine().trim());
            int minIntersection = Integer.parseInt(fbr.readLine().trim());
            float threshold = Float.parseFloat(fbr.readLine().trim());         

            RelatednessMatrix data = new RelatednessMatrix();
            data.map = new int[max_id + 1];
            data.matrix = new byte[nodesSize][];
            data.configuredMinRel = minRel;
            data.configuredMinIntersection = minIntersection;
            data.threshold = threshold;

            int idx = 0;
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

                if (splittedLine[2].equals("null")) {
                    data.matrix[node] = null;
                } else {
                    data.matrix[node] = Base64.getDecoder().decode(splittedLine[2].toString());
                    //data.matrix[node] = Base64.getMimeDecoder().decode(splittedLine[2].toString());
                }
                idx++;
            }
            /*
            if (idx != nodesSize) {
                throw new Exception("Wrong format relatedness file the number of line do not match with size of matrix");
            }
             */
            return data;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
