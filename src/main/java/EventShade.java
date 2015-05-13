import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class EventShade {
	
	private String title;
	private Calendar startTime;
	private Calendar endTime;
	private double duration;
	private String color;
	private int startAngle;
	private int endAngle;
	private int monthDateYear;
	private int day;
	
	public EventShade(String start, String end, String color, String title) {
		//parse Time if string is a date with time
		// else if string is a date with no time
		if (start.length() >= 43) {   				// 43 is length of a proper event with start/end times
			
			this.startTime = parseTime(start);
			this.endTime = parseTime(end);
		} else {										//event with no times
			this.startTime = parseDate(start);
			this.endTime = parseDate(end);
		}
		this.color = color;
		this.title = title;
		
		findMonthDateYear();
		findStartAngle();
		findDuration();
		findEndAngle();
	}
	//parses string that contains date and time. Returns a Date
	public static Calendar parseTime(String datestring) {
	    Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    String rfc3339 = datestring.substring(13, datestring.length() -2);	// 13 cuts off {"datetime":
	    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");//spec for RFC3339		
	    try{
	    	date = s.parse(rfc3339);		  
	    
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    
	    }
	    cal.setTime(date);
	    return cal;
	  }
	// parses string that contains only the date. Returns a Date 
	
	public static Calendar parseDate(String datestring) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String justDateString = datestring.substring(datestring.indexOf(':')+ 2, datestring.lastIndexOf('"')- 1);
		try {
			date = sdf.parse(justDateString);
					
		} catch(ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		return cal; 
		
	}
	
	public void findMonthDateYear(){
		day = startTime.get(Calendar.DATE);
		monthDateYear = startTime.get(Calendar.MONTH) + startTime.get(Calendar.DATE) + startTime.get(Calendar.YEAR);
	}
	
	public void findStartAngle(){
		double hourDecimal = startTime.get(Calendar.HOUR) + ((double)startTime.get(Calendar.MINUTE)/ 60);
		double hoursRadians;
		if(hourDecimal >= 3){
			hoursRadians = ((2.5 * Math.PI) - (hourDecimal * (Math.PI/6)));
		} else {
			hoursRadians = ((-hourDecimal * (Math.PI/6)) + (Math.PI/2));
		}
		startAngle = (int) (hoursRadians * (180/Math.PI));
		
	}
	
	public void findEndAngle(){
		endAngle = (int) -(duration * 30);
		
	}
	
	public void findDuration(){
		
		duration = endTime.get(Calendar.HOUR_OF_DAY) - startTime.get(Calendar.HOUR_OF_DAY);
		
	}
	
	public int getStartAngle(){
		return startAngle;
		
	}
	
	public int getEndAngle(){
		return endAngle;
	}
	
	public String getTitle() {
		return title;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public String getColor() {
		return color;
	}
	
	public int getMonthDateYear(){
		return monthDateYear;
	}
	
	public int getDay(){
		return day;
	}
	
	public String toString(){
		return "Event: " + title + "\n" + 
				"Start time: " + startTime.get(Calendar.HOUR) + ":" + 
				startTime.get(Calendar.MINUTE) + "\n" +
				"End Time: " + endTime.get(Calendar.HOUR) + ":" + endTime.get(Calendar.MINUTE) + "\n" +
				"Start Angle: " + startAngle + "  End Angle: " + endAngle + "  DurationAngle: " + duration ;
	}
	
	
}