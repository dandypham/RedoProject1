package com.revature.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Product;
import com.revature.beans.UserAccount;
import com.revature.utils.DBUtils;
import com.revature.utils.MyUtils;

/**
 * Servlet implementation class SpecificEmployee
 */
@WebServlet("/viewSpecific")
public class SpecificEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SpecificEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/specificView.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        Connection conn = MyUtils.getStoredConnection(request);
        UserAccount loginedUser = MyUtils.getLoginedUser(session);
        String requestidStr = (String) request.getParameter("id");
        
        int requestid = 0;
        requestid = Integer.parseInt(requestidStr);
        
        String errorString = null;
        List<Product> list = null;

      
 
        if (requestidStr == null) {
            errorString = "Please input all the blank";
        }
 
        if (errorString == null) {
        	try {
                list = DBUtils.querySpecificProduct(conn, requestid);
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }
            //save information after change to views
            request.setAttribute("errorString", errorString);
            request.setAttribute("productList", list);
             
            // Forward to /WEB-INF/views/requestView.jsp
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/recordView.jsp");
            dispatcher.forward(request, response);
        }
	}
}
