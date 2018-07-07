public class NBody {
	public static double readRadius(String txt) {
		/**Reads txt file txt and returns radius data */
		In in = new In(txt); 
		double radius = in.readDouble();
		radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String txt) {
		/**Reads txt file txt and returns array of planets */
		In in = new In(txt);
		int n = in.readInt();
		in.readDouble();
		Planet[] planets = new Planet[n];
		int index = 0;
		double xxPos, yyPos, xxVel, yyVel, mass;
		String img;

		while(!in.isEmpty() && index < n) {
			xxPos = in.readDouble();
			yyPos = in.readDouble();
			xxVel = in.readDouble();
			yyVel = in.readDouble();
			mass = in.readDouble();
			img = in.readString();
			planets[index] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
			index += 1;
		}
		return planets;
	}

	public static void main(String[] args){
		
		/**Initializes variables*/
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double radius = readRadius(filename);
		/**Actual drawing*/
		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Planet x : planets) {
			x.draw();
		}
		StdDraw.show();

		/**Animation*/
		for (double time = 0; time < T; time += dt) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for (int index = 0; index < planets.length; index +=1) {
				xForces[index] = planets[index].calcNetForceExertedByX(planets);
				yForces[index] = planets[index].calcNetForceExertedByY(planets);
			}
			for (int index = 0; index < planets.length; index +=1) {
				planets[index].update(dt, xForces[index], yForces[index]);
			}

			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet x : planets) {
				x.draw();
			}
			StdDraw.show();
			StdDraw.pause(1);

		//Data dump//
		}
		
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
		                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}

	}
}