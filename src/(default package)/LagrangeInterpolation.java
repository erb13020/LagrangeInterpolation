import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LagrangeInterpolation{
    @SuppressWarnings("unused")
	private static double[] x1;
    private static double[] y1;
    private static double[] f1;
    
  public static double[] findPolynomialFactors (double[] x, double[] y)
    throws RuntimeException
  {
    int n = x.length;

    double[][] data = new double[n][n];
    double[]   rhs  = new double[n];
    
    for (int i = 0; i < n; i++) {
      double v = 1;
      for (int j = 0; j < n; j++) {
        data[i][n-j-1] = v;
        v *= x[i];
      }

      rhs[i] = y[i];
    }

    // Solve m * s = b
    
    Matrix m = new Matrix (data);
    Matrix b = new Matrix (rhs, n);

    Matrix s = m.solve (b);

    return s.getRowPackedCopy();
  }

  static private String convertDecimalToFraction(double x){
	    if (x < 0){
	        return "-" + convertDecimalToFraction(-x);
	    }
	    double tolerance = 1.0E-6;
	    double h1=1; double h2=0;
	    double k1=0; double k2=1;
	    double b = x;
	    do {
	        double a = Math.floor(b);
	        double aux = h1; h1 = a*h1+h2; h2 = aux;
	        aux = k1; k1 = a*k1+k2; k2 = aux;
	        b = 1/(b-a);
	    } while (Math.abs(x-h1/k1) > x*tolerance);
	    
	    return h1+"/"+k1;
	    
	}
  

  
  public static void main (String args[])
  {
	  	System.out.println("LAGRANGE INTERPOLATING POLYNOMIAL COMPUTER");
	  
		ArrayList<Double> xi = new ArrayList<Double>();
		
		ArrayList<Double> yi = new ArrayList<Double>();
      	
		ArrayList<String> op = new ArrayList<String>();
		
		Scanner reader = new Scanner(System.in);
		
		int xc = 0;
		int yc = 0;
		
		boolean lock = true;
		
		while(lock == true) {
			System.out.println("Enter an x value");
			double xv = reader.nextDouble();
			if(xi.contains(xv)) {
				System.out.println(xv + " has already been entered as an x value and will cause the input matrix to be singular." + " Try again.");
			}else {
				xi.add(xc, xv);
				xc++;
				System.out.println("Enter a y value");
				double yv = reader.nextDouble();
				yi.add(yc, yv);
				yc++;
				op.add("(" + String.valueOf(xv) + "," + String.valueOf(yv) + ")");
				System.out.println();
				System.out.println("Entering ordered pair (" + xv + "," + yv + ")" + " as input");
				System.out.println();
				System.out.println("Current Input:");
				System.out.println(op.toString());
				System.out.println();
				System.out.println("Enter another value?");
				System.out.println("[0] Begin Lagrange Interpolation)");
				System.out.println("[1] Enter another value");
				int u = reader.nextInt();
				if(u == 0) {
					lock = false;
				}
			}
			
		}
		int sx = xi.size();
		int yx = yi.size();
		
	    double x[] = new double[sx];
	    
	    for (int i = 0; i < sx; i++) {
	    	x[i] = xi.get(i);
	    }
	    
	    double y[] = new double[yx];
	    
	    for (int i = 0; i < yx; i++) {
	    	y[i] = yi.get(i);
	    }
	    
	    
	if(!(x.length == y.length)) {
		System.exit(0);
	}
	

    double f[] = LagrangeInterpolation.findPolynomialFactors (x, y);
    
	System.out.println(Arrays.toString(x));
	System.out.println(Arrays.toString(y));
	
    x1 = x;
    y1 = y;
    f1 = f;
    
    int n = Array.getLength(y);
    
    String rationalCVector[] = new String[n]; 
    
    System.out.println("What do you want to do?");
    System.out.println("[0] Display Decimal Format");
    System.out.println("[1] Display Rational Format");
    int r = reader.nextInt();
    if(r == 0) {
    	 for (int i = 0; i < n; i++) {
    	    	DecimalFormat df = new DecimalFormat("#");
    	        df.setMaximumFractionDigits(1000000);
    	        int power = n - (i+1);
    	        if(i == 0) {
    	        	System.out.print("f(x)= ");
    	        }
    	        System.out.print(df.format(f[i]));
    	        System.out.print("x" + "^" + power);
    	        if((i<n-1)) {
    	        	System.out.print(" + ");
    	        }
    	    }
    }
    
    if(r == 1) {
    	for (int i = 0; i < n; i++) {
        	String rational = convertDecimalToFraction(f[i]);
        	rationalCVector[i] = rational;
        	int power = n - (i+1);
        	if((i==0)) {
        		System.out.print("f(x)= ");
        	}
        	System.out.print("(" + rationalCVector[i] + ")" + "x" + "^" + power);
        	if((i<n-1)) {
        		System.out.print(" + ");
        	}
        }
    }
    
double result = 0;
    
    System.out.println();
    System.out.println();
    System.out.println("What value of X do you want to interpolate?");
    int input = reader.nextInt();
    
    for (int i = 0; i < n; i++) {
    	int power = n - (i+1);
    	double coefficient = f[i];
    	double xval = input;
    	double val = coefficient * Math.pow(xval, power);
    	result = result + val;
    }
    
    
    System.out.println();
    System.out.println("f(" + input + ") = " + result);
    
    System.out.println();
    System.out.println("The interpolated value at x=" + input + " is " + result);
    
    interpolate(input);
    
    System.out.println("Print whole interpolation?");
    System.out.println("[0] Exit");
    System.out.println("[1] Continue");
    
    int r1 = reader.nextInt();
    if(r1 == 0) {
    	System.exit(0);
    }else {
    	System.out.println("Enter degree of precision. Example: 1");
    	double r2 = reader.nextDouble();
    	
    	double l = x[0];
    	double lastNum = x[x.length-1];
    	lastNum++;
    	
    	while(l<lastNum) {
    		System.out.print("f(" + l + ") = ");
    		System.out.print(interpolater(l));
    		System.out.println();
    		l = l + r2;
    	}
    }

    reader.close();
  }
  
  static private void interpolate(int input) {
	  double result = 0;
	  int n = Array.getLength(y1);
	    
	    for (int i = 0; i < n; i++) {
	    	int power = n - (i+1);
	    	double coefficient = f1[i];
	    	double xval = input;
	    	double val = coefficient * Math.pow(xval, power);
	    	result = result + val;
	    }
	    System.out.println(result);
  }

  
static private double interpolater(double input) {
	  double result = 0;
	  int n = Array.getLength(y1);
	    
	    for (int i = 0; i < n; i++) {
	    	int power = n - (i+1);
	    	double coefficient = f1[i];
	    	double xval = input;
	    	double val = coefficient * Math.pow(xval, power);
	    	result = result + val;
	    }
	    return result;
  }
  
}