/**
 * Copyright 2015 Javier Montero, Diego Martin, Javier Rabanillo

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import utils.DistributionGenerator;
import utils.Plane;

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
	
	//Execution time
	public double time;
	public double current_time = 1;
	
	//Numero de hangares
	public int number_of_terminal;
	//Numero de coches.
	public int number_of_cars;
	//Numero de pistas de aterrizaje.
	public int number_of_runways;

	//We don't use a Set because values can be repeated.
	public List<Plane> waiting_planes_landing ,waiting_planes_takeoff,
	waiting_planes_guidance,waiting_planes_terminal, busy_runways, planes_left;
	
	public double minimun;
	
	public Simulation(double T, int nTerminal, int nCars, int nRunways){
		time = T;
		number_of_terminal = nTerminal;
		number_of_cars = nCars;
		number_of_runways = nRunways;
	}
	public Simulation(){
		time = 43200;
		number_of_terminal = 50;
		number_of_cars = 20;
		number_of_runways = 3;
	}
	
	public void simulation(){
		try{
			waiting_planes_landing = new ArrayList<>();
			waiting_planes_takeoff = new ArrayList<>();
			waiting_planes_guidance = new ArrayList<>();
			waiting_planes_terminal = new ArrayList<>();
			busy_runways = new ArrayList<>();
			
			//We will say that one runway will be busy with the landed plane.
			Plane first_plane = new Plane(0.0);
			first_plane.setLanding_time(current_time + DistributionGenerator.exponential(DistributionGenerator.getPoissonRate(current_time)));
			waiting_planes_landing.add(0, first_plane);
			busy_runways.add(waiting_planes_landing.get(0));
			
			//While we have not run out of time...
			while((waiting_planes_landing.size() > 0 && waiting_planes_landing.get(0).getLanding_time() < time) ||
					(waiting_planes_takeoff.size() > 0 && waiting_planes_takeoff.get(0).getTakeoff_time() < time) ||
					(waiting_planes_guidance.size() > 0 && waiting_planes_guidance.get(0).getGuidance_time() < time) ||
					(waiting_planes_terminal.size() > 0 && waiting_planes_terminal.get(0).getTerminal_time() < time)){
				//We free the runways if the plane has stop using it.+`poi0   
				Iterator<Plane> it = busy_runways.iterator();

				while(it.hasNext()){
					System.out.println("Pista libre");
					Plane p = it.next();
					if(p.getLanding_time() <= current_time){
						it.remove();
					}
				}
				
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
			System.out.println("-------------------------------------------\n"
					         + "Numero aterrizajes "+num_landed_airplanes  
					         + "\nNumero despegues "+num_takeoff_airplanes
					         + "\nNumero aviones guiados con coche " + num_guidance_airplanes 
					         + "\nNumero de aviones en terminal " + num_terminal_airplanes);
		}catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
			
	}		
	/**
	 * Method to get the minimun of all the lists.
	 * @return
	 */
	public int checkMinimunList(){
		int minimunList = 1;
		
		//Get minimum available, if the list is empty we set a very high value;
		double  minimun_landing = waiting_planes_landing.size() > 0 ? waiting_planes_landing.get(0).getLanding_time() : time + 1.0;
		double minimun_takeoff = waiting_planes_takeoff.size() > 0 ? waiting_planes_takeoff.get(0).getTakeoff_time() : time + 1.0;
		double minimun_guidance = waiting_planes_guidance.size() > 0 ? waiting_planes_guidance.get(0).getGuidance_time(): time + 1.0;
		double minimun_terminal = waiting_planes_terminal.size() > 0 ? waiting_planes_terminal.get(0).getTerminal_time(): time + 1.0;
		
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
		current_time = minimun;
		return minimunList;
	}
	
	public void landing_process(){
		
		if(busy_runways.size() < number_of_runways){
						
			System.out.println("1. Avion aterrizado en el tiempo " + current_time);
			num_landed_airplanes++;
			
			Plane landing_plane = waiting_planes_landing.get(0);
			waiting_planes_landing.remove(0);
			
			//Next plane arrival
			double distributionTime = DistributionGenerator.exponential(DistributionGenerator.getPoissonRate(current_time));
			Plane next_plane = new Plane(current_time);
			next_plane.setLanding_time(current_time + distributionTime);
			waiting_planes_landing.add(waiting_planes_landing.size(), next_plane);
			
//			What if planes arrive every minute?
//			waiting_planes_landing.add(waiting_planes_landing.size(), current_time + 1);
			
			//Landed plane goes to guidance queue
			double timeUntilFreeRunway = current_time + DistributionGenerator.gaussian(10, 3);
			landing_plane.setGuidance_time(timeUntilFreeRunway);
	
			//Guindance when the landed is complete.
			waiting_planes_guidance.add(landing_plane);
			busy_runways.add(landing_plane);
			
			Collections.sort(waiting_planes_landing);
			Collections.sort(waiting_planes_guidance);

		}else{
			System.out.println(">>>>>Avion intentando aterrizar" + current_time);
			Plane not_landed_plane = waiting_planes_landing.get(waiting_planes_landing.size()-1);
			not_landed_plane.setLanding_time(current_time + r.nextInt(10));
			Collections.sort(waiting_planes_landing);
			

		}
	}
	public void takeoff_process(){
		if(busy_runways.size() < number_of_runways){
			
			//Plane ready to takeoff! Check how much time it will take
			double timeUntilFreeRunway = (current_time + DistributionGenerator.uniform(10,15));
			Double douCurrentTime = new Double(current_time);
			for(double i = current_time ; i < timeUntilFreeRunway; i++){
				if(busy_runways.size() > 0 && busy_runways.contains(douCurrentTime)){
					System.out.println("La pista va a ser ocupada inminentemente");
					Plane taking_off_plane = waiting_planes_takeoff.get(0);
					taking_off_plane.setTakeoff_time(taking_off_plane.getTakeoff_time() + r.nextInt(100));
					continue;
				}
			}
			
			Plane leaving_plane = waiting_planes_takeoff.get(0);
			leaving_plane.setLeft_time(timeUntilFreeRunway);
			Collections.sort(busy_runways);
			
			System.out.println("4. Avion despegado en el tiempo " + current_time);
			num_takeoff_airplanes++;
			waiting_planes_takeoff.remove(0);
			planes_left.add(leaving_plane);
		}else{
			//Takeoff delayed
			System.out.println("No hay pistas disponibles, retrasamos la salida del avion");
			Plane not_takingoff_plane = waiting_planes_takeoff.get(0);
			not_takingoff_plane.setTakeoff_time(not_takingoff_plane.getTakeoff_time() + r.nextInt(100));
		}
	}
	public void guidance_process(){
		//Available cars?
		if(waiting_planes_terminal.size() < number_of_cars ){
			System.out.println("2. Avion guiado " + current_time);
			num_guidance_airplanes++;
			
			Plane guided_plane = waiting_planes_guidance.get(0);	
			waiting_planes_guidance.remove(0);
			
			//Plane guided goes to terminal
			//TODO Delete +20
			double time_spent = DistributionGenerator.exponential(45) + 20;
			double terminal_time = current_time + time_spent + r.nextInt(300);
			guided_plane.setTerminal_time(terminal_time);
			waiting_planes_terminal.add(guided_plane);
			Collections.sort(waiting_planes_terminal);

		}else{
			System.out.println(">>>>>Avion esperando a que haya un coche libre que le guie");
			Plane not_guided_plane = waiting_planes_landing.get(0);
			not_guided_plane.setLanding_time(not_guided_plane.getLanding_time() + 100);
		}
	}
	public void terminal_process(){
		//Available terminals?
		if(waiting_planes_takeoff.size() < number_of_terminal){
			System.out.println("3. Avion yendo a Terminal " + current_time);
			num_terminal_airplanes++;
			Plane terminal_plane = waiting_planes_terminal.get(0);
			waiting_planes_terminal.remove(0);
			
			//Plane in terminal goes to takeoff
			terminal_plane.setTerminal_time(current_time + r.nextDouble());
			waiting_planes_takeoff.add(terminal_plane);
			Collections.sort(waiting_planes_takeoff);
		}else{
			System.out.println(">>>>>Avion esperando a que haya terminal libre");
			Plane not_terminal_plane = waiting_planes_terminal.get(0);
			not_terminal_plane.setGuidance_time(not_terminal_plane.getGuidance_time() + 100);
		}
	}

}