package com.tuesdayrefactoring.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Map;
import com.tuesdayrefactoring.model.Employee;
import com.tuesdayrefactoring.util.Constants;
import com.tuesdayrefactoring.util.PropertyUtil;
import com.tuesdayrefactoring.util.QueryUtil;
import com.tuesdayrefactoring.util.XSLTransformUtil;

public class EmployeeService extends PropertyUtil {

	public static final Logger log = Logger.getLogger(EmployeeService.class.getName());

	private final ArrayList<Employee> employeeList = new ArrayList<Employee>();

	private static Connection conn;

	private static Statement statement;

	private PreparedStatement preparedStatement;

	public EmployeeService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(properties.getProperty( Constants.URL ), properties.getProperty( Constants.USER_NAME ),
					properties.getProperty( Constants.PASSWORD ));
		} catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (LinkageError e) {
			log.log(Level.SEVERE, e.getMessage());
		}  
	}

	/**
	 * This will read XMLResponse.xml file and load those data into a arraylist
	 * 
	 */
	public void loadEmployees() {

		try {
			/*
			 * This will return an ArrayList that contain employee details as a map.
			 * Read the map element & store in Employee object
			 */
			for (Map<String, String> employeeMap : XSLTransformUtil.readXpathValues() ) {
				Employee emp = new Employee();
				emp.setEmployeeId(employeeMap.get( Constants.XPATH_EMPLOYEE_ID_KEY ));
				emp.setEmployeeName(employeeMap.get( Constants.XPATH_EMPLOYEE_NAME_KEY ));
				emp.setEmployeeAddress(employeeMap.get( Constants.XPATH_EMPLOYEE_ADDRESS_KEY ));
				emp.setEmployeeFaculty(employeeMap.get( Constants.XPATH_FACULTY_NAME_KEY ));
				emp.setEmployeeDepartment(employeeMap.get( Constants.XPATH_DEPARTMENT_KEY ));
				emp.setEmployeeDesignation(employeeMap.get( Constants.XPATH_DESIGNATION_KEY ));
				employeeList.add(emp);
				System.out.println(emp.toString() + "\n");
			}
		} catch (IndexOutOfBoundsException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}  catch (ClassCastException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		} finally {
			/*
			 * Close the connections at the end
			 */
			try {
				if (conn != null) {
					conn.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
	}

	public void createEmployeeTable() {
		try {
			statement = conn.createStatement();
			statement.executeUpdate(QueryUtil.Q("q2"));
			statement.executeUpdate(QueryUtil.Q("q1"));
		} catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	public void addEmployee() {
		try {
			preparedStatement = conn.prepareStatement(QueryUtil.Q("q3"));
			conn.setAutoCommit(false);
			for(Employee emp : employeeList){
				preparedStatement.setString( Constants.COLUMN_INDEX_ONE, emp.getEmployeeId() );
				preparedStatement.setString( Constants.COLUMN_INDEX_TWO, emp.getEmployeeName() );
				preparedStatement.setString( Constants.COLUMN_INDEX_THREE, emp.getEmployeeAddress() );
				preparedStatement.setString( Constants.COLUMN_INDEX_FOUR, emp.getEmployeeFaculty() );
				preparedStatement.setString( Constants.COLUMN_INDEX_FIVE, emp.getEmployeeDepartment() );
				preparedStatement.setString( Constants.COLUMN_INDEX_SIX, emp.getEmployeeDesignation() );
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	public void getEmployeeById(String empID) {

		Employee emp = new Employee();
		try {
			preparedStatement = conn.prepareStatement(QueryUtil.Q("q4"));
			preparedStatement.setString( Constants.PARAMETER_INDEX_ONE, empID );
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				emp.setEmployeeId(results.getString( Constants.COLUMN_INDEX_ONE ));
				emp.setEmployeeName(results.getString( Constants.COLUMN_INDEX_TWO ));
				emp.setEmployeeAddress(results.getString( Constants.COLUMN_INDEX_THREE ));
				emp.setEmployeeFaculty(results.getString( Constants.COLUMN_INDEX_FOUR ));
				emp.setEmployeeDepartment(results.getString( Constants.COLUMN_INDEX_FIVE ));
				emp.setEmployeeDesignation(results.getString( Constants.COLUMN_INDEX_SIX ));
			}
			ArrayList<Employee> empList = new ArrayList<Employee>();
			empList.add(emp);
			printEmployee(empList);
		} catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	public void deleteEmployee(String eid) {

		try {
			preparedStatement = conn.prepareStatement(QueryUtil.Q("q6"));
			preparedStatement.setString( Constants.PARAMETER_INDEX_ONE, eid );
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	//get the employee details & store them in a array list and send them to printEmployee() method to print
	public void displayEmployee() {

		ArrayList<Employee> empList = new ArrayList<Employee>();
		try {
			preparedStatement = conn.prepareStatement(QueryUtil.Q("q5"));
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(results.getString(1));
				emp.setEmployeeName(results.getString(2));
				emp.setEmployeeAddress(results.getString(3));
				emp.setEmployeeFaculty(results.getString(4));
				emp.setEmployeeDepartment(results.getString(5));
				emp.setEmployeeDesignation(results.getString(6));
				empList.add(emp);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		printEmployee(empList);
	}
	
	//print employee details on the console
	public void printEmployee(ArrayList<Employee> empList){
		
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out
				.println("================================================================================================================");
		for( Employee employee : empList ){
			System.out.println(employee.getEmployeeId() + "\t" + employee.getEmployeeName() + "\t\t"
					+ employee.getEmployeeAddress() + "\t" + employee.getEmployeeFaculty() + "\t" + employee.getEmployeeDepartment() + "\t"
					+ employee.getEmployeeDesignation() + "\n");
			System.out
			.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
