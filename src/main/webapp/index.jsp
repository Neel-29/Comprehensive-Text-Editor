<%-- This is the main JSP page for the Comprehensive Text Editor application --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Comprehensive Text Editor</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        .container { max-width: 800px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"] { width: 100%; padding: 8px; box-sizing: border-box; }
        textarea { width: 100%; height: 200px; padding: 10px; box-sizing: border-box; }
        button { padding: 10px 15px; background-color: #007bff; color: white; border: none; cursor: pointer; }
        .message { color: green; margin-top: 10px; }
        .error { color: red; margin-top: 10px; }
    </style>
</head>
<body>
    <header>
        <h1>Web-Based Comprehensive Text Editor</h1>
    </header>
    <main class="container">
        
        <%-- Display messages from the Servlet --%>
        <% if (request.getAttribute("message") != null) { %>
            <div class="<%= request.getAttribute("message").toString().contains("Error") ? "error" : "message" %>">
                <strong>Message:</strong> <%= request.getAttribute("message") %>
            </div>
        <% } %>

        <h2>File Actions</h2>
        
        <form action="editor" method="post">
            <div class="form-group">
                <label for="filePath">Enter File Path (e.g., /tmp/myFile.txt):</label>
                <input type="text" id="filePath" name="filePath" required>
            </div>

            <div class="form-group">
                <button type="submit" name="action" value="readContent">1. Read Content</button>
                <button type="submit" name="action" value="textStatistics">2. Text Statistics</button>
                <%-- Add buttons for other actions here --%>
            </div>
        </form>

        <%-- Display File Content --%>
        <% if (request.getAttribute("fileContent") != null) { %>
            <h3>File Content:</h3>
            <textarea readonly><%= request.getAttribute("fileContent") %></textarea>
        <% } %>
        
        <%-- Display Text Statistics --%>
        <% if (request.getAttribute("stats") != null) { %>
            <h3>Text Statistics:</h3>
            <% Map<String, Integer> stats = (Map<String, Integer>) request.getAttribute("stats"); %>
            <ul>
                <% for (Map.Entry<String, Integer> entry : stats.entrySet()) { %>
                    <li><strong><%= entry.getKey() %>:</strong> <%= entry.getValue() %></li>
                <% } %>
            </ul>
        <% } %>
 
    </main>
    <footer>
        <p>&copy; 2023 Comprehensive Text Editor. All rights reserved.</p>
    </footer>
</body>
</html>