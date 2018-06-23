package eu.dandelion.Business.Implementations;

import eu.dandelion.Business.Implementations.Base.BaseImplementation;
import eu.dandelion.Business.Implementations.Base.IBaseImplementation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

public class HashMapImplementation extends BaseImplementation implements IBaseImplementation {
    
    private HashMap _hashMap;

    @Override
    public boolean isDumpLoaded()
    {
        return _hashMap != null;
    }
    
    public HashMapImplementation(String dump) throws Exception {
        super(dump);
    }

    @Override
    public float Relatedness(int a, int b) throws Exception {

        if (a == b) {
            return 1f;
        }

        int nodeA = _map[a];
        int nodeB = _map[b];

        if (nodeA < 0 || nodeB < 0) {
            return 0f;
        }

        String key = (nodeA > nodeB) ? (nodeA + "." + nodeB) : (nodeB + "." + nodeA);
        try {
            byte value = (byte) _hashMap.get(key);

            if (value == 0) {
                return 0f;
            } else {
                int byint = value + 128;
                float byteRel = byint / 255f;
                return _configuredMinRel + byteRel * (1 - _configuredMinRel);
            }
        } catch (Exception e) {
            return 0f;
        }
    }

    @Override
    public void ReadDump(String dump) throws Exception {
        BufferedReader fbr = new BufferedReader(new FileReader(dump));
        String line = new String();

        try {
            _maxId = Integer.parseInt(fbr.readLine().trim());
            _nodesSize = Integer.parseInt(fbr.readLine().trim());
            _configuredMinRel = Float.parseFloat(fbr.readLine().trim());
            _configuredMinIntersection = Integer.parseInt(fbr.readLine().trim());
            _threshold = Float.parseFloat(fbr.readLine().trim());

            _map = new int[_maxId + 1];
            _hashMap = new HashMap();

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
                _map[wid] = node;

                if (!splittedLine[2].equals("null")) {
                    byte[] postingList = Base64.getDecoder().decode(splittedLine[2]);
                    for(int i = 0; i < postingList.length /4; i++)
                    {
                        int index = i * 4;
                        int idx_value =   ((postingList[index] & 0xFF) << 16)
                                        + ((postingList[index + 1] & 0xFF) << 8)
                                        + ((postingList[index + 2] & 0xFF) /* << 0 */);

                        String key = idx_value + "." + node;
                        _hashMap.put(key, postingList[index + 3]);
                    }
        
                } 
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            _hashMap = null;
        }
    }
}
