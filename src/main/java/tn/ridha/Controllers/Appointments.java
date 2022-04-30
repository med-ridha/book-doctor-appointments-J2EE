package tn.ridha.Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tn.ridha.Beans.AppointmentBean;
import tn.ridha.Beans.UserBean;
import tn.ridha.Dao.AppointmentDao;

/**
 * Servlet implementation class Appointments
 */
@WebServlet("/Appointments")
public class Appointments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Appointments() {
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
			AppointmentDao appDao = new AppointmentDao();
			String deleteId = request.getParameter("deleteId");
			String message = "";
			if (deleteId != null) {
				AppointmentBean appToDelete = appDao.get("_id", deleteId);
				if (appToDelete == null) {
					message = "there is no appointment by that id";
				}
				else {
					int r = appDao.delete(appToDelete);
					if (r > 0) {
						message = "appointment " + appToDelete.getDate()+" has been deleted";
					}else {
						message = "oops!! there was a problem deleting that appoinment";
					}
				}
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/Appointments.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
