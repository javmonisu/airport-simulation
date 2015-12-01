/**
 * Copyright 2015 Javier Montero,Diego Martin

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

public class InitializeSimulation {

	public static void main(String[] args) {
//		int T = 43200;
//		int nTerminal = 50;
//		int nCars = 20;
//		int nRunways = 3;
		
		int T = 100;
		int nTerminal = 50;
		int nCars = 25;
		int nRunways = 4;
		
//		Simulation s = new Simulation();		
		Simulation s = new Simulation(T, nTerminal, nCars, nRunways);

		s.simulation();
	}
}