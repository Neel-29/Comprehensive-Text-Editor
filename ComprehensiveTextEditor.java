import java.io.*;
import java.util.*;
import java.nio.file.*;
import org.apache.poi.xwpf.usermodel.*;

public class ComprehensiveTextEditor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the file path: ");
        String filePath = sc.nextLine();

        File file = new File(filePath);

        try {
            if (!file.exists()) {
                System.out.println("The file does not exist. Creating a new file...");
                file.createNewFile();
            } else {
                // If the file exists, read and display its content
                System.out.println("File Content:");
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }

            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Append content to the file");
                System.out.println("2. Search for a word");
                System.out.println("3. Replace text");
                System.out.println("4. Save to a new file");
                System.out.println("5. Load content from a file");
                System.out.println("6. Word Frequency Analysis");
                System.out.println("7. Format Text (Bold/Italic/Underline)");
                System.out.println("8. Highlight Text");
                System.out.println("9. Convert to .docx");
                System.out.println("10. Text Statistics");
                System.out.println("11. Read File Content");
                System.out.println("12. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();  // Consume the newline

                switch (choice) {
                    case 1:
                        appendContentToFile(file, sc);
                        break;
                    case 2:
                        searchWordInFile(file, sc);
                        break;
                    case 3:
                        replaceTextInFile(file, sc);
                        break;
                    case 4:
                        saveToFile(file, sc);
                        break;
                    case 5:
                        loadFromFile(file, sc);
                        break;
                    case 6:
                        wordFrequencyAnalysis(file);
                        break;
                    case 7:
                        formatTextInFile(file, sc);
                        break;
                    case 8:
                        highlightTextInFile(file, sc);
                        break;
                    case 9:
                        convertToDocx(file, sc);
                        break;
                    case 10:
                        textStatistics(file);
                        break;
                    case 11:
                        readContentFromFile(file);
                        break;
                    case 12:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void appendContentToFile(File file, Scanner sc) throws IOException {
        System.out.println("Enter the content to write to the file (type '</' to stop):");
        FileWriter fileWriter = new FileWriter(file, true); // 'true' for appending
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String line;
        while (!(line = sc.nextLine()).equalsIgnoreCase("</")) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        System.out.println("Content has been appended to the file.");
    }

    public static void searchWordInFile(File file, Scanner sc) throws IOException {
        System.out.print("Enter a word to search for in the file: ");
        String searchWord = sc.nextLine();
        int searchWordCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (word.equalsIgnoreCase(searchWord)) {
                        searchWordCount++;
                    }
                }
            }
        }
        System.out.println("Occurrences of '" + searchWord + "': " + searchWordCount);
    }

    public static void replaceTextInFile(File file, Scanner sc) throws IOException {
        System.out.print("Enter a word to replace in the file: ");
        String wordToReplace = sc.nextLine();
        System.out.print("Enter the replacement word: ");
        String replacement = sc.nextLine();

        String fileContent = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll(wordToReplace, replacement);
                fileContent += line + System.lineSeparator();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContent);
        }
        System.out.println("Replacement completed.");
    }

    public static void saveToFile(File file, Scanner sc) throws IOException {
        System.out.print("Enter the new file path to save the content: ");
        String newFilePath = sc.nextLine();
        File newFile = new File(newFilePath);

        try {
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Content has been saved to the new file.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void loadFromFile(File file, Scanner sc) throws IOException {
        System.out.print("Enter the file path to load content from: ");
        String loadFilePath = sc.nextLine();
        File loadFile = new File(loadFilePath);
        if (loadFile.exists()) {
            String fileContent = "";
            try (BufferedReader br = new BufferedReader(new FileReader(loadFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent += line + System.lineSeparator();
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(fileContent);
            }
            System.out.println("Content has been loaded from the specified file.");
        } else {
            System.out.println("File not found. Content not loaded.");
        }
    }

    public static void wordFrequencyAnalysis(File file) throws IOException {
        Map<String, Integer> wordFrequency = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    word = word.toLowerCase();
                    word = word.replaceAll("[^a-zA-Z]", "");
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }
        System.out.println("Word Frequency Analysis:");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void formatTextInFile(File file, Scanner sc) throws IOException {
        System.out.print("Enter the word or phrase to format: ");
        String textToFormat = sc.nextLine();
        System.out.println("Choose a format option:");
        System.out.println("1. Bold");
        System.out.println("2. Italic");
        System.out.println("3. Underline");
        System.out.print("Enter the format option: ");
        int formatChoice = sc.nextInt();
        sc.nextLine();

        String fileContent = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (formatChoice == 1) {
                    line = line.replaceAll(textToFormat, "<b>" + textToFormat + "</b>");
                } else if (formatChoice == 2) {
                    line = line.replaceAll(textToFormat, "<i>" + textToFormat + "</i>");
                } else if (formatChoice == 3) {
                    line = line.replaceAll(textToFormat, "<u>" + textToFormat + "</u>");
                }
                fileContent += line + System.lineSeparator();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContent);
        }
        System.out.println("Text formatting completed.");
    }

    public static void highlightTextInFile(File file, Scanner sc) throws IOException {
        System.out.print("Enter the word or phrase to highlight: ");
        String textToHighlight = sc.nextLine();
        String fileContent = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Highlight the text with HTML tags
                line = line.replaceAll(textToHighlight, "<mark>" + textToHighlight + "</mark>");
                fileContent += line + System.lineSeparator();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContent);
        }
        System.out.println("Text highlighting completed.");
    }

    public static void convertToDocx(File file, Scanner sc) throws IOException {
        System.out.print("Enter the destination .docx file path: ");
        String docxFilePath = sc.nextLine();

        XWPFDocument document = new XWPFDocument();

        try (BufferedReader br = new BufferedReader(new FileReader(file)) ;
             FileOutputStream out = new FileOutputStream(docxFilePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(line);
            }
            document.write(out);
            System.out.println("File converted to .docx");
        }
    }

    public static void readContentFromFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void textStatistics(File file) throws IOException {
        int wordCount = 0;
        int charCount = 0;
        int numberCount = 0;
        int lineCount = 0;
        int spaceCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                spaceCount += line.split("\\s+").length - 1;
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordCount++;
                    if (word.matches("[0-9]+")) {
                        numberCount++;
                    }
                }
            }
        }

        System.out.println("Text Statistics:");
        System.out.println("Word Count: " + wordCount);
        System.out.println("Character Count: " + charCount);
        System.out.println("Number Count: " + numberCount);
        System.out.println("Line Count: " + lineCount);
        System.out.println("Space Count: " + spaceCount);
    }
}