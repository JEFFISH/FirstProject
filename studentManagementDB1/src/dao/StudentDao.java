package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Banji;
import entity.Student;

public class StudentDao {
	
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private List<Student> list;         

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
	
	public Student selectById(int id)
	{
		Student stu=new Student();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/school?characterEncoding=UTF-8", "root", "123456");
			String sql="select s.*,b.name as bj_name from student as s left join banji as b on s.bj_id =b.id where s.id = ?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Banji banji=new Banji();
				banji.setName(rs.getString("bj_name"));
				stu.setName(rs.getString("name"));
				stu.setAge(rs.getInt("age"));
				stu.setSex(rs.getString("sex"));
				stu.setId(id);
				stu.setBanji(banji);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  stu;
	}
	
	
	
	/**
	 * 查询所有的学生
	 * @return
	 */
	public List selectAll()
	{
		list=new ArrayList<Student>();
		linkDb();
		try {
			 rs=state.executeQuery("select s.*,b.name as bj_name from student as s left join banji as b on s.bj_id =b.id");
			 while(rs.next())
			 {
				 Student stu=new Student();
				 Banji banji=new Banji();
				 String name=rs.getString("name");
				 int id=Integer.parseInt(rs.getString("id"));
				 String sex=rs.getString("sex");
				 int age=Integer.parseInt(rs.getString("age"));
				 banji.setName(rs.getString("bj_name"));
				 stu.setAge(age);
				 stu.setId(id);
				 stu.setName(name);
				 stu.setSex(sex);
				 stu.setBanji(banji);
				 list.add(stu);
			 }
			 closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 添加学生
	 */
	public int add(Student stu)
	{
		int result=0;
		linkDb();
		try {
			result=state.executeUpdate("insert into student (name,sex,age,bj_id) value ('"+stu.getName()+"','"+stu.getSex()+"','"+stu.getAge()+"','"+stu.getBanji().getId()+"')");
			System.out.println(result);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 修改学生
	 */
	public int update(Student stu,Student stuNew)
	{
		int result=0;
		linkDb();
		try {
			System.out.println(stuNew.getBanji().getId());
			result=state.executeUpdate("update student set name='"+stuNew.getName()+"',sex='"+stuNew.getSex()+"',age='"+stuNew.getAge()+"',bj_id='"+stuNew.getBanji().getId()+"' where id="+stu.getId());
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除学生
	 */
	public int delete(Student stu)
	{
		int flagNum=0;
		linkDb();
		try {
			//flagNum=state.executeUpdate("DELETE FROM student WHERE NAME='"+stu.getName()+"' and sex='"+stu.getSex()+"' and age='"+stu.getAge()+"'");
			flagNum=state.executeUpdate("delete from student where id='"+stu.getId()+"'");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flagNum;
	}

	/**
	 * 根据条件查找
	 * @return
	 */
	public List serchByCondition(String name,String sex,int age,String banji_name)
	{
		list=new ArrayList<Student>();
		linkDb();
		String sql="select s.*,b.name as bj_name from student as s left join banji as b on s.bj_id =b.id where 1=1";
		if(!"".equals(name))
		{
			sql+=" and "+"s.name like '%"+name+"%'";
		}
		if(!"".equals(sex))
		{
			sql+=" and "+"s.sex='"+sex+"'";
		}
		if(age!=-1)
		{
			sql+=" and "+"s.age='"+age+"'";
		}
		if(!"".equals(banji_name)&&!"-1".equals(banji_name))
		{
			sql+=" and "+"b.name like '%"+banji_name+"%'";
		}
		if("-1".equals(banji_name))
		{
			sql+=" and "+"s.bj_id=0";
		}
		try {
			rs=state.executeQuery(sql);
			while(rs.next())
			{
				 Banji banji=new Banji();
				 Student stu=new Student();
				 String name1=rs.getString("name");
				 int id=Integer.parseInt(rs.getString("id"));
				 String sex1=rs.getString("sex");
				 int age1=Integer.parseInt(rs.getString("age"));
				 banji.setName(rs.getString("bj_name"));
				 stu.setAge(age1);
				 stu.setBanji(banji);
				 stu.setId(id);
				 stu.setName(name1);
				 stu.setSex(sex1);
				 list.add(stu);
			}
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
		
}
