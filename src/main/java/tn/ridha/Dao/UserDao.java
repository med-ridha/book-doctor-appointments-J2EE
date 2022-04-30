package tn.ridha.Dao;

import java.sql.Statement;
import java.util.Iterator;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tn.ridha.Beans.UserBean;
import tn.ridha.singleton.SingletonConnection;

public class UserDao implements Dao<UserBean>{
	private Connection connection = SingletonConnection.getConnection();
	private Statement s;
	public UserDao () {
		try {
			s = connection.createStatement();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int insert(UserBean t) {
		String name = t.getName();
		String email = t.getEmail();
		String password = t.getPassword();
		String surname = t.getSurname();
		String level = t.getLevel();
		Date birthdate = t.getBirthdate();
		String command = "insert into users(name, surname, email, password, birthdate, level) values('"+name+"','"+surname+"','"+email+"', '"+password+"','"+birthdate+"', '"+level+"');";
		try {
			int r = s.executeUpdate(command);
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int update(UserBean t) {
		String command = "UPDATE users SET "
				+ "name='"+t.getName()
				+ "', surname='"+t.getSurname()
				+ "', password='"+t.getPassword()
				+ "', birthdate='"+t.getBirthdate()
				+ "', email='"+t.getEmail()
				+ "', level='"+t.getLevel()
				+"' WHERE _id='"+t.get_id()+"';";
		try {
			int r = s.executeUpdate(command);
			return r;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public UserBean extract(ResultSet r) throws SQLException {
		UserBean user = new UserBean();
		int id =  r.getInt("_id");
		user.set_id(id);
		user.setName(r.getString("name"));
		user.setSurname(r.getString("surname"));
		user.setEmail(r.getString("email"));
		user.setBirthdate(r.getDate("birthdate"));
		user.setPassword(r.getString("password"));
		user.setLevel(r.getString("level"));
		return user;
	}
	@Override
	public UserBean get(String column, String key) {
			String query = "select * from users where "+column+"='"+key+"';";
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
	public HashMap<Integer, UserBean> getAll() {
		HashMap<Integer, UserBean> allUsers = new HashMap<Integer, UserBean>();
		String query = "select * from users;"; 
		try {
			ResultSet r = s.executeQuery(query);
			while (r.next()) {
				UserBean user = extract(r);
				allUsers.put(user.get_id(), user);
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return allUsers;
	}
	@Override
	public int delete(UserBean t) {
		int id = t.get_id();
		String command = "DELETE FROM users WHERE _id ='"+id+"';";
		try {
			int r = s.executeUpdate(command);
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public void test() throws SQLException {
		String query = "SELECT * FROM ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, "users");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString(1));
		}
	}
}
