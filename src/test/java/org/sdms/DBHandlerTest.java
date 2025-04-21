package org.sdms;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.sdms.Model.DBHandler;
import static org.junit.Assert.*;

/**
 * The class that tests DBHandler class' methods
 * 
 * @author voicerol
 *
 */
public class DBHandlerTest {

	public DBHandlerTest() {
		// In case I set a password for the database
		// DBHandler.setPassword("password");
	}

	@Before
	public void init() {
		// Create tables in order to test methods that modifies them
		DBHandler.createTables();
	}

	@Test
	public void checkIfTableExistsTest() throws SQLException {

		boolean result = DBHandler.checkIfTableExists("Test");
        assertFalse(result);

		result = DBHandler.checkIfTableExists("students");
        assertTrue(result);

		result = DBHandler.checkIfTableExists("courses");
        assertTrue(result);

		result = DBHandler.checkIfTableExists("faculties");
        assertTrue(result);

		result = DBHandler.checkIfTableExists("Students");
        assertFalse(result);

		result = DBHandler.checkIfTableExists("coures");
        assertFalse(result);

		result = DBHandler.checkIfTableExists("5161521");
        assertFalse(result);

		result = DBHandler.checkIfTableExists("=-/*-+");
        assertFalse(result);

		result = DBHandler.checkIfTableExists("");
        assertFalse(result);

		result = DBHandler.checkIfTableExists("   ");
        assertFalse(result);

	}

	@Test
	public void addFacultyTest() throws SQLException {
		boolean result = DBHandler.addFaculty("Test");
        assertTrue(result);

		result = DBHandler.addFaculty("students");
        assertTrue(result);

		result = DBHandler.addFaculty("automatica");
        assertTrue(result);

		result = DBHandler.addFaculty("calculatoare");
        assertTrue(result);

		result = DBHandler.addFaculty("electronica");
        assertTrue(result);

		result = DBHandler.addFaculty("y y y y y");
        assertTrue(result);

		result = DBHandler.addFaculty("5161521");
        assertTrue(result);

		result = DBHandler.addFaculty("=-/*-+");
        assertTrue(result);

		result = DBHandler.addFaculty("");
        assertTrue(result);

		result = DBHandler.addFaculty("   ");
        assertTrue(result);
	}

	@Test
	public void addCourse() throws SQLException {
		boolean result = DBHandler.addCourse("matematica", "Test", 12);
        assertTrue(result);

		result = DBHandler.addCourse("bigint", "students", 999999999);
        assertTrue(result);

		result = DBHandler.addCourse("negativeint", "automatica", -9515141);
        assertTrue(result);

		result = DBHandler.addCourse("zero", "calculatoare", 0);
        assertTrue(result);

		result = DBHandler.addCourse("fizica", "electronica", 6);
        assertTrue(result);
	}
}
