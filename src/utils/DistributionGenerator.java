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

import java.util.Random;


public final class DistributionGenerator {

//    private static Random random;    // pseudo-random number generator
//    private static long seed;        // pseudo-random number generator seed
//
//    // static initializer
//    static {
//        // this is how the seed was set in Java 1.4
//        seed = System.currentTimeMillis();
//        random = new Random(seed);
//    }
	
	public static final Random random = new Random();
	
	 /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform() {
        return random.nextDouble();
    }
	
	/**
     * Returns a random real number uniformly in [a, b).
     * 
     * @param  a the left endpoint
     * @param  b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless <tt>a < b</tt>
     */
    public static double uniform(double a, double b) {
        if (!(a < b)) throw new IllegalArgumentException("Invalid range");
        return a + uniform() * (b-a);
    }
    
    /**
     * Returns a random integer from a Poisson distribution with mean &lambda;.
     *
     * @param  lambda the mean of the Poisson distribution
     * @return a random integer from a Poisson distribution with mean <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt> and not infinite
     */
    public static int poisson(double lambda) {
    	//TODO Tal como esta ahora mismo no nos da en que momento nos llega el siguiente avión en minutos.
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("Parameter lambda must be positive");
        if (Double.isInfinite(lambda))
            throw new IllegalArgumentException("Parameter lambda must not be infinite");
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= L);
        return k-1;
    }
    
    /**
     * Returns a random real number from an exponential distribution
     * with rate &lambda;.
     * 
     * @param  lambda the rate of the exponential distribution
     * @return a random real number from an exponential distribution with
     *         rate <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt>
     */
    public static double exponential(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("Rate lambda must be positive");
        return -Math.log(1 - uniform()) / lambda;
    }
    
    /**
     * Returns a random real number from a standard Gaussian distribution.
     * 
     * @return a random real number from a standard Gaussian distribution
     *         (mean 0 and standard deviation 1).
     */
    public static double gaussian() {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x*x + y*y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * Returns a random real number from a Gaussian distribution with mean &mu;
     * and standard deviation &sigma;.
     * 
     * @param  mu the mean
     * @param  sigma the standard deviation
     * @return a real number distributed according to the Gaussian distribution
     *         with mean <tt>mu</tt> and standard deviation <tt>sigma</tt>
     */
    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }
    
    /**
     * Returns the poisson proccess rate in our system
     * 
     * @param t - time in minutes along a month
     * @return
     */
    public static double getPoissonRate(double t){
    	/* y = mx + n */
    	double aviones = 0.0;
    	double lambda = 0.0;
    	
    	/* As "t" could be any minute in a month,
    	 * we need to get the "t" in minutes in THE day */
    	
    	double t_in_day = t % (24.0*60.0);  /* 24*60 are the number of minutes in a day */
    	double h_in_day = t_in_day / 60.0;
    	
    	//section 1
    	if(h_in_day >= 0.0 && h_in_day <= 5.0){
    		aviones = 2.0/5.0*h_in_day + 1.0;
    	}
    	
    	//section 2
		if(h_in_day >= 5.0 && h_in_day <= 8.0){
			aviones = -1.0/3.0*h_in_day + 14.0/3.0;
		}
		
		//section 3
		if(h_in_day >= 8.0 && h_in_day <= 15.0){
			aviones = 3.0/7.0*h_in_day - 10.0/7.0;
		}
		
		//section 4
		if(h_in_day >= 15.0 && h_in_day <= 17.0){
			aviones = -3.0/2.0*h_in_day + 55.0/2.0;
    	}
		
		//section 5
		if(h_in_day >= 17.0 && h_in_day <= 24.0){
			aviones = -1.0/7.0*h_in_day + 31.0/7.0;
    	}
		
		lambda = aviones/t_in_day;
		
		System.out.println("time in minutes in the day: "+t_in_day);
		
		System.out.println("\n number of planes: "+aviones);
		
		return lambda;
    }
}