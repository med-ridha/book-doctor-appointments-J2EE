package tn.ridha.Controllers;

import java.io.IOException;

import java.sql.Date;

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
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/Signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		Date birthdate;
		UserDao userDao = new UserDao();
		UserBean userBean = new UserBean();

		String name = request.getParameter("name");
		if (name.length() == 0) {
			error = "empty name!";
		}

		String surname = request.getParameter("surname");
		if (surname.length() == 0) {
			error = "empty surname!";
		}
		String email = request.getParameter("email");
		if (email.length() == 0) {
			error = "empty email!";
		}
		String stringbirthdate = request.getParameter("birthdate");
		if (stringbirthdate.length() == 0) {
			error = "invalid birthdate!!";
		}else {
			birthdate = Date.valueOf(stringbirthdate);
			userBean.setBirthdate(birthdate);
		}
		String plainPassword = request.getParameter("password");
		if (plainPassword.length() == 0) {
			error = "empty Password!";
		}
		String hashedPassword = MD5.getMd5(plainPassword); 
		userBean.setName(name);
		userBean.setSurname(surname);
		userBean.setEmail(email);
		userBean.setPassword(hashedPassword);
		userBean.setLevel("user");
		if (userDao.get("email", email) != null) {
			error = "email already exists";
		}
		if (error.length() > 0) {
			request.setAttribute("error", error);
			request.getRequestDispatcher("/Signup.jsp").forward(request, response);
		}else
		if(userDao.insert(userBean) > 0) {
			userBean = userDao.get("email", email);
			Cookie cookie = new Cookie("email", userBean.getEmail());
			Cookie cookie2 = new Cookie("hashedPassword", hashedPassword);
			// cookie last for 30 days
			cookie.setMaxAge(60*60*24*30);
			cookie2.setMaxAge(60*60*24*30);
			response.addCookie(cookie);
			response.addCookie(cookie2);
			HttpSession session = request.getSession();
			session.setAttribute("userData", userBean);
			response.sendRedirect("/Projet/Welcome");
		}
	}
}
