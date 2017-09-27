package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.Banji;
import entity.Score;
import entity.Student;
import entity.Subject;

public class ScoreDao {

	SubjectDao subjectDao = new SubjectDao();
	StudentDao studentDao = new StudentDao();
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private List<Score> list;
	Set<Score> listChangeds=new HashSet<Score>();
	BanjiDao banjiDao = new BanjiDao();

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

	public List selectAll() {

		list = new ArrayList<Score>();
		linkDb();
		String sql = "SELECT bj.name as bj_name,sc.id as sc_id,st.id as stu_id,st.name as stu_name,sub.id as sub_id,sub.name as sub_name,st.bj_id as bj_id,sc.score as score,sc.grade as grade from student as st LEFT JOIN m_bj_sub as m on st.bj_id=m.bj_id LEFT JOIN subject as sub on m.sub_id=sub.id  LEFT JOIN score as sc on sub.id=sc.sub_id and st.id=sc.stu_id LEFT JOIN banji as bj on st.bj_id=bj.id";
		try {
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Student stu = new Student();
				Subject sub = new Subject();
				Banji banji=new Banji();
				Score score = new Score();
				String stu_name=rs.getString("stu_name");
				String grade=rs.getString("grade");
				String banji_name=rs.getString("bj_name");
				String sub_name=rs.getString("sub_name");
				int sc_id=rs.getInt("sc_id");
				score.setId(sc_id);
				score.setScore(rs.getInt("score"));
				if(grade==null)
				{
					grade="";
				}
				if(stu_name==null)
				{
					stu_name="";
				}
				if(banji_name==null)
				{
					banji_name="";
				}
				if(sub_name==null)
				{
					sub_name="";
				}
				banji.setName(banji_name);
				banji.setId(rs.getInt("bj_id"));
				score.setGrade(grade);
				stu.setBanji(banji);
				stu.setId(rs.getInt("stu_id"));;
				stu.setName(stu_name);
				sub.setId(rs.getInt("sub_id"));
				sub.setName(sub_name);
				score.setStudent(stu);
				score.setSubject(sub);
				list.add(score);
			}
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List selectBycondition(Score score) {
		list = new ArrayList<Score>();
		linkDb();
		int fenshu = score.getScore();
		System.out.println(fenshu);
		Student stu = score.getStudent();
		Subject sub = score.getSubject();
		String stuName = stu.getName();
		String subName = sub.getName();
		String banjiName = stu.getBanji().getName();
		String sql = "select * from (SELECT bj.name as bj_name,sc.id as sc_id,st.id as stu_id,st.name as stu_name,sub.id as sub_id,sub.name as sub_name,st.bj_id as bj_id,sc.score as score,sc.grade as grade from student as st LEFT JOIN m_bj_sub as m on st.bj_id=m.bj_id LEFT JOIN subject as sub on m.sub_id=sub.id  LEFT JOIN score as sc on sub.id=sc.sub_id and st.id=sc.stu_id LEFT JOIN banji as bj on st.bj_id=bj.id) as temp where 1=1";
		if (fenshu != -1) {
			sql += " and score='" + fenshu + "'";
		}
		if (!"".equals(stuName)) {
			List<Student> stulist = new ArrayList<Student>();
			stulist = studentDao.serchByCondition(stuName, "", -1, "");
			for (int i = 0; i < stulist.size(); i++) {
				if (stulist.size() == 1) {
					sql += " and stu_id='" + stulist.get(i).getId() + "'";
				} else {
					if (i == 0) {
						sql += " and (stu_id='" + stulist.get(i).getId() + "'";
					}else if(i==stulist.size()-1)
					{
						sql += " or stu_id='" + stulist.get(i).getId() + "')";
					}
					else{
						sql += " or stu_id='" + stulist.get(i).getId() + "'";
					}
				}
			}
		}
		if (!"".equals(banjiName)) {
			List<Banji> banjilist = new ArrayList<Banji>();
			banjilist = banjiDao.serchByCondition(banjiName);
			for (int i = 0; i < banjilist.size(); i++) {
				if (banjilist.size() == 1) 
				{
					sql += " and bj_id='" + banjilist.get(i).getId() + "'";
				}
				else
				{
					if (i == 0) {
						sql += " and (bj_id='" + banjilist.get(i).getId() + "'";
					}
					else if(i==banjilist.size()-1)
					{
						sql += " or bj_id='" + banjilist.get(i).getId() + "')";
					}
					else {
						sql += " or bj_id='" + banjilist.get(i).getId() + "'";
					}
				}
			}
		}
		if (!"".equals(subName)) {
			List<Subject> sublist = new ArrayList<Subject>();
			sublist = subjectDao.serchByCondition(subName);
			for (int i = 0; i < sublist.size(); i++) {
				if (sublist.size() == 1)
				{
					sql += " and sub_id='" + sublist.get(i).getId() + "'";
				}
				else
				{
					if (i == 0) {
						sql += " and (sub_id='" + sublist.get(i).getId() + "'";
					} 
					else if(i==sublist.size()-1)
					{
						sql += " or sub_id='" + sublist.get(i).getId() + "')";
					}
					else {
						sql += " or sub_id='" + sublist.get(i).getId() + "'";
					}
				}
			}
		}
		System.out.println(sql);
		try {
			rs = state.executeQuery(sql);
			while (rs.next()) {
				Student stu1 = new Student();
				Subject sub1 = new Subject();
				Score score1 = new Score();
				score1.setId(rs.getInt("sc_id"));
				score1.setScore(rs.getInt("score"));
				score1.setGrade(rs.getString("grade"));
				stu1 = studentDao.selectById(rs.getInt("stu_id"));
				sub1 = subjectDao.selectById(rs.getInt("sub_id"));
				score1.setStudent(stu1);
				score1.setSubject(sub1);
				list.add(score1);
			}
			closeDb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean save(Set listchanged)
	{
		int flag=-1;
		boolean relflag=true;
		listChangeds=listchanged;
		linkDb();
		String sql="";
		for(Score score:listChangeds)
		{
			if(score.getId()==0)
			{
				sql="insert into score(stu_id,sub_id,score,grade) value('"+score.getStudent().getId()+"','"+score.getSubject().getId()+"','"+score.getScore()+"','"+score.getGrade()+"')";
			}
			else
			{
				sql="update score set score='"+score.getScore()+"' where id='"+score.getId()+"'";
			}
			try {
				System.out.println(sql);
				flag=state.executeUpdate(sql);
				if(flag<=0)
				{
					relflag=false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		closeDb();
		return relflag;
	}
}
