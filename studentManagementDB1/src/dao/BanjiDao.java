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

public class BanjiDao {
	
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private List<Banji> list;         

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
	
	public Banji selectById(int id)
	{
		Banji banji=new Banji();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/school?characterEncoding=UTF-8", "root", "123456");
			String sql="select * from banji where id = ?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				banji.setName(rs.getString("name"));
				banji.setStuNums(rs.getInt("stuNums"));
				banji.setId(id);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  banji;
	}
	
	public List selectAll()
	{
		list=new ArrayList<Banji>();
		linkDb();
		try {
			 rs=state.executeQuery("select * from banji");
			 while(rs.next())
			 {
				 Banji banji=new Banji();
				 String name=rs.getString("name");
				 int id=rs.getInt("id");
				 int stuNums=rs.getInt("stuNums");
				 banji.setId(id);
				 banji.setName(name);
				 banji.setStuNums(stuNums);
				 list.add(banji);
			 }
			 closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int add(Banji banji)
	{
		int result=0;
		linkDb();
		try {
			result=state.executeUpdate("insert into Banji (name) value ('"+banji.getName()+"')");
			System.out.println(result);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public int update(Banji banji,Banji banjiNew)
	{
		int result=0;
		linkDb();
		try {
			result=state.executeUpdate("update banji set name='"+banjiNew.getName()+"' where id='"+banji.getId()+"'");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public int delete(Banji banji)
	{
		int flagNum=0;
		linkDb();
		try {
			flagNum=state.executeUpdate("DELETE FROM banji WHERE id="+banji.getId());
			state.executeUpdate("update student set bj_id=0 where bj_id="+banji.getId());
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flagNum;
	}

	public List serchByCondition(String name,int stuNumsnew)
	{
		String sql="select * from banji where 1=1";
		list=new ArrayList<Banji>();
		linkDb();
		if(!"".equals(name))
		{
			sql+=" and "+"name like '%"+name+"%'";
		}
		if(stuNumsnew!=-1)
		{
			sql+=" and "+"stuNums='"+stuNumsnew+"'";
		}
		try {
			rs=state.executeQuery(sql);
			while(rs.next())
			{
				 Banji banji=new Banji();
				 String name1=rs.getString("name");
				 int id=rs.getInt("id");
				 int stuNums=rs.getInt("stuNums");
				 banji.setId(id);
				 banji.setName(name1);
				 banji.setStuNums(stuNums);
				 list.add(banji);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
		
	
	public List serchByCondition(String name)
	{
		List<Banji> list=new ArrayList<Banji>();
		String sql="select * from banji where 1=1";
		list=new ArrayList<Banji>();
		linkDb();
		if(!"".equals(name))
		{
			sql+=" and "+"name like '%"+name+"%'";
		}
		try {
			rs=state.executeQuery(sql);
			while(rs.next())
			{
			Banji banji=new Banji();
			String name1=rs.getString("name");
			int id=rs.getInt("id");
			int stuNums=rs.getInt("stuNums");
			banji.setId(id);
			banji.setName(name1);
			banji.setStuNums(stuNums);
			list.add(banji);
			}
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
