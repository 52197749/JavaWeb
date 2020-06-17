package com.yjl.servlet;

import com.alibaba.fastjson.JSON;
import com.yjl.entity.Pet;
import com.yjl.entity.PetOwner;
import com.yjl.entity.PetStore;
import com.yjl.service.PetOwnerService;
import com.yjl.service.PetService;
import com.yjl.service.PetStoreService;
import com.yjl.service.impl.PetOwnerServiceImpl;
import com.yjl.service.impl.PetServiceImpl;
import com.yjl.service.impl.PetStoreServiceImpl;
import com.yjl.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;


public class PetOwnerServelt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");

        String opr = request.getParameter("opr");
        if("buyPet".equals(opr)){
            //购买宠物
            buyPet(request, response);
        }else if("list".equals(opr)){
            //获取我的宠物列表
            getMyPets(request, response);
        }else if("sell".equals(opr)){
            //出售宠物
            sellPet(request, response);
        }else if("toBuyPet".equals(opr)) {
            //获取库存宠物列表，跳转到购买宠物的页面
            toBuyPet(request, response);
        }else if("toSellPet".equals(opr)) {
            //获取我的宠物信息和宠物商店信息，返回到出售页面展示
            toSellPet(request, response);
        }
    }

    private void toSellPet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:
        HttpSession session = request.getSession();
        PetOwner petOwner = (PetOwner) session.getAttribute("petOwner");
        if(petOwner == null){
            //重定向到登录页面
            response.sendRedirect("login.jsp");
            return;
        }

        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        PetStoreService petStoreService = new PetStoreServiceImpl();
        try {
            //获取我的宠物列表
            List<Pet> myPets = petOwnerService.getMyPets(petOwner.getId());
            //获取商店列表
            List<PetStore> petStores = petStoreService.getAll();

            request.setAttribute("pets", myPets);
            request.setAttribute("petStores", petStores);
            //转发到出售宠物的页面
            request.getRequestDispatcher("petOwnerSellPet.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            //跳转到错误页面
            session.setAttribute("error", "获取库存宠物列表失败，请联系管理员。");
            response.sendRedirect("error.jsp");
        }
    }


    private void toBuyPet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取库存宠物列表
        PetService petService = new PetServiceImpl();
        List<Pet> pets = null;
        HttpSession session = request.getSession();
        try {
            pets = petService.getPetStorePets();
            request.setAttribute("pets", pets);
            //转发到购买宠物的页面petOwnerBuyPet.jsp
            request.getRequestDispatcher("petOwnerBuyPet.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            //跳转到错误页面
            session.setAttribute("error", "获取库存宠物列表失败，请联系管理员。");
            response.sendRedirect("error.jsp");
        }
    }


    private void sellPet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO：
        HttpSession session = request.getSession();
        PetOwner petOwner = (PetOwner) session.getAttribute("petOwner");
        if(petOwner == null){
            //重定向到登录页面
            response.sendRedirect("login.jsp");
            return;
        }
        //调用宠物主人的出售宠物的方法
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        //通过宠物id获取宠物信息
        String petId = request.getParameter("petId");
        String storeId = request.getParameter("storeId");
        PetService petService = new PetServiceImpl();
        Pet pet = null;
        try {
            pet = petService.getPetById(Integer.parseInt(petId));
            boolean success = petOwnerService.sellPet(petOwner, pet, Integer.parseInt(storeId));

            PrintWriter out = response.getWriter();
            if(success){
                out.print(true);
            }else{
//                session.setAttribute("error", "出售宠物失败，请联系管理员。");
//                response.sendRedirect("error.jsp");
                out.print(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "出售宠物失败，请联系管理员。");
            response.sendRedirect("error.jsp");
        }
    }


    private void getMyPets(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取我的宠物列表
        HttpSession session = request.getSession();
        PetOwner petOwner = (PetOwner) session.getAttribute("petOwner");
        if(petOwner == null){
            //重定向到登录页面
            response.sendRedirect("login.jsp");
            return;
        }
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        try {

            String pageNoStr = request.getParameter("pageNo");
            int pageNo = pageNoStr==null?1:Integer.parseInt(pageNoStr);
            /*int pageSize = 5;
            int count = petOwnerService.count(petOwner.getId());
            int totalPageNo = count%pageSize==0?count/pageSize:count/pageSize+1;
            if(pageNo<1){
                pageNo=1;
            }
            if(pageNo>totalPageNo){
                pageNo=totalPageNo;
            }
            List<Pet> pets = petOwnerService.getMyPets(petOwner.getId(), pageNo, pageSize);*/

            PageUtil pageUtil = new PageUtil();
            pageUtil.setPageNo(pageNo);
            petOwnerService.getPageMyPets(petOwner.getId(), pageUtil);

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String pageInfo = JSON.toJSONStringWithDateFormat(pageUtil, "yyyy-MM-dd");
            out.print(pageInfo);

//            request.setAttribute("pets", pageUtil.getPets());
//            request.setAttribute("pageNo", pageUtil.getPageNo());//当前页
//            request.setAttribute("totalPageNo", pageUtil.getTotalPageNo());//总页数
//            request.getRequestDispatcher("petOwnerPetList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "获取宠物列表失败，请联系管理员。");
            response.sendRedirect("error.jsp");
        }
    }

    private void buyPet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();

        //PetOwner petOwner = (PetOwner) request.getAttribute("petOwner");
        HttpSession session = request.getSession();
        PetOwner petOwner = (PetOwner) session.getAttribute("petOwner");
        if(petOwner == null){

            response.sendRedirect("login.jsp");
        }

        try {
            int count = petOwnerService.buyPet(petOwner, Integer.parseInt(id));
            if(count>0){
                //response.sendRedirect("petOwnerPetList.jsp");
                response.sendRedirect("/petOwner?opr=list");
            }else{
                session.setAttribute("error", "购买宠物失败，请联系管理员");
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "购买宠物失败，请联系管理员");
            response.sendRedirect("error.jsp");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
