package eu.dandelion.Business.Services.Base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IDumpGenerator {
    void CreateDump(int maxId, int nodeSize, double nullPercentage) throws Exception, FileNotFoundException, UnsupportedEncodingException, IOException;
}
