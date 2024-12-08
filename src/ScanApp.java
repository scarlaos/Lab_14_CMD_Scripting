import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

import static java.nio.file.StandardOpenOption.CREATE;

public class ScanApp {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        int wordCount = 0;
        int charCount = 0;        int lineCount = 0;

        try {
            if (args.length > 0) {
                selectedFile = new File(args[0]);
                if (!selectedFile.exists()) {
                    System.out.println("The file " + args[0] + " does not exist.");
                    return;
                }
            } else {
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                } else {
                    System.out.println("The program could not open a file.");
                    return;
                }
            }

            Path file = selectedFile.toPath();
            InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            int line = 0;
            while (reader.ready()) {
                rec = reader.readLine();
                line++;
                lineCount++;
                System.out.printf("\nLine %4d %-60s ", line, rec);

                StringTokenizer tokenizer = new StringTokenizer(rec);
                int totalWordCount = tokenizer.countTokens();
                wordCount += totalWordCount;

                int totalCharCount = rec.length();
                charCount += totalCharCount;
            }
            reader.close();
            System.out.println("\n\n\n\n");
            System.out.println("File: " + selectedFile.getName());
            System.out.printf("Total words: %d\nTotal characters: %d\nTotal lines: %d", wordCount, charCount, lineCount);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
