package uk.tlscott.spike;

import java.time.LocalDate;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import uk.tlscott.DatePicker;

public class DatePickerSpike {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DatePicker dp = new DatePicker();
		frame.add(dp);
		frame.pack();
		frame.setVisible(true);
		
		
		dp.setDate(LocalDate.of(1988, 5, 10));
		
		JComboBox<String> box = (JComboBox<String>)dp.getComponent(0);
		
//		box.setSelectedItem("10");
		
		System.out.println(box.getSelectedItem());
	}

}
