package eu.dandelion;

import eu.dandelion.Business.Implementations.HashMapImplementation;
import eu.dandelion.Business.Implementations.NativeImplementation;
import eu.dandelion.Business.Services.RandomDumpGenerator;
import eu.dandelion.Domain.Models.Localizations;
import eu.dandelion.Domain.Models.TestCase;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        out.println("Process Started");
        GenerateNewDump();
        TestNativeImplementation();
        TestHashMapImplementation();
        out.println("Process Ended");
    }

    public static void GenerateNewDump() throws Exception {
        RandomDumpGenerator dumpGenerator = new RandomDumpGenerator();
        dumpGenerator.CreateDump(Localizations.MaxId, Localizations.NodesSize, Localizations.NullPercentage);
    }

    public static void TestNativeImplementation() throws Exception {
        // Read dump
        NativeImplementation nativeImplementation = new NativeImplementation(Localizations.GeneratedDumpPath);

        // Load test cases
        RandomDumpGenerator dumpGenerator = new RandomDumpGenerator();
        for (TestCase test : dumpGenerator.ReadTestCases(Localizations.TestCasesPath)) {
            // Get relatedness and check value
            float relatedness = nativeImplementation.Relatedness(test.FirstId, test.SecondId);
            // out.println(test.FirstId + "\t" + test.SecondId + "NATIVE: " + test.Relatedness + "\t" + relatedness);

            if (test.Relatedness != relatedness) {
                throw new Exception("Expected relatedness and current relatedness doesn't match");
            }
        }
    }

    public static void TestHashMapImplementation() throws Exception {
        // Read dump
        HashMapImplementation hashMapImplementation = new HashMapImplementation(Localizations.GeneratedDumpPath);

        // Load test cases
        RandomDumpGenerator dumpGenerator = new RandomDumpGenerator();
        for (TestCase test : dumpGenerator.ReadTestCases(Localizations.TestCasesPath)) {
            // Get relatedness and check value
            float relatedness = hashMapImplementation.Relatedness(test.FirstId, test.SecondId);
            // out.println(test.FirstId + "\t" + test.SecondId + "NATIVE: " + test.Relatedness + "\t" + relatedness);

            if (test.Relatedness != relatedness) {
                throw new Exception("Expected relatedness and current relatedness doesn't match");
            }
        }
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
