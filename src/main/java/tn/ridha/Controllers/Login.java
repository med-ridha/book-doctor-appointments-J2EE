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
import tn.ridha.Dao.MD5;
import tn.ridha.Dao.UserDao;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String plainPassword = request.getParameter("password");
		String hashedPassword = MD5.getMd5(plainPassword);
		String error = "";
		try {
			UserDao userDao = new UserDao();
			UserBean userBean = userDao.get("email", email);
			if (userBean == null) {
				error = "Email or password incorrect";
				request.setAttribute("error", error);
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}else {
				if (hashedPassword.equals(userBean.getPassword())) {
					HttpSession session = request.getSession();
					session.setAttribute("userData", userBean);
					Cookie cookie = new Cookie("email", userBean.getEmail());
					Cookie cookie2 = new Cookie("hashedPassword", hashedPassword);
					// cookie last for 30 days
					cookie.setMaxAge(60*60*24*30);
					cookie2.setMaxAge(60*60*24*30);
					response.addCookie(cookie);
					response.addCookie(cookie2);
					response.sendRedirect("/Projet/");
				}else {
					error = "Email or password incorrect!";
					request.setAttribute("error", error);
					request.getRequestDispatcher("/Login.jsp").forward(request, response);
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
