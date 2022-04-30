package tn.ridha.Controllers;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Date;

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
 * Servlet implementation class Booking
 */
@WebServlet("/Booking")
public class Booking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Booking() {
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
					String filter = request.getParameter("filter");
					String id = request.getParameter("id");
					if (id != null) {
						AppointmentDao appDao = new AppointmentDao();
						AppointmentBean appBean = new AppointmentBean();
						appBean.setUser_id(userData.get_id());
						try {
							appBean.setDoctor_id(Integer.parseInt(id));
						}catch(NumberFormatException e) {
							PrintWriter out = response.getWriter();
							out.write("Something went wrong: 67 !!");
							throw new RuntimeException(e);
						}
						Date dateCreated = new Date((System.currentTimeMillis()));
						appBean.setDatecreated(dateCreated);
						Date date = new Date((long) (System.currentTimeMillis()+1.728e+9 /*adds 20 days to current date*/));
						appBean.setDate(date);
						if(appDao.insert(appBean)> 0) {
							response.sendRedirect("/Projet/Appointments");
						}else {
							PrintWriter out = response.getWriter();
							out.write("Something went wrong: 67 !!");
						}
					}else {
						request.setAttribute("filter", filter);
						request.getRequestDispatcher("/Booking.jsp").forward(request, response);
					}
				}
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
