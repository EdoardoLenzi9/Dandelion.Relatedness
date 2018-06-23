package eu.dandelion.Business.Utility;

import static java.lang.System.out;
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
}
