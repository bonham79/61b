public class hw0{

	public static void main(String[] args){
		int[] a = {1, 2, -3, 4, 5, 4};
    	int n = 3;
		windowPosSum(a, n);

    	// Should print 4, 8, -3, 13, 9, 4
    	System.out.println(java.util.Arrays.toString(a)); 
	}
	

	public static void drawTriangle(int N){
		//prints triangle of height N and side length N.
		int row = 1;
		int col = 1;
		while (row <= N) {
			while (col <= row){
				System.out.print("*");
				col += 1;
			}
			System.out.println("");
			col = 1;
			row += 1;
		}
	}

	public static int  max(int[] m){
	//Returns maximum value of int array m
		int largest = m[0];
		int counter = 0;
		while (counter < m.length) {
			if (m[counter] > largest) {
				largest = m[counter];
			}
			counter += 1;
		}
		return largest;
	}

	public static int forMax(int[] m) {
		//Implementation of max using a for loop
		int largest = m[0];
		for(int counter = 1; counter < m.length; counter += 1){
			if (m[counter] > largest) {
				largest = m[counter];
			}
		}
		return largest;
	}

	public static void windowPosSum(int[] a, int n) {
		//Replaces each positive value element a[i] with the sum of values from a[i] to a[i+n]
		//If array is shorter than a[i+n] element, then sums to end of array
		for (int index = 0; index < a.length; index +=1) {
			if (a[index] < 0) {
				continue;
			}
			int total = a[index];
			for (int counter = 1; counter <= n; counter += 1) {
				if (counter + index >= a.length) {
					break;
				}
				total += a[index + counter];
			}
			a[index] = total;
		}
	}
}