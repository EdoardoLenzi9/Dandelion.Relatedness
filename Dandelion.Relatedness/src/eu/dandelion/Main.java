package eu.dandelion;

import eu.dandelion.Business.Implementations.HashMapImplementation;
import eu.dandelion.Business.Implementations.NativeImplementation;
import eu.dandelion.Business.Services.RandomDumpGenerator;
import eu.dandelion.Domain.Models.Localizations;
import eu.dandelion.Domain.Models.TestCase;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        out.println("Localizations:" + Localizations.MaxId);
        out.println("Process Started");
        //sleep(10000);
        
        //GenerateNewDump();
        TestHashMapImplementation();
        // TestNativeImplementation();
        
        out.println("Process Ended");
        //sleep(60 * 60 * 1000);
    }

    public static void GenerateNewDump() throws Exception {
        RandomDumpGenerator dumpGenerator = new RandomDumpGenerator();
        dumpGenerator.CreateDump(Localizations.MaxId, Localizations.NodesSize, Localizations.NullPercentage);
    }

    public static void TestNativeImplementation() throws Exception {
        // Read dump
        
        Timestamp initialReadTime = new Timestamp(System.currentTimeMillis());
        //out.println(initialReadTime + "\t Read Dump - STARTED");
        NativeImplementation nativeImplementation = new NativeImplementation(Localizations.GeneratedDumpPath);
        Timestamp finalReadTime = new Timestamp(System.currentTimeMillis());
        //out.println(finalReadTime + "\t Read Dump - ENDED");
        out.println("NATIVE READ TIME\t" + (finalReadTime.getTime() - initialReadTime.getTime()));

        // Load test cases
        Timestamp initialTestTime = new Timestamp(System.currentTimeMillis());
        //out.println(initialTestTime + "\t Testing - STARTED");
        RandomDumpGenerator dumpGenerator = new RandomDumpGenerator();
        for (TestCase test : dumpGenerator.ReadTestCases(Localizations.TestCasesPath)) {
            // Get relatedness and check value
            float relatedness = nativeImplementation.Relatedness(test.FirstId, test.SecondId);
            // out.println(test.FirstId + "\t" + test.SecondId + "NATIVE: " + test.Relatedness + "\t" + relatedness);

            if (test.Relatedness != relatedness) {
                throw new Exception("Expected relatedness and current relatedness doesn't match");
            }
        }
        Timestamp finalTestTime = new Timestamp(System.currentTimeMillis());
        //out.println(finalTestTime + "\t Testing - ENDED");
        out.println("NATIVE TEST TIME\t" + (finalTestTime.getTime() - initialTestTime.getTime()));
    }

    public static void TestHashMapImplementation() throws Exception {
        // Read dump
        Timestamp initialReadTime = new Timestamp(System.currentTimeMillis());
        //out.println(initialReadTime + "\t Read Dump - STARTED");
        HashMapImplementation hashMapImplementation = new HashMapImplementation(Localizations.GeneratedDumpPath);
        Timestamp finalReadTime = new Timestamp(System.currentTimeMillis());
        //out.println(finalReadTime + "\t Read Dump - ENDED");
        out.println("HASH READ TIME\t" + (finalReadTime.getTime() - initialReadTime.getTime()));

        // Load test cases
        Timestamp initialTestTime = new Timestamp(System.currentTimeMillis());
        //out.println(initialTestTime + "\t Testing - STARTED");
        RandomDumpGenerator dumpGenerator = new RandomDumpGenerator();
        for (TestCase test : dumpGenerator.ReadTestCases(Localizations.TestCasesPath)) {
            // Get relatedness and check value
            float relatedness = hashMapImplementation.Relatedness(test.FirstId, test.SecondId);
            // out.println(test.FirstId + "\t" + test.SecondId + "NATIVE: " + test.Relatedness + "\t" + relatedness);

            if (test.Relatedness != relatedness) {
                throw new Exception("Expected relatedness and current relatedness doesn't match");
            }
        }
        Timestamp finalTestTime = new Timestamp(System.currentTimeMillis());
        //out.println(finalTestTime + "\t Testing - ENDED");
        out.println("HASH TEST TIME\t" + (finalTestTime.getTime() - initialTestTime.getTime()));
    }

    public static void DockerTest() throws Exception {
        out.println("Test Start");
        List<int[]> array = new ArrayList<int[]>();
        for (int i = 0; i < 10; i++) {
            out.println("Allocates more memory (+ 100MB)");
            sleep(1000);
            array.add(new int[10000000]);
        }
        out.println("Test Sleep");
        sleep(60 * 60 * 1000);
        out.println("Test End");
    }
}
