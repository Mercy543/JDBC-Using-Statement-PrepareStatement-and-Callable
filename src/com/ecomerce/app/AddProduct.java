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
 * Servlet implementation class AddProduct
 */
@WebServlet("/add-product")
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("add-product.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		Integer price = Integer.parseInt(request.getParameter("price"));
		
		
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
					//String query ="insert into  eproduct(name,price ) values('"+name+"',"+price+") ";---static
					String query ="insert into  eproduct(name,price ) values (?,?) ";
					
					//3. Create a statement
					
					//Statement stm =conn.getConnection().createStatement();  --statement type
					
					//Prepare statement call
					PreparedStatement pstm =conn.getConnection().prepareStatement(query);
					
					//set parameters
					pstm.setString(1, name);
					pstm.setDouble(2, price);
					
					//4. Execute query
					int noOfRowsAffected = pstm.executeUpdate();
					out.print("<h3>Number of Products Added "+noOfRowsAffected + "</h3>");
					
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
