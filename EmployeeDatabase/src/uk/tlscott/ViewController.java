package uk.tlscott;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public  class ViewController {

	private static final int MAX_VIEWS = 10;
	public static final String RECORD_VIEW = "record_view";
	public static final String SEARCH_VIEW = "search_view";
	private JPanel parent;
	private CardLayout layout;
	private Map<String, JPanel> mapNames;
	
	public ViewController(JPanel parent, CardLayout layout) {
		this.parent = parent;
		this.layout = layout;
		mapNames = new HashMap<String, JPanel>(MAX_VIEWS);
	}
	
	public void addView(JPanel view, String name) {
		mapNames.put(name, view);
		parent.add(view, name);
	}
	
	public void showRecordOf(Employee emp) {
		showView(RECORD_VIEW);
		EmployeeUpdatePanel panel = (EmployeeUpdatePanel)mapNames.get(RECORD_VIEW);
		panel.setEmployee(emp);
	}
	
	public void showSearchView(){
		showView(SEARCH_VIEW);
	}
	
	private void showView(String view){
		layout.show(parent, view);
	}
	
}
