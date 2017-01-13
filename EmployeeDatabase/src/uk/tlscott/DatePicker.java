package uk.tlscott;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

// panel off combo boxes representing day/month/year
// defaults to todays date
public class DatePicker extends JPanel{

	private static final long serialVersionUID = 1682449202814187644L;
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static final int NUMBER_OF_YEARS = 150; // 150 is oldest man at time of writing
	
	private final String[] months = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}; 
	private String[] days;
	private String[] years;

	private JComboBox<String> dayBox;
	private JComboBox<String> monthBox;
	private JComboBox<String> yearBox;
	
	private DateTimeFormatter dateReturnFormat;

	public DatePicker() {
		super();
		
		dateReturnFormat = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		populateDays(Calendar.getInstance());
		populateYears();
		
		initialiseComboBoxes();
		
		// set combo boxes
		setDate(LocalDate.now());
		
		// add action listeners
		monthBox.addActionListener(new DateListener());
		yearBox.addActionListener(new DateListener());

		// add combo boxes to panel
		add(dayBox);
		add(monthBox);
		add(yearBox);
	}

	// Updates the days in the month depending on the month and year, handles leap years
	private class DateListener implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar chosenDate = Calendar.getInstance();
			int firstDayOfMonth = 1;
			int selectedMonth = Integer.parseInt((String)monthBox.getSelectedItem()) - 1; // Calendar months are zero indexed i.e. 0 is January
			int selectedYear  = Integer.parseInt((String)yearBox.getSelectedItem());
			chosenDate.set(selectedYear, selectedMonth, firstDayOfMonth);
	
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(fillAndReturnDayArray(chosenDate));
			dayBox.setModel(model);
			
		}
	
		private String[] fillAndReturnDayArray(Calendar date) {
			populateDays(date);
			return days;
		};
	}

	// Fills days array with correct amount of days for the month
	private void populateDays(Calendar date) {
		days = new String[date.getActualMaximum(Calendar.DAY_OF_MONTH)];
		
		for (int i = 0; i < days.length; i++) {
			days[i] = String.valueOf(i + 1);
		}
	}

	private void populateYears() {
		// start with todays year and go back NUMBER_OF_YEARS
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		years = new String[NUMBER_OF_YEARS];
	
		for (int i = 0; i < NUMBER_OF_YEARS; i++) {
			years[i] = String.valueOf(thisYear - i);
		}
	}

	private void initialiseComboBoxes() {
		// Fill combo boxes
		dayBox = new JComboBox<String>(days);
		monthBox = new JComboBox<String>(months);
		yearBox = new JComboBox<String>(years);
	
		// set widths
		Dimension smallSize = new Dimension(55, 30);
		Dimension mediumSize = new Dimension(70, 30);
		dayBox.setPreferredSize(smallSize);
		monthBox.setPreferredSize(smallSize);
		yearBox.setPreferredSize(mediumSize);
		
		
		// centre text
		((JLabel)dayBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		((JLabel)monthBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		((JLabel)yearBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	}

	// Sets the date combo boxes based on the string passed.
	public void setDate(LocalDate date) {
		if (date == null)
			date = LocalDate.now();
		
		yearBox.setSelectedItem(String.valueOf(date.getYear()));
		monthBox.setSelectedItem(String.valueOf(date.getMonthValue()));
		dayBox.setSelectedItem(String.valueOf(date.getDayOfMonth()));
	}

	public String getDateAsString() {
		return getLocalDate().format(dateReturnFormat);
	}

	public LocalDate getLocalDate() {
		String day = (String)dayBox.getSelectedItem();
		String month = (String)monthBox.getSelectedItem();
		String year = (String)yearBox.getSelectedItem();
		
		int dayInt = Integer.parseInt(day);
		int monthInt = Integer.parseInt(month);
		int yearInt = Integer.parseInt(year);
		
		return LocalDate.of(yearInt, monthInt, dayInt);
	}
	
	public void setDateReturnFormat(DateTimeFormatter dateFormat) {
		dateReturnFormat = dateFormat;
	}

	public DateTimeFormatter getDateReturnFormat() {
		return dateReturnFormat;
	}
}
