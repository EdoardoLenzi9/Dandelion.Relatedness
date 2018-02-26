package eu.dandelion;

import eu.dandelion.Dump.DumpGenerator;
import eu.dandelion.Implementations.HashMapImplementation;
import eu.dandelion.Implementations.NativeImplementation;
import static java.lang.System.out;

public class Main {

    public static String generatedDump = "../generatedDump.txt";
    public static String dump = "../dump.txt";

    public static void main(String[] args) throws Exception {
        //DumpGenerator.Generate(60000, 50000, 0.57, 0.005);
        long before;
        long after;
        /*
        before = getAllocatedMemory();
        NativeImplementation nativeImplementation = new NativeImplementation(generatedDump);
        
        after = getAllocatedMemory();
        PrintAllocatedMemory(before, after);
        HashMapImplementation hashMapImplementation = new HashMapImplementation(generatedDump);
        
        before = after;
        after = getAllocatedMemory();
        PrintAllocatedMemory(before, after);
         */

        NativeImplementation nativeImplementation = new NativeImplementation(dump);
        out.println(nativeImplementation.Relatedness(53675726, 53675670));//33590, 33580));
        HashMapImplementation hashMapImplementation = new HashMapImplementation(dump);
        out.println(hashMapImplementation.Relatedness(53675726, 53675670));

    }
}
