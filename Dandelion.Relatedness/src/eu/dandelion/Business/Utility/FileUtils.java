package eu.dandelion.Business.Utility;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtils {

    public static void Print(String path, String content) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "utf-8"))) {
            writer.write(content);
        }
    }

    public static void Println(String path, String content) throws IOException {
        Print(path, content + '\n');
    }
}
