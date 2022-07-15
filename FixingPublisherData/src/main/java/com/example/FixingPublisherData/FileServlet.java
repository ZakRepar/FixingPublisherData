package com.example.FixingPublisherData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/fileServlet")
public class FileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        File currentDatabase = new File(request.getParameter("database"));
        File updates = new File(request.getParameter("update"));

        File outputFile = FixingPublisherDataApplication.updateDatabase(currentDatabase, updates);
        String htmlResponse = "<a href=\"" + outputFile + "\" download>";
        PrintWriter out = response.getWriter();
        response.setContentType("file/html");
        out.println("<h1>Hello World</h1>");
        out.print("<a href=" + outputFile + ">Updated Database</a");
    }
}
