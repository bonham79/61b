public class Planet {
	public double xxPos, yyPos, xxVel, yyVel, mass;
	private String imgFileName;

	public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
		/**Initializes a Planet class */
		this.xxPos = xxPos;
		this.yyPos = yyPos;
		this.xxVel = xxVel;
		this.yyVel = yyVel;
		this.mass = mass;
		this.imgFileName = imgFileName;
	}

	public Planet(Planet P) {
		/**Constructs identical planet to Planet P */
		this.xxPos = P.xxPos;
		this.yyPos = P.yyPos;
		this.xxVel = P.xxVel;
		this.yyVel = P.yyVel;
		this.mass = P.mass;
		this.imgFileName = P.imgFileName;
	}

	public double calcDistance(Planet Q) {
		/**Returns distance between Planet calling method and supplied planet */
		double x2 = (this.xxPos - Q.xxPos) * (this.xxPos - Q.xxPos);
		double y2 = (this.yyPos - Q.yyPos) * (this.yyPos - Q.yyPos);
		return Math.sqrt(x2 + y2);
	} 

	public double calcForceExertedBy(Planet Q) {
		/**Calculates force exerted on planet calling method by Planet Q */
		return 6.67e-11 * this.mass * Q.mass/
		this.calcDistance(Q)/this.calcDistance(Q);
	}

	public double calcForceExertedByX(Planet Q) {
		/**Calculates horizontal force exerted on object 
		calling method by Planet Q */
		return this.calcForceExertedBy(Q) * (Q.xxPos - this.xxPos) / this.calcDistance(Q);
	}

	public double calcForceExertedByY(Planet Q) {
		/**Calculates vertical force exerted on object 
		calling method by Planet Q */
		double yyDist;
		if (this.yyPos > Q.yyPos) {
			yyDist = this.yyPos - Q.yyPos;
		} else {
			yyDist = Q.yyPos - this.yyPos;
		}
		return this.calcForceExertedBy(Q) * (Q.yyPos -this.yyPos) / this.calcDistance(Q);
	}

	public double calcNetForceExertedByX(Planet[] planets) {
		/**Calculates the net horizontal force exerted on object calling method
		by all planets in array planets */
		double xxNet = 0.0;
		for (Planet p : planets) {
			if (this.equals(p)) {
				continue;
			}
			xxNet += this.calcForceExertedByX(p);
		}
		return xxNet;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		/**Calculates the net vertical force exerted on object calling method
		by all planets in array planets */
		double yyNet = 0.0;
		for (Planet p : planets) {
			if (this.equals(p)) {
				continue;
			}
			yyNet += this.calcForceExertedByY(p);
		}
		return yyNet;
	}

	public void update(double dt, double fX, double fY) {
		double aXX = fX/this.mass;
		double aYY = fY/this.mass;
		this.xxVel = aXX * dt + this.xxVel;
		this.yyVel = aYY * dt + this.yyVel;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel; 
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}

	public String imgFileName(){
		return this.imgFileName;
	}
}