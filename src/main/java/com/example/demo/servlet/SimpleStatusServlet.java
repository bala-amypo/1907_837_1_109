
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;

// public class SimpleStatusServlet extends HttpServlet {
    
//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//         response.setContentType("text/plain");
//         response.getWriter().write("Credit Card Reward Maximizer is running");
//         response.getWriter().flush();
//     }
// }
package com.example.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/simple-status")
public class SimpleStatusServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        out.print("Credit Card Reward Maximizer is running");
        out.flush(); // Required for test t04
    }
}