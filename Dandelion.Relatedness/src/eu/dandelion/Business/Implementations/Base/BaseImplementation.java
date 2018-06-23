package eu.dandelion.Business.Implementations.Base;

import static java.lang.System.out;

public abstract class BaseImplementation {
   
    protected int _maxId;
    protected int _nodesSize;
    protected int _configuredMinIntersection;
    protected float _configuredMinRel;
    protected float _threshold;
    protected static final int EL_SIZE = 4;

    protected int[] _map;
    
    protected abstract void ReadDump(String dump) throws Exception;
    protected abstract boolean isDumpLoaded() throws Exception;
    
    public BaseImplementation(String dump) throws Exception {
        if(!isDumpLoaded())
        {
            ReadDump(dump);
        }
    }
    
    public void printPostingList(byte[] postingList) {
        out.println("-------------------------------------------");
        for (int i = 0; i < postingList.length; i++) {
            int key = ((postingList[i++] & 0xFF) << 16)
                    + ((postingList[i++] & 0xFF) << 8)
                    + ((postingList[i++] & 0xFF) /*<< 0*/);
            float value = decodeRelatedness(postingList[i]);
            out.println("\t" + key + " ->" + value);
        }
        out.println("-------------------------------------------");
    }

    public float decodeRelatedness(byte value) {
        int byint = value + 128;
        float byteRel = byint / 255f;
        return _configuredMinRel + byteRel * (1 - _configuredMinRel);
    }
}
