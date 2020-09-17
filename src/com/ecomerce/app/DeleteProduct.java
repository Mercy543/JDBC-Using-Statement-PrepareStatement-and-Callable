package com.ecomerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comm.ecomerce.connection.DBConnection;

/**
 * Servlet implementation class DeleteProduct
 */
@WebServlet("/delete-product")
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProduct() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("delete-product.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//fetch data
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		
		
		
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
					
					String query ="Delete from eproduct where p_id=? ";
					
					//3. Create a statement
					
					//Statement stm =conn.getConnection().createStatement();  --statement type
					
					//Prepare statement call
					PreparedStatement pstm =conn.getConnection().prepareStatement(query);
					
					//set parameters
					pstm.setInt(1, pid);
					
					
					//4. Execute query
					int noOfRowsAffected = pstm.executeUpdate();
					out.print("<h3>Number of Products deleted "+noOfRowsAffected + "</h3>");
					
					pstm.close();
				  }
					
				conn.closeConnection();
				out.print("<h3>The DB connection is closed !</h3>");
				
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}

}
