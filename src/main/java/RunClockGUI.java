import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.api.services.calendar.model.Event;

public class RunClockGUI {
	
	private static List<Event> events;
	private static List<EventShade> eventShades = new ArrayList<>();
	private static List<EventShade> todayShades = new ArrayList<>();
	private static Calendar today;
	
	public static void main(String[] args) {
		today = Calendar.getInstance();
		
		CalendarQuickstart calendar = new CalendarQuickstart(); // connect to Google Calendar
		events = calendar.getEventList();						// Get list of events 
		for (Event event: events){								// Use event list to make list of event shades
			
			eventShades.add(new EventShade(event.getStart().toString(), event.getEnd().toString(),
					event.getColorId(), event.getSummary()));	
			
		}
		for(EventShade event: eventShades){
			if(event.getDay() == today.get(Calendar.DATE)) {
				todayShades.add(event);
			}
		}
		
		// Pass list of event shades into Clock gui
		
		new ClockGUI(todayShades);
		
	}
	
	
}


