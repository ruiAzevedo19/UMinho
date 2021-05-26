package com.gms;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Game", urlPatterns = "/Game")
public class Servlet extends HttpServlet {
    private Facade gms;

    public void init() {
        //gms = new Facade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("Testing...");
        //Game g = gms.getGameByName("GTA V");
        //System.out.println(g);

        request.setAttribute("game", "Game 1");

        getServletConfig().getServletContext()
                .getRequestDispatcher("/WEB-INF/index.jsp").forward(request,response);
    }


    public void destroy() {
    }
}