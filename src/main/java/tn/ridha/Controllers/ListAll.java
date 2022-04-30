package tn.ridha.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tn.ridha.Beans.UserBean;
import tn.ridha.Dao.UserDao;

/**
 * Servlet implementation class ListAll
 */
@WebServlet("/List")
public class ListAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListAll() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) {
			UserBean userData = (UserBean) session.getAttribute("userData");
			if (userData == null || userData.getName() == null) {
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}
			else {
				if (!userData.getLevel().equals("admin")) {
					response.sendRedirect("/Projet/");
					return;
				}
				String filter = request.getParameter("filter");
				String deleteId = request.getParameter("deleteId");
				String message = "";
				UserDao userDao = new UserDao();
				if (deleteId != null) {
					UserBean userToDelete = userDao.get("_id", deleteId);
					if (userToDelete == null) {
						message = "there is no user by that id";
					}
					else if (userToDelete.get_id() == userData.get_id()) {
						message = "I am sorry, you can't delete Yourself!! if you would like to delete your account please contact another admin";
					}
					else {
						int r = userDao.delete(userToDelete);
						if (r > 0) {
							message = "user " + userToDelete.getName()+" has been deleted";
						}else {
							message = "oops!! there was a problem deleting that user";
						}
					}
				}
				request.setAttribute("filter", filter);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/ListAll.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
