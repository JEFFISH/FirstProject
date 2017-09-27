package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Banji;
import entity.Subject;

public class SubToBanjiDao {
	
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private List<Subject> list;         

	/**
	 * 连接数据库
	 */
	public void linkDb()
	{
		//连接数据库的步骤
				try {
					//1.类反射,加载驱动类
					Class.forName("com.mysql.jdbc.Driver");
					//2.连接数据库
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/school?characterEncoding=UTF-8", "root", "123456");
					//3.创建statement
					state=conn.createStatement();
				
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
	public void closeDb()
	{
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List selectById(int bj_id)
	{
		linkDb();
		list=new ArrayList<Subject>();
		String sql="select b.id as id,b.name as bj_name,s.name as sub_name,s.id as sub_id from banji as b inner join m_bj_sub as m on b.id=m.bj_id inner join subject as s on m.sub_id=s.id where b.id='"+bj_id+"'";
		try {
			rs=state.executeQuery(sql);
			while(rs.next())
			{
				Subject subject=new Subject();
				subject.setId(rs.getInt("sub_id"));
				subject.setName(rs.getString("sub_name"));
				list.add(subject);
			}
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int add(Subject sub,Banji bj)
	{
		int flag = -1;
		String sql="insert into m_bj_sub(bj_id,sub_id) value('"+bj.getId()+"','"+sub.getId()+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//2.连接数据库
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/school?characterEncoding=UTF-8", "root", "123456");
			//3.创建statement
			Statement state=conn.createStatement();
			flag=state.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return flag;			
	}
	
	public int delete(Subject sub,Banji bj)
	{
		linkDb();
		int flag=-1;
		String sql="delete from m_bj_sub where bj_id='"+bj.getId()+"' and sub_id='"+sub.getId()+"'";
		try {
			System.out.println(sql);
			flag=state.executeUpdate(sql);
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
