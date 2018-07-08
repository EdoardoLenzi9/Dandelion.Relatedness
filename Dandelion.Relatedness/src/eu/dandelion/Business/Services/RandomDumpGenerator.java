package eu.dandelion.Business.Services;

import eu.dandelion.Business.Implementations.NativeImplementation;
import eu.dandelion.Business.Services.Base.IDumpGenerator;
import eu.dandelion.Business.Utility.FileUtils;
import eu.dandelion.Domain.Models.DumpItem;
import eu.dandelion.Domain.Models.Localizations;
import eu.dandelion.Domain.Models.PostingListItem;
import eu.dandelion.Domain.Models.TestCase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class RandomDumpGenerator implements IDumpGenerator {

    public void CreateDump(int maxId, int nodeSize, double nullPercentage) throws Exception, FileNotFoundException, UnsupportedEncodingException, IOException {

        // init string 
        StringBuilder testCases = new StringBuilder();
        StringBuilder domainStringBuilder = new StringBuilder();
        StringBuilder readableDump = new StringBuilder();
        StringBuilder dump = new StringBuilder();
        StringBuilder mapFunction = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        HashSet domain = new HashSet();

        int[] map = new int[maxId + 1];
        int[] inverseMap = new int[nodeSize + 1];
        int mapIndex = nodeSize - 1;
        int nullCounter = 0;
        int maxNullCounter = (int) (nodeSize * nullPercentage);

        dump.append(maxId).append('\n');
        dump.append(nodeSize).append('\n');
        dump.append(Localizations.MinRel).append('\n');
        dump.append(Localizations.MinIntersection).append('\n');
        dump.append(Localizations.Treshold).append('\n');

        // generate a random domain (sorted)
        for (int i = 0; i <= nodeSize; i++) {
            domain.add(random.nextInt(maxId));
            i = domain.size();
        }

        // generate dump rows
        for (Object item : domain) {
            domainStringBuilder.append(item).append('\n');
            DumpItem tmp = new DumpItem();
            tmp.Id = (int) item;
            tmp.MapId = mapIndex--;
            inverseMap[tmp.MapId] = tmp.Id;

            if (random.nextBoolean() && nullCounter < maxNullCounter) {
                nullCounter++;
            } else {
                tmp.PostingList = new ArrayList<>();
                for (int i = tmp.Id - 1; i > 0; i--) {
                    if(domain.contains(i))
                    {
                        tmp.PostingList.add(new PostingListItem(map[i], (random.nextInt(255) + 1)));                    
                    }
                }
            }
            readableDump.append(tmp.Id).append("\t").append(tmp.MapId).append("\t");
            mapFunction.append(tmp.Id).append("\t").append(tmp.MapId).append("\n");
            map[tmp.Id] = tmp.MapId;
            
            if (tmp.PostingList != null) {
                for (PostingListItem postingListItem : tmp.PostingList) {
                    readableDump.append(postingListItem.Key).append("-").append(postingListItem.Value).append("\t");
                }
            } else 
            {
                readableDump.append("null");
            }
            readableDump.append("\n");
            dump.append(tmp.toString());
        }
        
        FileUtils.Print(Localizations.GeneratedMapPath, mapFunction.toString());
        FileUtils.Print(Localizations.GeneratedDumpPath, dump.toString());
        FileUtils.Print(Localizations.DomainPath, domainStringBuilder.toString());
        FileUtils.Print(Localizations.GeneratedReadableDumpPath, readableDump.toString());
        
        // TestCases        
        NativeImplementation nativeImplementation = new NativeImplementation(Localizations.GeneratedDumpPath);
        ArrayList<Integer> testDomain = new ArrayList(domain);
        for(int i = 0; i < Localizations.NumberOfTests; i++)
        {
            int firstId = testDomain.get(random.nextInt(nodeSize - 1));
            int secondId = testDomain.get(random.nextInt(nodeSize - 1));
            float relatedness = nativeImplementation.Relatedness(firstId, secondId);
            if(relatedness != 0.0)
            {
                testCases.append(firstId).append(';').append(secondId).append(';').append(relatedness).append('\n');
            } else {
                i--;
            }
        }
        
        FileUtils.Print(Localizations.TestCasesPath , testCases.toString());
    }
    
    public List<Integer> LoadDomain(String path) throws FileNotFoundException {
        BufferedReader fbr = new BufferedReader(new FileReader(path));
        String line;
        List<Integer> list = new ArrayList();
        try {
            String END_OF_FILE = "" + '\0';

            while ((line = fbr.readLine()) != null) {
                if (line.trim().equals(END_OF_FILE.trim())) {
                    break;
                }
                list.add(Integer.parseInt(line));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<TestCase> ReadTestCases(String path) throws FileNotFoundException {
        BufferedReader fbr = new BufferedReader(new FileReader(path));
        String line;
        List<TestCase> testCases = new ArrayList();
        try {
            String END_OF_FILE = "" + '\0';

            while ((line = fbr.readLine()) != null) {
                if (line.trim().equals(END_OF_FILE.trim())) {
                    break;
                }

                String[] splittedLine = line.split(";");

                if (splittedLine.length != 3) {
                    throw new Exception("Wrong format testcase file for the line: " + line);
                }

                testCases.add(new TestCase(splittedLine));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return testCases;
    }
}
