package eu.dandelion.Dump;

import com.google.common.io.Files;
import eu.dandelion.Domain.DumpItem;
import eu.dandelion.Domain.PostingListItem;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.System.out;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DumpRandomGenerator {

    public static void randomPostingList(int maxId, int nodeSize, double nullPercentage) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        StringBuilder testCase = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append(maxId + "\n");
        sb.append(nodeSize + "\n");
        sb.append(0.01 + "\n");
        sb.append(2 + "\n");
        sb.append(1.0 + "\n");

        int[] inverseMap = new int[nodeSize + 1];

        Random random = new Random(System.currentTimeMillis());

        // generate random domain (not sorted)
        List domain = new ArrayList();
        for (int i = 0; i < nodeSize; i++) {
            int item = random.nextInt(maxId);
            if (!domain.contains(item)) {
                domain.add(item);
            } else {
                i--;
            }
        }

        // generate dump rows>();
        int mapIndex = 0;
        int nullCounter = 0;
        int maxNullCounter = (int) (nodeSize * nullPercentage);

        for (Object item : domain) {
            DumpItem tmp = new DumpItem();
            tmp.Id = (int) item;
            tmp.MapId = mapIndex++;
            inverseMap[tmp.MapId] = tmp.Id;

            int testIndex = 0;
            if (tmp.MapId > 0) {
                testIndex = random.nextInt(tmp.MapId);
            }

            if (random.nextBoolean() && nullCounter < maxNullCounter) {
                nullCounter++;
                testCase.append(tmp.Id + ";" + tmp.MapId + ";" + inverseMap[testIndex] + ";" + testIndex + ";" + 0 + "\n");
            } else {
                tmp.PostingList = new ArrayList<>();
                for (int i = 0; i < tmp.MapId; i++) {
                    tmp.PostingList.add(new PostingListItem(i, (int) (Math.random() * 255)));
                }
                if (tmp.PostingList.size() > 0) {
                    PostingListItem testPostingListItem = tmp.PostingList.get(testIndex);
                    testCase.append(tmp.Id + ";" + tmp.MapId + ";" + inverseMap[testPostingListItem.Key] + ";" + testPostingListItem.Key + ";" + testPostingListItem.Value + "\n");
                }
            }

            sb.append(tmp.toString());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("src/eu/dandelion/generatedDump.txt"), "utf-8"))) {
            writer.write(sb.toString());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("src/eu/dandelion/testCases.txt"), "utf-8"))) {
            writer.write(testCase.toString());
        }
    }
}
