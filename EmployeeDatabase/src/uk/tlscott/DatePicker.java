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

	private final String[] months = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	
	private JComboBox<String> dayBox; 
	private JComboBox<String> monthBox;
	private JComboBox<String> yearBox;
	private Calendar cal = Calendar.getInstance();
	
	public DatePicker() {
		super();
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		// get max days in month
		String[] days = populateDays(cal);

		
		// start with todays year and go  back 150 (oldest man)
		int thisYear = cal.get(Calendar.YEAR);
		int YEARS_BEFORE = 150;

		String[] years = new String[YEARS_BEFORE];

		for (int i = 0; i < YEARS_BEFORE; i++) {
			years[i] = String.valueOf(thisYear - i);
		}
		
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
		
		// set combo boxes
		setDate(LocalDate.now());
		
		// add action listeners
		monthBox.addActionListener(new DateListener(dayBox, monthBox, yearBox));
		yearBox.addActionListener(new DateListener(dayBox, monthBox, yearBox));

		// add combo boxes to panel
		add(dayBox);
		add(monthBox);
		add(yearBox);
	}
	
	// Fills days array with correct amount of days for the month
	private static String[] populateDays(Calendar date) {
		String[] days = new String[date.getActualMaximum(Calendar.DAY_OF_MONTH)];
		
		for (int i = 0; i < days.length; i++) {
			days[i] = String.valueOf(i + 1);
		}
		
		return days;
	}
	
	
	// Sets the date combo boxes based on the string passed.
	public void setDate(LocalDate date) {
		if (date == null)
			date = LocalDate.now();
		
		String dateString = date.format(DateTimeFormatter.ofPattern("d-M-yyyy"));		
		
		String[] dates = dateString.split("-");
		
		yearBox.setSelectedItem(dates[2]);
		monthBox.setSelectedItem(dates[1]);
		dayBox.setSelectedItem(dates[0]);
	}
	
	// Updates the days in the month depending on the month and year, handles leap years
	private class DateListener implements ActionListener {
		private JComboBox<String> yearBox;
		private JComboBox<String> monthBox;
		private JComboBox<String> dayBox;

		public DateListener(JComboBox<String> dayBox, JComboBox<String> monthBox, JComboBox<String> yearBox) {
			this.dayBox   = dayBox;
			this.monthBox = monthBox;
			this.yearBox  = yearBox;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar date = Calendar.getInstance();
			int firstDayOfMonth = 1;
			int selectedMonth = Integer.parseInt((String)monthBox.getSelectedItem()) - 1; // Calendar months are zero indexed i.e. 0 is January
			int selectedYear  = Integer.parseInt((String)yearBox.getSelectedItem());
			date.set(selectedYear, selectedMonth, firstDayOfMonth);

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(populateDays(date));
			dayBox.setModel(model);
			
		};
	}

	public String getDate() {
		String day = (String)dayBox.getSelectedItem();
		String month = (String)monthBox.getSelectedItem();
		String year = (String)yearBox.getSelectedItem();
		
		int dayInt = Integer.parseInt(day);
		int monthInt = Integer.parseInt(month);
		return String.format("%s-%02d-%02d", year, monthInt, dayInt);
	}
}
