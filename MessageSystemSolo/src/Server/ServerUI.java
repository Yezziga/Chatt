package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import javax.swing.*;

/**
 * This class opens a frame for searching for events in the server log.
 * 
 * @author Jessica
 *
 */
public class ServerUI {
	private JFrame frame = new JFrame("Server log");
	private JTextArea display = new JTextArea();

	private final Integer[] YEARS = { 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2018 };
	private final Integer[] MONTHS = { 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4 };
	private final Integer[] DAYS = { 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
			30, 31, 1, 2, 3, 4, 5, 6, 7, 8,9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	private final Integer[] HOURS = { 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13 };
	private final Integer[] MINUTES = { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
			11, 12, 13, 14, 15, 16, 17, 18, 19, 20, };
	private final Integer[] SECONDS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
			23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
			50, 51, 52, 53, 54, 55, 56, 57, 58, 59 };
	private JComboBox<Integer> cbFromYY, cbFromMM, cbFromDD, cbFromHH, cbFrommm, cbFromss, cbToYY, cbToMM, cbToDD,
			cbToHH, cbTomm, cbToss;
	private JButton btnSearch;

	public ServerUI() {
		initializeUI();
		Listener l = new Listener();
		btnSearch.addActionListener(l);
		start();
	}

	private void initializeUI() {
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setEditable(false);
		JScrollPane scroller = new JScrollPane(display);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setBounds(0, 0, 750, 395);
		JLabel lblTo = new JLabel("To:");
		JLabel lblFrom = new JLabel("From:");
		JLabel lblGuide = new JLabel("YY:MM:DD:hh:mm");
		btnSearch = new JButton("Search");

		cbFromYY = new JComboBox<Integer>(YEARS);
		cbFromMM = new JComboBox<Integer>(MONTHS);
		cbFromDD = new JComboBox<Integer>(DAYS);
		cbFromHH = new JComboBox<Integer>(HOURS);
		cbFrommm = new JComboBox<Integer>(MINUTES);
		cbFromss = new JComboBox<Integer>(SECONDS);
		cbToYY = new JComboBox<Integer>(YEARS);
		cbToMM = new JComboBox<Integer>(MONTHS);
		cbToDD = new JComboBox<Integer>(DAYS);
		cbToHH = new JComboBox<Integer>(HOURS);
		cbTomm = new JComboBox<Integer>(MINUTES);
		cbToss = new JComboBox<Integer>(SECONDS);
		lblFrom.setBounds(10, 390, 95, 20);
		lblGuide.setBounds(250, 390, 130, 20);
		cbFromYY.setBounds(-3, 409, 90, 20);
		cbFromMM.setBounds(70, 409, 67, 20);
		cbFromDD.setBounds(120, 409, 67, 20);
		cbFromHH.setBounds(170, 409, 67, 20);
		cbFrommm.setBounds(220, 409, 67, 20);
		cbFromss.setBounds(275, 409, 67, 20);

		btnSearch.setBounds(335, 409, 80, 20);
		lblTo.setBounds(500, 390, 40, 20);
		cbToYY.setBounds(410, 409, 90, 20);
		cbToMM.setBounds(490, 409, 67, 20);
		cbToDD.setBounds(540, 409, 67, 20);
		cbToHH.setBounds(590, 409, 67, 20);
		cbTomm.setBounds(640, 409, 67, 20);
		cbToss.setBounds(690, 409, 67, 20);

		frame.add(scroller);
		frame.add(lblFrom);
		frame.add(lblTo);
		frame.add(lblGuide);
		frame.add(btnSearch);
		frame.add(cbFromYY);
		frame.add(cbFromMM);
		frame.add(cbFromDD);
		frame.add(cbFromHH);
		frame.add(cbFrommm);
		frame.add(cbFromss);
		frame.add(cbToYY);
		frame.add(cbToMM);
		frame.add(cbToDD);
		frame.add(cbToHH);
		frame.add(cbTomm);
		frame.add(cbToss);
	}

	public void start() {

		frame.setBounds(0, 0, 750, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false); // Prevent user from change size
		frame.setLocationRelativeTo(null); // Start middle screen
	}

	/**
	 * Appends text to the text-area
	 * 
	 * @param the
	 *            text to append with
	 */
	public void append(String str) {
		display.append(str + "\n");
	}

	/**
	 * Fetches all logged event between two dates
	 * 
	 * @param to
	 *            the date to stop fetching
	 * @param from
	 *            from what date to start fetching
	 */
	public void viewHistory(LocalDateTime to, LocalDateTime from) {
		String str = TrafficLogger.getLog(to, from);
		display.setText(str);
	}

	/**
	 * Listens for when the "Search" button is pressed, and then fetches the logged
	 * event according to the input.
	 * 
	 * @author Jessica
	 *
	 */
	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int yearFrom = (int) cbFromYY.getSelectedItem();
			int monthFrom = (int) cbFromMM.getSelectedItem();
			int dayFrom = (int) cbFromDD.getSelectedItem();
			int hourFrom = (int) cbFromHH.getSelectedItem();
			int minuteFrom = (int) cbFrommm.getSelectedItem();
			int secondFrom = (int) cbFromss.getSelectedItem();
			LocalDateTime from = LocalDateTime.of(yearFrom, monthFrom, dayFrom, hourFrom, minuteFrom, secondFrom);
			int yearTo = (int) cbToYY.getSelectedItem();
			int monthTo = (int) cbToMM.getSelectedItem();
			int dayTo = (int) cbToDD.getSelectedItem();
			int hourTo = (int) cbToHH.getSelectedItem();
			int minuteTo = (int) cbTomm.getSelectedItem();
			int secondTo = (int) cbToss.getSelectedItem();
			LocalDateTime to = LocalDateTime.of(yearTo, monthTo, dayTo, hourTo, minuteTo, secondTo);
			viewHistory(to, from);
		}

	}

	public static void main(String[] args) {
		ServerUI ui = new ServerUI();

	}
}
