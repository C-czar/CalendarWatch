

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;



public class ClockGUI extends JFrame {
	BufferedImage image = null;
	Timer timer;
	
	Boolean imageDrawn = true;
	Boolean isRunning;
	Calendar calendarTime;
	
	// Alpha values for transparency
	float alpha = .3f;
	int type = AlphaComposite.SRC_OVER;
	AlphaComposite composite = AlphaComposite.getInstance(type, alpha);
	
	List <EventShade> eventShades = new ArrayList<>();
	
	double piOverThirty = Math.PI / 30;
	double piOverTwo =  Math.PI / 2;
	double piOverSix = Math.PI / 6;
	
	Color[] colorArray;
	
	int count = 0;
	
	int currentSeconds;
	int currentHour;
	int currentMinute;
	
	int centerX = 175;
	int centerY = 183;
	
	int secondX;
	int secondY;
	
	int minuteX;
	int minuteY;
	
	int hourX;
	int hourY;
	
	public ClockGUI(List<EventShade> eventShades) {
		
		colorArray = new Color[10];
		
		colorArray[0] = new Color(0,0,1, alpha); //blue
		colorArray[1] = new Color(1,0,0, alpha); //red
		colorArray[2] = new Color(0,1,0, alpha); //green
		colorArray[3] = new Color(1,1,.2f, alpha); //yellow
		colorArray[4] = new Color(.8f,0,.8f, alpha); //purple
		colorArray[5] = new Color(1,.5f,0, alpha); //orange
		colorArray[6] = new Color(.5f,.5f,.5f, alpha); // grey
		colorArray[7] = new Color(.4f,1,1, alpha); // skyBlue
		colorArray[8] = new Color(.4f,0,0, alpha); //crimson
		colorArray[9] = new Color(0,.4f,0, alpha); //darkGreen
		
		this.eventShades = eventShades;
		for (EventShade event: eventShades){
			System.out.println(event.toString());
		}
		try{
			image = ImageIO.read(new File("src/main/resources/blueClock.png"));
		} catch (IOException e ) {
			System.out.println(e);
		}
		tick();
		
		setIconImage( image);
		setSize( 350, 350);
		setBackground(Color.black);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		setVisible( true);
		
		timer = new Timer( 1000, new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent e) {
				tick();
				repaint();
			}
		});
		timer.start();
	}
	
	public void tick() {
		
		calendarTime = Calendar.getInstance();
		
		currentSeconds = calendarTime.get( Calendar.SECOND);
		currentMinute = calendarTime.get( Calendar.MINUTE);
		currentHour = calendarTime.get( Calendar.HOUR);
		
		updateSecondHand( currentSeconds);
		updateMinuteHand( currentMinute);
		updateHourHand( currentHour, currentMinute);
		

	}
	
	private void drawSecondHand(Graphics2D second2D) {
		
		second2D.setColor(Color.cyan);
		second2D.setStroke(new BasicStroke(1));
		second2D.drawLine(centerX, centerY, secondX, secondY);
	}
	
	private void drawMinuteHand(Graphics2D minute2D){
		
		minute2D.setColor(Color.cyan);
		minute2D.setStroke(new BasicStroke(2));
		minute2D.drawLine(centerX, centerY, minuteX, minuteY );
	}
	
	private void drawHourHand(Graphics2D hour2D){
		
		hour2D.setColor(Color.cyan);
		hour2D.setStroke(new BasicStroke(3));
		hour2D.drawLine(centerX, centerY, hourX, hourY);
	}
	
	private void drawArc(Graphics2D arc2D, Color color, int startAngle, int endAngle){

		arc2D.setColor(color);
		arc2D.fillArc(63, 71, 224, 224, startAngle , endAngle);
	}
	
	@Override
	public void paint( Graphics g) {
		
		Graphics2D g2 = ( Graphics2D) g;
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);	
		g2.drawImage(image , 25, 40, null);
		
		for (EventShade event: eventShades){
			if (count < colorArray.length) {
				drawArc(g2, colorArray[count] , event.getStartAngle(),event.getEndAngle() );
				count += 1;
			} else{count = 0;}
		}
		count = 0;
		drawSecondHand(g2);
		drawMinuteHand(g2);
		drawHourHand(g2);
		
	}
	
	@Override
	public void update(Graphics g) {
		
		paint(g);
	}
	
	public void updateSecondHand(int seconds){
		secondX = (int) (Math.cos((seconds  * piOverThirty) - (piOverTwo)) * 112 + centerX);
		secondY = (int) (Math.sin((seconds * piOverThirty) - (piOverTwo)) * 112 + centerY);
	}
	
	public void updateMinuteHand(int minutes){
		minuteX = (int) (Math.cos((minutes  * piOverThirty) - (piOverTwo)) * 112 + centerX); 
		minuteY = (int) (Math.sin((minutes * piOverThirty) - (piOverTwo)) * 112 + centerY);		
	}
	
	public void updateHourHand(int hr, int min){
		
		double hours = (double) hr;
		double minutes = (double) min;
		double hour = hours + ((minutes * 2)/100);
		hourX = (int) (Math.cos((hour  * piOverSix) - (piOverTwo)) * 56 + centerX); 
		hourY = (int) (Math.sin((hour * piOverSix) - (piOverTwo)) * 56 + centerY); 
	}
	
	
}
