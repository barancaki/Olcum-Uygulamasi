package processor;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class OutputWriter {

    public static void writeToFile(String basePath, String type, String calcType, List<String> contents) {
        try {
            File outDir = new File(basePath + "/sonuc/" + type);
            if (!outDir.exists()) outDir.mkdirs();

            File outFile = new File(outDir, calcType + ".txt");
            FileWriter writer = new FileWriter(outFile);

            for (String line : contents) {
                writer.write(line + "\n");
            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Yazma hatasi: " + e.getMessage());
        }
    }
}
