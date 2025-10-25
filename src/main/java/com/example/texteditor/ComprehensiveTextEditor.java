package com.example.texteditor;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import org.apache.poi.xwpf.usermodel.*;

// NEW: Import Servlet dependencies
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// CHANGE: Class now extends HttpServlet to handle web requests
public class ComprehensiveTextEditor extends HttpServlet {
    // REMOVE: The main(String[] args) method is removed.

    private static final String JSP_PAGE = "/index.jsp";

    // NEW: doPost handles all form submissions from the web page
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Get parameters sent from index.jsp
        String filePath = request.getParameter("filePath");
        String action = request.getParameter("action");
        String message = "";
        
        File file = new File(filePath);

        // Handle file creation/existence check (simplified for web)
        try {
            if (!file.exists()) {
                file.createNewFile();
                message = "The file did not exist. Creating a new file: " + filePath;
            } else {
                message = "File accessed: " + filePath;
            }

            switch (action) {
                case "readContent":
                    // Call adapted method and set result as an attribute
                    String content = readContentFromFile(file);
                    request.setAttribute("fileContent", content);
                    message = "File content loaded.";
                    break;
                    
                case "appendContent":
                    String contentToAppend = request.getParameter("contentToAppend");
                    if (contentToAppend != null) {
                        appendContentToFile(file, contentToAppend);
                        message = "Content has been appended to the file.";
                    }
                    break;

                case "searchWord":
                    String searchWord = request.getParameter("searchWord");
                    if (searchWord != null) {
                        int count = searchWordInFile(file, searchWord);
                        request.setAttribute("searchWordCount", count);
                        request.setAttribute("searchWordResult", searchWord);
                        message = "Search completed.";
                    }
                    break;
                    
                case "textStatistics":
                    Map<String, Integer> stats = textStatistics(file);
                    request.setAttribute("stats", stats);
                    message = "Text statistics calculated.";
                    break;
                    
                // Implement other cases (replaceText, convertToDocx, etc.) here...

                default:
                    message = "Invalid action selected.";
            }
        } catch (IOException e) {
            message = "Error performing action: " + e.getMessage();
        }

        // Set the final message and file path to persist across the page reload
        request.setAttribute("message", message);
        request.setAttribute("filePath", filePath);
        
        // Forward the request back to the JSP page to display results
        request.getRequestDispatcher(JSP_PAGE).forward(request, response);
    }


    // --- ADAPTED UTILITY METHODS (Returning values instead of using System.out) ---

    public static void appendContentToFile(File file, String contentToAppend) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(contentToAppend);
            bufferedWriter.newLine();
        }
    }

    public static int searchWordInFile(File file, String searchWord) throws IOException {
        int searchWordCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Only match if the word is not empty
                    if (!word.isEmpty() && word.replaceAll("[^a-zA-Z]", "").equalsIgnoreCase(searchWord)) {
                        searchWordCount++;
                    }
                }
            }
        }
        return searchWordCount;
    }

    public static String readContentFromFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    public static Map<String, Integer> textStatistics(File file) throws IOException {
        int wordCount = 0;
        int charCount = 0;
        int lineCount = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) { 
                         wordCount++;
                    }
                }
            }
        }
        
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Word Count", wordCount);
        stats.put("Character Count", charCount);
        stats.put("Line Count", lineCount);
        // Add more statistics if needed
        
        return stats;
    }

    // NOTE: Other functions like replaceTextInFile, saveToFile, loadFromFile,
    // wordFrequencyAnalysis, formatTextInFile, highlightTextInFile, 
    // and convertToDocx would need similar refactoring to use 
    // HttpServletRequest parameters and return status/results.
    // They have been omitted here for brevity but follow the same pattern 
    // as the methods above.
}