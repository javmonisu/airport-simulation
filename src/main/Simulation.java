package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.Constants;

/*
 * It is expected to launch more than one simulation at a time.
 */
public class Simulation {
	Random r = new Random();
	
	//Counters
	public int num_landed_airplanes = 0;
	public int num_takeoff_airplanes = 0;
	public int num_guidance_airplanes = 0;
	public int num_terminal_airplanes = 0;
	public int available_runways = Constants.number_of_runways;
	
	//Execution time
	public int time = 43200;
	public int actual_time = 0;
	
	//We don't use a Set because values can be repeated.
	public List<Integer> waiting_planes_landing ,waiting_planes_takeoff,
	waiting_planes_guidance,waiting_planes_terminal;
	
	public int minimun;
	
	public Simulation(){
		waiting_planes_landing = new ArrayList<>();
		waiting_planes_takeoff = new ArrayList<>();
		waiting_planes_guidance = new ArrayList<>();
		waiting_planes_terminal = new ArrayList<>();
		
		//First arrival.
		waiting_planes_landing.add(waiting_planes_landing.size(), actual_time);
		
		//While we have not run out of time...
		while((waiting_planes_landing.size() > 0 && waiting_planes_landing.get(0) < time) ||
				(waiting_planes_takeoff.size() > 0 && waiting_planes_takeoff.get(0) < time) ||
				(waiting_planes_guidance.size() > 0 && waiting_planes_guidance.get(0) < time) ||
				(waiting_planes_terminal.size() > 0 && waiting_planes_terminal.get(0) < time)){
			int minimun_List = checkMinimunList();
			switch (minimun_List) {
			case 1:
				landing_process();
				break;
			case 2:
				takeoff_process();
				break;
			case 3:
				guidance_process();
				break;
			case 4:
				terminal_process();
				break;
			}
		}
		System.out.println("Numero aterrizajes "+num_landed_airplanes+"\nNumero despegues "+num_takeoff_airplanes+
				"\nNumero aviones guiados con coche "+num_guidance_airplanes + "\nNumero de aviones en terminal " + num_terminal_airplanes);
	}
	public int checkMinimunList(){
		int minimunList = 1;
		
		//Get minimum available, if the list is empty we set a very high value;
		int minimun_landing = waiting_planes_landing.size() > 0 ? waiting_planes_landing.get(0) : time+1;
		int minimun_takeoff = waiting_planes_takeoff.size() > 0 ? waiting_planes_takeoff.get(0) : time+1;
		int minimun_guidance = waiting_planes_guidance.size() > 0 ? waiting_planes_guidance.get(0): time+1;
		int minimun_terminal = waiting_planes_terminal.size() > 0 ? waiting_planes_terminal.get(0): time+1;
		
		minimun = minimun_landing;
		if(minimun_takeoff < minimun){
			minimun = minimun_takeoff;
			minimunList = 2;
		}
		if(minimun_guidance < minimun){
			minimun = minimun_guidance;
			minimunList = 3;
		}
		if(minimun_terminal < minimun){
			minimun = minimun_terminal;
			minimunList = 4;
		}
		actual_time = minimun;
		return minimunList;
	}
	
	public void landing_process(){
		
		if(available_runways > 0){
			System.out.println("Avion aterrizado en el tiempo " + actual_time);
			num_landed_airplanes++;
			available_runways--;

			waiting_planes_landing.remove(0);
			
			//Next plane arrival
			waiting_planes_landing.add(waiting_planes_landing.size(), actual_time + r.nextInt(200));
			
			//Landed plane goes to guidance queue
			waiting_planes_guidance.add(actual_time += 50);
			available_runways++;
		}else{
			System.out.println("Avion intentando aterrizar" + actual_time);
			waiting_planes_landing.remove(0);
			waiting_planes_landing.add(waiting_planes_landing.size(), actual_time + r.nextInt(10));
		}
	}
	public void takeoff_process(){
		if(available_runways > 0){
			System.out.println("Avion despegado en el tiempo " + actual_time);
			num_takeoff_airplanes++;
			waiting_planes_takeoff.remove(0);
		}else{
			//Takeoff delayed
			waiting_planes_takeoff.set(0, waiting_planes_takeoff.get(0) + r.nextInt(50));
		}
	}
	public void guidance_process(){
		if(waiting_planes_terminal.size() < Constants.number_of_cars ){
			System.out.println("Avion guiado " + actual_time);

			num_guidance_airplanes++;
			waiting_planes_guidance.remove(0);
			
			//Plane guided goes to terminal
			waiting_planes_terminal.add(actual_time + r.nextInt(75));
		}
	}
	public void terminal_process(){
		
		if(waiting_planes_takeoff.size() < Constants.number_of_terminal){
			
			System.out.println("Avion yendo a Terminal " + actual_time);

			num_terminal_airplanes++;
			waiting_planes_terminal.remove(0);
			
			//Plane in terminal goes to takeoff
			waiting_planes_takeoff.add(actual_time + r.nextInt(30));
		}
	}
}