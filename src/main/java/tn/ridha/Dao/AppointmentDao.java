package tn.ridha.Dao;

import java.util.HashMap;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import tn.ridha.Beans.AppointmentBean;
import tn.ridha.singleton.SingletonConnection;

public class AppointmentDao implements Dao<AppointmentBean>{

	private Connection connection = SingletonConnection.getConnection();
	private Statement s;
	public AppointmentDao () {
		try {
			s = connection.createStatement();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int insert(AppointmentBean t) {
		int user_id = t.getUser_id();
		int doctor_id = t.getDoctor_id();
		Date date = t.getDate();
		Date dateCreated = t.getDatecreated();
		String command = "insert into appointments(date, datecreated, user_id, doctor_id) values('"+date+"','"+dateCreated+"','"+user_id+"', '"+doctor_id+"');";
		try {
			int r = s.executeUpdate(command);
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int update(AppointmentBean t) {
		String command = "UPDATE appointments SET "
				+ "date='"+t.getDate()
				+"' WHERE _id='"+t.get_id()+"';";
		try {
			int r = s.executeUpdate(command);
			return r;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public AppointmentBean get(String column, String key) {
			String query = "select * from appointments where "+column+"='"+key+"';";
			try {
				ResultSet r = s.executeQuery(query);
				while (r.next()) {
					return extract(r);
				}
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		return null;
	}
	@Override
	public HashMap<Integer, AppointmentBean> getAll() {
		HashMap<Integer, AppointmentBean> allApp = new HashMap<Integer, AppointmentBean>();
		String query = "select * from appointments;"; 
		try {
			ResultSet r = s.executeQuery(query);
			while (r.next()) {
				AppointmentBean app = extract(r);
				allApp.put(app.get_id(), app);
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return allApp;
	}
	@Override
	public int delete(AppointmentBean t) {
		int id = t.get_id();
		String command = "DELETE FROM appointments WHERE _id ='"+id+"';";
		try {
			int r = s.executeUpdate(command);
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public AppointmentBean extract(ResultSet r){
		try {
			AppointmentBean appointment = new AppointmentBean();
			int id =  Integer.parseInt(r.getString("_id"));
			appointment.set_id(id);
			appointment.setDate(r.getDate("date"));
			appointment.setDatecreated(r.getDate("datecreated"));
			appointment.setUser_id(r.getInt("user_id"));
			appointment.setDoctor_id(r.getInt("doctor_id"));
			return appointment;
		}catch(SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
}
