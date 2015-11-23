/**
 * Copyright 2015 Diego Martin,Javier Montero

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
package utils;

public class DistributionGeneratorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Testing 
		
		//LANDING DISTRIBUTION PARAMETERS
		double NORMAL_MEAN = 10;
		double NORMAL_DESVIATION = 3;
		
		//TAKING OFF DISTRIBUTION PARAMETERS
		double UNIFORM_A = 10;
		double UNIFORM_B = 15;
		
		//PLANES GUIDING DISTRIBUTION PARAMETERS
		double EXPONENTIAL_LAMBDA_GUIDING = 30.0 / 60.0;
		
		//BOARDING TERMINAL DISTRIBUTION PARAMETERS
		double EXPONENTIAL_LAMBDA_TERMINAL = 45.0;
		
		//POISSON RATE OF 4920 min (4th day at 10:00 a.m.)
		double POISSON_RATE = DistributionGenerator.getPoissonRate(4920);
		
		System.out.println("Poisson with rate: " + POISSON_RATE);
		
		System.out.println("Generate Exponential: " + DistributionGenerator.exponential(POISSON_RATE));
		
		for (int i = 0; i < 43000; i++){
			System.out.println(DistributionGenerator.poisson(POISSON_RATE));
		}
		
		System.out.println("Normal with mean: " + NORMAL_MEAN + "and desviation: " + NORMAL_DESVIATION);
		for (int i = 0; i < 100; i++){
			System.out.println(DistributionGenerator.gaussian(NORMAL_MEAN, NORMAL_DESVIATION));
		}
		
		System.out.println("Uniform between A: " + UNIFORM_A + " and B: " +UNIFORM_B);
		for (int i = 0; i < 100; i++){
			System.out.println(DistributionGenerator.uniform(UNIFORM_A, UNIFORM_B));
		}	
		
		System.out.println("Exponential with lambda: " + EXPONENTIAL_LAMBDA_GUIDING);
		for (int i = 0; i < 100; i++){
			System.out.println(DistributionGenerator.exponential(EXPONENTIAL_LAMBDA_GUIDING));
		}
		
		System.out.println("Exponential with lambda: " + EXPONENTIAL_LAMBDA_TERMINAL);
		for (int i = 0; i < 100; i++){
			System.out.println(DistributionGenerator.exponential(EXPONENTIAL_LAMBDA_TERMINAL));
		}	

	}

}
