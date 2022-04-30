package tn.ridha.Controllers;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tn.ridha.Beans.UserBean;
import tn.ridha.Dao.UserDao;



/**
 * Servlet implementation class Home
 */
@WebServlet(name="home", urlPatterns = {"/Home", "/"})
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() {
    	
    }
    
	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		Cookie[] cookies = request.getCookies();
		String email = null;
		String hashedPassword = null; 
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("email")) {
					email = cookie.getValue();
				}
				if (cookie.getName().equals("hashedPassword")) {
					hashedPassword = cookie.getValue();
				}
			}
		}
		if (request.getParameter("Logout") != null) {
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			request.getSession().invalidate();
			response.sendRedirect("/Projet");
			return;
		}
		try {
			HttpSession session = request.getSession();
			String level = request.getParameter("level");
			if (email != null && hashedPassword != null) {
				UserBean userData = (UserBean) session.getAttribute("userData");
				if (userData == null) {
					userData = new UserBean();
				}
				if (userData.getName()==null) {
					UserDao userDao = new UserDao();
					userData = userDao.get("email", email);
					String password = userData.getPassword();
					if (hashedPassword.equals(password)) {
						if (level != null) {
							switch (level) {
							case "admin" : userData.setLevel("admin"); break;
							case "user" : userData.setLevel("user"); break;
							}
						}
						session.setAttribute("userData", userData);
						request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
						return;
					}else {
						request.getRequestDispatcher("/Login.jsp").forward(request, response);
						return;
					}
				}else{
					if (level != null) {
						switch (level) {
						case "admin" : userData.setLevel("admin"); break;
						case "user" : userData.setLevel("user"); break;
						}
					}
					request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
					return;
				}
			}else {
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
