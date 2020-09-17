package com.ecomerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comm.ecomerce.connection.DBConnection;

/**
 * Servlet implementation class ProductDetail
 */
@WebServlet("/product-detail")
public class ProductDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetail() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. create db connection
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		
		//get config
		InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.propertyfile");
		
		Properties props = new Properties();
		props.load(ins);
		
		//create a connection
		try {
			DBConnection conn = new DBConnection(props.getProperty("url"),props.getProperty("username"),props.getProperty("password"));
		if (conn !=null)
		{
			//2. create a query
			String query ="Select * from eproduct";
			
			//3. Create a statement
			
			Statement stm =conn.getConnection().createStatement();
			
			//4. Execute query
			ResultSet rstm = stm.executeQuery(query);
			
			while (rstm.next()) {
				out.print("<br>-----------------------------------</br>");
				out.print(rstm.getInt(1) + "  " + rstm.getString(2) + "  " + rstm.getDouble(3));
				
			      }
			rstm.close();
			stm.close();
			
		  }
			
		conn.closeConnection();
		out.print("<h3>The DB connection is closed !</h3>");
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//create a query
		//execue query
		//print product detail
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
