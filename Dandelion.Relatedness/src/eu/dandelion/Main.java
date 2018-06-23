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
        
        RandomDumpGenerator dumpGenerator = new RandomDumpGenerator();
        dumpGenerator.CreateDump(6000, 5000, 0.5);
        
        List<Integer> testCases = dumpGenerator.LoadDomain(Localizations.DomainPath);

        NativeImplementation nativeImplementation = new NativeImplementation(Localizations.GeneratedDumpPath);
        HashMapImplementation hashMapImplementation = new HashMapImplementation(Localizations.GeneratedDumpPath);
        
        for(TestCase test : dumpGenerator.ReadTestCases(Localizations.TestCasesPath)){
            float relatedness = nativeImplementation.Relatedness(test.FirstId, test.SecondId);
            out.println(test.FirstId + "\t" + test.SecondId + "NATIVE: " + test.Relatedness + "\t" + relatedness);
            relatedness = hashMapImplementation.Relatedness(test.FirstId, test.SecondId);
            out.println(test.FirstId + "\t" + test.SecondId + "HASHMAP: " + test.Relatedness + "\t" + relatedness);
        }
        //RandomDumpGenerator.randomPostingList(500, 300, 0.5);
        
        
        //GenerateDump(60000);
        /* long before;
        long after;
        
        before = getAllocatedMemory();
        NativeImplementation nativeImplementation = new NativeImplementation(generatedDump);
        
        after = getAllocatedMemory();
        PrintAllocatedMemory(before, after);
        HashMapImplementation hashMapImplementation = new HashMapImplementation(generatedDump);
        
        before = after;
        after = getAllocatedMemory();
        PrintAllocatedMemory(before, after);
  
        NativeImplementation nativeImplementation = new NativeImplementation(dump);
        out.println(nativeImplementation.Relatedness(53675726, 53675670));//33590, 33580));
        HashMapImplementation hashMapImplementation = new HashMapImplementation(dump);
        out.println(hashMapImplementation.Relatedness(53675726, 53675670));       
*/

        out.println("Process Ended");
    }
    
    public static void GenerateDump(int numberOfNodes) throws Exception {
        /*OrderedDumpGenerator generator = new OrderedDumpGenerator());
        generator.Generate(numberOfNodes, (int)(numberOfNodes * 0.85), 0.57, 0.005);*/
    }
    
    public static void DockerTest() throws Exception {
        out.println("Test Start");
        List<int[]> array = new ArrayList<int[]>();
        for(int i = 0; i < 10; i++)
        {
            out.println("Allocates more memory (+ 100MB)");
            sleep(1000);
            array.add(new int[10000000]);
        }
        out.println("Test Sleep");
        sleep(60*60*1000);
        out.println("Test End");
    }
}
