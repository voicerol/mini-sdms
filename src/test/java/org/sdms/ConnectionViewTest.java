package org.sdms;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sdms.View.ConnectionView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class that tests ConnectionView class' GUI
 *
 * @author voicerol
 *
 */
public class ConnectionViewTest {

	private FrameFixture connectionFrame;
	private ConnectionView connectionView;

	@Before
	public void setUp() {
		// Инициализация компонента ConnectionView и его фиксации с использованием FrameFixture
		connectionView = new ConnectionView();
		connectionFrame = new FrameFixture(ConnectionView.connectionFrame);
	}

	@After
	public void tearDown() {
		// Очистка после каждого теста
		connectionFrame.cleanUp();
	}

	// Тест для проверки пустых полей
	@Test
	public void emptyFieldsTest() {
		// Нажатие на кнопку "Connect" при пустых полях
		connectionFrame.button("connectButton").click();
		// Проверка, что показывается сообщение об ошибке
		connectionFrame.optionPane().requireErrorMessage().requireMessage("Please fill in all the empty fields!");
	}

	// Тест для проверки неправильного URL базы данных
	@Test
	public void wrongDatabaseUrl() {
		// Ввод некорректных данных
		connectionFrame.textBox("loginField").enterText("root");
		connectionFrame.textBox("passwordField").enterText("simplepassword123");
		connectionFrame.textBox("databaseUrlField").enterText("dawgfaea"); // Некорректный URL
		connectionFrame.button("connectButton").click();
		// Проверка, что показывается сообщение об ошибке с указанием проблемы
		connectionFrame.optionPane().requireErrorMessage().requireMessage(
				"Connection with the database hasn't been established!\nPlease check your credentials!");
	}

	// Тест для проверки правильных данных и успешного подключения
	@Test
	public void correctCredentials() {
		// Ввод правильных данных
		connectionFrame.textBox("loginField").enterText("root");
		connectionFrame.textBox("passwordField").enterText("simplepassword123");
		connectionFrame.textBox("databaseUrlField").enterText("jdbc:mysql://localhost:3306/studentsdb"); // Правильный URL
		connectionFrame.button("connectButton").click();
		// Проверка, что показывается сообщение об успешном подключении
		connectionFrame.optionPane().requireMessage("Connection with the database has been successfully established!");
	}

	// Метод для имитации подключения к базе данных (если необходимо использовать в тестах)
	private Connection createConnection() throws SQLException {
		// Настройка строки подключения для MySQL
		String url = "jdbc:mysql://localhost:3306/studentsdb";
		String user = "root";
		String password = "simplepassword123"; // Обновите на свой пароль
		return DriverManager.getConnection(url, user, password);
	}
}
