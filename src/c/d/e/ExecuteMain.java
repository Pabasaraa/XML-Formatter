package c.d.e;

import com.tuesdayrefactoring.service.EmployeeService;
import com.tuesdayrefactoring.util.XSLTransformUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuteMain {

	public static final Logger log = Logger.getLogger(ExecuteMain.class.getName());

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		EmployeeService employeeService = new EmployeeService();
		try {
			XSLTransformUtil.XMLTransform();
			employeeService.loadEmployees();
			employeeService.createEmployeeTable();
			employeeService.addEmployee();
			// employeeService.eMPLOYEEGETBYID("EMP10004");
			// employeeService.EMPLOYEEDELETE("EMP10001");
			employeeService.displayEmployee();
		} catch (NumberFormatException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

}
