package tn.ridha.Controllers;

import java.io.IOException;

import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tn.ridha.Beans.UserBean;
import tn.ridha.Dao.MD5;
import tn.ridha.Dao.UserDao;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean userData = (UserBean) session.getAttribute("userData");
		if (userData == null || userData.getName() == null) {
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("/Profile.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String error = "";
		UserBean oldUserBean = (UserBean) session.getAttribute("userData");
		UserBean userBean = new UserBean();
		UserDao userDao = new UserDao();
		String email = request.getParameter("email");
		if (!email.equals(oldUserBean.getEmail()) && userDao.get("email", email) != null ) {
			error = "email already exists";
			request.setAttribute("error", error);
			request.getRequestDispatcher("/Profile.jsp").forward(request, response);
		}else {
			userBean.set_id(oldUserBean.get_id());
			userBean.setName(request.getParameter("name"));
			userBean.setSurname(request.getParameter("surname"));
			userBean.setEmail(email);
			userBean.setLevel(oldUserBean.getLevel());
			Date date = Date.valueOf(request.getParameter("birthdate"));
			userBean.setBirthdate(date);
			String plainPassword = request.getParameter("password");
			if (plainPassword.length() > 0) {
				String hashedPassword = MD5.getMd5(plainPassword);
				userBean.setPassword(hashedPassword);
			}else {
				userBean.setPassword(oldUserBean.getPassword());
			}
			if (userDao.update(userBean) == 0) {
				error = "someting went wrong and couldn't update your profile!!";
				request.getRequestDispatcher("/Profile.jsp").forward(request, response);
			}else {
				session.setAttribute("userData", userBean);
				response.sendRedirect("/Projet/");
			}
		}
	}

}
