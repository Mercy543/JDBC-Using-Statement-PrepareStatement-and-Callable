package com.ecomerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comm.ecomerce.connection.DBConnection;

/**
 * Servlet implementation class AddProductByCallable
 */
@WebServlet("/add-product-callable")
public class AddProductByCallable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductByCallable() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("add-product-callable.html");
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
					//String query ="insert into  eproduct(name,price ) values (?,?) "; ---preparestatement
					//callable(stored procedure call)---use call to call a stored procedure
					
					String query ="call add_eproduct(?,?)"; 
					
					//3. Create a statement
					
					//Statement stm =conn.getConnection().createStatement();  --statement type
					
					//Prepare statement call
					//PreparedStatement pstm =conn.getConnection().prepareStatement(query); --prepare statement,parameterized statement
					
					//3. Create a callable statement (stored procedure call)
					 CallableStatement cstm = conn.getConnection().prepareCall(query);
					
					//set parameters
					cstm.setString(1, name);
					cstm.setDouble(2, price);
					
					//4. Execute query
					int noOfRowsAffected = cstm.executeUpdate();
					out.print("<h3>Number of Products Added "+noOfRowsAffected + "</h3>");
					
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
