package uk.tlscott.spike;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class dateComboBox {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400, 200);
				frame.setLocation(500, 500);
				
				JPanel panel = new JPanel(new FlowLayout());
				frame.add(panel);
				
				JComboBox<String> dayBox; 
				JComboBox<String> monthBox;
				JComboBox<String> yearBox;

				SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-Y");
				
				Calendar cal = Calendar.getInstance();
				
				
				String today = dateFormat.format(cal.getTime());
				
				String[] todayArray = today.split("-");

				System.out.println(today);
				
				// get max days in month - only days need to change if m/y changes function?
				String[] days = new String[cal.getActualMaximum(Calendar.DAY_OF_MONTH)];
				
				for (int i = 0; i < days.length; i++) {
					days[i] = String.valueOf(i + 1);
				}
				
				// there are always 12 months
				String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
				
				// start with todays year and go  back 150 (oldest man)
				int thisYear = Integer.parseInt(todayArray[2]);
				int YEARS_BEFORE = 150;
				
				String[] years = new String[YEARS_BEFORE];
				
				for (int i = 0; i < YEARS_BEFORE; i++) {
					years[i] = String.valueOf(thisYear - i);
				}
				
				// set combo boxes
				
				dayBox = new JComboBox<String>(days);
				monthBox = new JComboBox<String>(months);
				yearBox = new JComboBox<String>(years);
				
				dayBox.setSelectedItem(todayArray[0]);
				monthBox.setSelectedItem(todayArray[1]);
				yearBox.setSelectedItem(todayArray[2]);
				
				monthBox.addActionListener(new DateListener(dayBox, monthBox, yearBox));
				yearBox.addActionListener(new DateListener(dayBox, monthBox, yearBox));

				panel.add(dayBox);
				panel.add(monthBox);
				panel.add(yearBox);
				
				frame.setVisible(true);
			}
		});
	}
	
	private static String[] populateDays(Calendar date) {
		String[] days = new String[date.getActualMaximum(Calendar.DAY_OF_MONTH)];
		
		for (int i = 0; i < days.length; i++) {
			days[i] = String.valueOf(i + 1);
		}
		
		return days;
	}

	
	public String buildDate(String day, String month, String year) {
		return String.format("%s-%s-%s", day, month, year);
	}
	
	public static class DateListener implements ActionListener {
		private JComboBox<String> yearBox;
		private JComboBox<String> monthBox;
		private JComboBox<String> dayBox;

		public DateListener(JComboBox<String> dayBox, JComboBox<String> monthBox, JComboBox<String> yearBox) {
			this.dayBox   = dayBox;
			this.monthBox = monthBox;
			this.yearBox   = yearBox;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar date = Calendar.getInstance();
			int selectedMonth = Integer.parseInt((String)monthBox.getSelectedItem()) - 1;
			int selectedYear = Integer.parseInt((String)yearBox.getSelectedItem());
			date.set(selectedYear, selectedMonth, 1);

			String[] days = populateDays(date);
			dayBox.removeAllItems();
			
			for (int i = 0; i < days.length; i++) {
				dayBox.addItem(days[i]);
			}
		};

	}
}
