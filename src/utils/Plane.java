package utils;

import java.util.List;

public class Plane implements Comparable<Plane>{
	private Double arriving_time, landing_time, guidance_time, terminal_time, takeoff_time, left_time;
	
	public Plane(Double arriving_time){
		this.arriving_time = arriving_time;
	}
	
	public Double getLanding_time() {
		return landing_time;
	}
	public void setLanding_time(Double landing_time) {
		this.landing_time = landing_time;
	}
	public Double getGuidance_time() {
		return guidance_time;
	}
	public void setGuidance_time(Double guidance_time) {
		this.guidance_time = guidance_time;
	}
	public Double getTerminal_time() {
		return terminal_time;
	}
	public void setTerminal_time(Double terminal_time) {
		this.terminal_time = terminal_time;
	}
	public Double getTakeoff_time() {
		return takeoff_time;
	}
	public void setTakeoff_time(Double takeoff_time) {
		this.takeoff_time = takeoff_time;
	}
	public Double getLeft_time() {
		return left_time;
	}
	public void setLeft_time(Double left_time) {
		this.left_time = left_time;
	}
	
	@Override
	public int compareTo(Plane arg0) {
		
		if(this.takeoff_time != null){
			return this.takeoff_time.compareTo(arg0.takeoff_time);
		}
		if(this.terminal_time != null){
			return this.terminal_time.compareTo(arg0.terminal_time);			
		}
		if(this.guidance_time != null){
			return this.guidance_time.compareTo(arg0.guidance_time);			
		}
		if(this.landing_time != null){
			return this.landing_time.compareTo(arg0.landing_time);			
		}
		return 0;
	}


}
