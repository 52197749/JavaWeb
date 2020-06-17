package com.yjl.servlet;

import com.yjl.entity.PetOwner;
import com.yjl.service.PetOwnerService;
import com.yjl.service.impl.PetOwnerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        try{
            PetOwner petOwner = petOwnerService.login(username, password);
            if(petOwner != null){
                request.setAttribute("petOwner", petOwner);
                HttpSession session = request.getSession();
                session.setAttribute("petOwner", petOwner);
                request.getRequestDispatcher("home.jsp").forward(request, response);
            }else{
                request.setAttribute("error", "用户名或密码错误。");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
