package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Subject;

public class SubjectDao {

	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private List<Subject> list;

	/**
	 * 连接数据库
	 */
	public void linkDb() {
		// 连接数据库的步骤
		try {
			// 1.类反射,加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			// 2.连接数据库
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/school?characterEncoding=UTF-8",
							"root", "123456");
			// 3.创建statement
			state = conn.createStatement();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 关闭数据库
	 */
	public void closeDb() {
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Subject selectById(int id) {
		Subject sub = new Subject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/school?characterEncoding=UTF-8",
							"root", "123456");
			String sql = "select * from subject where id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sub.setName(rs.getString("name"));
				sub.setId(id);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sub;
	}

	public List selectAll() {
		list = new ArrayList<Subject>();
		linkDb();
		try {
			rs = state.executeQuery("select * from subject");
			while (rs.next()) {
				Subject sub = new Subject();
				String name = rs.getString("name");
				int id = rs.getInt("id");
				sub.setId(id);
				sub.setName(name);
				list.add(sub);
			}
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int add(Subject sub) {
		int result = 0;
		linkDb();
		try {
			result = state.executeUpdate("insert into subject (name) value ('"
					+ sub.getName() + "')");
			System.out.println(result);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int update(Subject sub, Subject subNew) {
		int result = 0;
		linkDb();
		try {
			result = state.executeUpdate("update subject set name='"+ subNew.getName() + "' where id='"+ sub.getId() + "'");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int delete(Subject sub) {
		int flagNum = 0;
		linkDb();
		try {
			flagNum = state.executeUpdate("DELETE FROM subject WHERE id="
					+ sub.getId());
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flagNum;
	}

	public List serchByCondition(String name) {
		String sql = "select * from subject where 1=1";
		list = new ArrayList<Subject>();
		linkDb();
		if (!"".equals(name)) {
			sql += " and " + "binary name like '%" + name + "%'";
		}
		try {
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Subject sub = new Subject();
				String name1 = rs.getString("name");
				int id = rs.getInt("id");
				sub.setId(id);
				sub.setName(name1);
				list.add(sub);
			}
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
