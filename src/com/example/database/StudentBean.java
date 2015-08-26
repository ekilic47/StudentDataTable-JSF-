package com.example.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class StudentBean implements Serializable {
	private static final long serialVersionUID = 6081417964063918994L;
	private Student stu = new Student();

	public Student getStu() {
		return stu;
	}

	private boolean disable;

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public void setStu(Student stu) {
		this.stu = stu;
	}

	private List<Student> students = new ArrayList<Student>();

	@PostConstruct
	public void connect() {
		Connection connect = null;

		String url = "jdbc:mysql://localhost:3306/studentdb";

		String username = "root";
		String password = "";

		try {

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connect = DriverManager.getConnection(url, username, password);
			// System.out.println("Connection established"+connect);

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}
		PreparedStatement pstmt = null;
		try {
			pstmt = connect.prepareStatement("select stu_id, stu_name, stu_surname from student");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (rs.next()) {

				Student stu = new Student();
				stu.setStu_id(rs.getInt("stu_id"));
				stu.setStu_name(rs.getString("stu_name"));
				stu.setStu_surname(rs.getString("stu_surname"));
				students.add(stu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// close resources
		try {
			rs.close();
			pstmt.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String sqle() {
		String sql = "INSERT INTO student(stu_id,stu_name,stu_surname) VALUES(?,?,?)";
		return sql;

	}

	public void add() {

		int i = 0;
		PreparedStatement ps = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "");
			String sql = sqle();
			ps = con.prepareStatement(sql);
			ps.setLong(1, stu.getStu_id());
			ps.setString(2, stu.getStu_name());
			ps.setString(3, stu.getStu_surname());
			i = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		students.clear();
		connect();

	}

	public void edit(Student s) {
		this.stu = s;
	}

	public void delete(Student s) {
		PreparedStatement ps = null;
		Connection con = null;
		int i = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "");
			ps = con.prepareStatement("DELETE FROM student WHERE stu_id=" + s.getStu_id());

			i = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		students.remove(s);
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void update(Student s) {
		PreparedStatement ps = null;
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "");
			String sql = "UPDATE student set stu_name = '" + stu.getStu_name() + "', stu_surname = '"
					+ stu.getStu_surname() + "' WHERE stu_id=" + stu.getStu_id();
			ps = con.prepareStatement(sql);
			int i = ps.executeUpdate();
			if (i > 0) {
				System.out.println("Row updated successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		students.clear();
		connect();
	}

}
