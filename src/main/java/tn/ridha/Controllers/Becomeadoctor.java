package tn.ridha.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tn.ridha.Beans.DoctorBean;
import tn.ridha.Beans.UserBean;
import tn.ridha.Dao.DoctorDao;
import tn.ridha.Dao.UserDao;

/**
 * Servlet implementation class Becomeadoctor
 */
@WebServlet("/Becomeadoctor")
public class Becomeadoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Becomeadoctor() {
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
					request.getRequestDispatcher("/Becomeadoctor.jsp").forward(request, response);
				}
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DoctorBean doctorBean = new DoctorBean();
		UserBean userBean = (UserBean) request.getSession().getAttribute("userData");
		int user_id = userBean.get_id();
		System.out.println(user_id);
		userBean.setLevel("doctor");
		String specialty = request.getParameter("specialty");
		String hospital = request.getParameter("hospital");
		doctorBean.setUser_id(user_id);
		doctorBean.setSpecialty(specialty);
		doctorBean.setHospital(hospital);
		DoctorDao doctorDao = new DoctorDao();
		UserDao userDao = new UserDao();
		if (doctorDao.insert(doctorBean) > 0) {
			if (userDao.update(userBean) > 0) {
				response.sendRedirect("/Projet/");
			}else {
				response.getWriter().println("something went wrong 67");
			}
		}else {
			response.getWriter().println("something went wrong 70");
		}
	}
}
