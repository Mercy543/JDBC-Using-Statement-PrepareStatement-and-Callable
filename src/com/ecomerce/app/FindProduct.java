package com.ecomerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comm.ecomerce.connection.DBConnection;

/**
 * Servlet implementation class FindProduct
 */
@WebServlet("/find-product")
public class FindProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindProduct() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("find-product.html");
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
							//2. Create a query
							
							String query ="call find_eproduct(?)"; 													
														
							
							//3. Create a callable statement (stored procedure call)
							 CallableStatement cstm = conn.getConnection().prepareCall(query);
							
							 
							//set parameters
							cstm.setInt(1, pid);
							
							//set parameters
							cstm.setInt(1, pid);
							
							
							//4. Execute query
							 ResultSet rs = cstm.executeQuery();
							 
							 while (rs.next()) {
									out.print("<br>-----------------------------------</br>");
									out.print(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getDouble(3));
									
								      }
							
							
							cstm.close();
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
