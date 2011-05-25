package tests;

import java.util.Calendar;
import java.util.Date;

public class Chrono {
	
	Date d_start,d_finished;
	
	public void start() {
		d_start=new Date();
		System.out.println("[chrono] "+dateLog(d_start)+" : START");
	}
	public void stop() {
		d_finished=new Date();
		System.out.println("[chrono] "+dateLog(d_finished)+" : END");
		System.out.println("[chrono] TEMPS EXECUTION : "+tempsExecution());
	}
	public String dateLog(Date d) {
		String res="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		res+=(cal.get(Calendar.HOUR_OF_DAY)<10)?"0"+cal.get(Calendar.HOUR_OF_DAY):cal.get(Calendar.HOUR_OF_DAY);
		res+="h ";
		res+=(cal.get(Calendar.MINUTE)<10)?"0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE);
		res+="m ";
		res+=(cal.get(Calendar.SECOND)<10)?"0"+cal.get(Calendar.SECOND):cal.get(Calendar.SECOND);
		res+=",";
		int m=cal.get(Calendar.MILLISECOND);String r="";if (m<10) r+="0";if (m<100) r+="0";r+=m;
		res+=r;
		res+="s";
		return res;
	}
	public String tempsExecution() {
		Calendar question = Calendar.getInstance();
		Calendar answer = Calendar.getInstance();
		question.setTime(d_start);
		answer.setTime(d_finished);
		long l=answer.getTimeInMillis()-question.getTimeInMillis();
		int min,sec,mil;
		min=(int)l/60000;
		sec=((int)(l-min*60000)/1000);
		mil=((int)l-min*60000-sec*1000);
		return min+"m "+sec+","+mil+"s"+"            ("+l+")";
	}
}