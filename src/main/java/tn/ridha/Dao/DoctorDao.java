package tn.ridha.Dao;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import tn.ridha.Beans.DoctorBean;
import tn.ridha.singleton.SingletonConnection;

public class DoctorDao implements Dao<DoctorBean>{

	private Connection connection = SingletonConnection.getConnection();
	private Statement s;
	public DoctorDao () {
		try {
			s = connection.createStatement();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int insert(DoctorBean t) {
		Integer user_id = t.getUser_id();
		String specialty = t.getSpecialty();
		String hospital = t.getHospital();
		String command = "insert into doctors(specialty, hospital, user_id) values('"+specialty+"','"+hospital+"','"+user_id+"');";
		DoctorBean doctorBean = this.get("user_id", user_id.toString());
		try {
			if (doctorBean != null) {
				return this.update(t);
			}else {
				int r = s.executeUpdate(command);
				return r;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int update(DoctorBean t) {
		String command = "UPDATE doctors SET "
				+ "specialty='"+t.getSpecialty()
				+ "', hospital='"+t.getHospital()
				+"' WHERE user_id='"+t.getUser_id()+"';";
		System.out.println(command);
		try {
			int r = s.executeUpdate(command);
			return r;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public DoctorBean get(String column, String key) {
			String query = "select * from doctors where "+column+"='"+key+"';";
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
	public HashMap<Integer, DoctorBean> getAll() {
		HashMap<Integer, DoctorBean> allDoctors = new HashMap<Integer, DoctorBean>();
		String query = "select * from doctors;"; 
		try {
			ResultSet r = s.executeQuery(query);
			while (r.next()) {
				DoctorBean doctor = extract(r);
				allDoctors.put(doctor.get_id(), doctor);
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return allDoctors;
	}
	@Override
	public int delete(DoctorBean t) {
		int id = t.get_id();
		String command = "DELETE FROM doctors WHERE _id ='"+id+"';";
		try {
			int r = s.executeUpdate(command);
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public DoctorBean extract(ResultSet r){
		try {
			DoctorBean doctor = new DoctorBean();
			int id =  Integer.parseInt(r.getString("_id"));
			doctor.set_id(id);
			doctor.setSpecialty(r.getString("specialty"));
			doctor.setHospital(r.getString("hospital"));
			doctor.setUser_id(r.getInt("user_id"));
			return doctor;
		}catch(SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

}
