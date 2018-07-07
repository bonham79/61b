public class TestPlanet {
	public static void main(String[] args) {
		Planet Q = new Planet(5.0, 10.0, 25.0, 35.0, 100.0, "kkdog");
		Planet L = new Planet(70.0, 230.0, 304.0, 340.0, 355235.0, "342kd");
		System.out.println(Q.calcForceExertedBy(L) + " , " + L.calcForceExertedBy(Q));
	}
}