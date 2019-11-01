import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class HyperLogLogCounter{
    int m; // power of 2, we want to store m integers in M array
    int k; // 32
    int[] M;
    int noOfZeros;
    DistinctElements de = new DistinctElements();

    HyperLogLogCounter(int m, int k){
        this.m = m;
        this.k = k;
        this.M = new int[m]; // for i:=0 to m-1 do M[i]:=0
        this.noOfZeros = m;
    }


    private int f(int x){
        return ((x*0xbc164501) & 0x7fe00000) >> 21; // !!! parenthesis was in the wrong place
    }

    private double getZ() {
        double denominator = 0.0;
        double Z = 0.0;

        for (int i = 0; i < m; i++) {
            denominator += Math.pow(2, (-M[i]));
        }
        if (denominator != 0) Z = 1 /denominator;

        return Z;
    }

    public double getE(double Z) {
        return (m*m*Z* 0.7213/(1 + 1.079/m));
    }

    // get the number of zeros in M array
    public int getV(){
        /*int V = 0;
        for (int i = 0; i < M.length; i++){
            if (M[i] == 0) { 
                V += 1;
            }
        }
        return V;*/
        return noOfZeros;
    }

    // get the position of the first 1 in the binary representation of int read from left
    public int getRho(int x) {
        // 2 base logarithm of x if x is positive
        // !!! negative numbers in binary start with 1
        // Java uses two's complement for negative 
        // numbers and the basic rule is to take the positive, invert all bits then add one.
        int i = 31; // if we have a negative x, we return k-i = 32 - 31 = 1 -> 1st digit is 1
        if (x >= 0) i = (int) (Math.log(x) / Math.log(2));
        return k-i;
    }

    // sequenceElement -> y[i]
    // numberFromRho -> rho(h(y[i]))
    public void addSequenceElementToM (int sequenceElement){
        // j:=f(y[i])
        int j = f(sequenceElement);
        // h(y[i])
        int hashed = de.calculateHashValue(sequenceElement);
        // rho(h(y[i]))
        int numberFromRho = getRho(hashed);
        // M[j]:=max(M[j], rho(h(y[i])))
        if (M[j] == 0 && numberFromRho != 0) noOfZeros--;
        M[j] = Math.max(M[j], numberFromRho);
        // System.out.println("from max M[j]: " + M[j]);
    }

    /**
     *  algorithm1 for estimating the number of distinct integers in a sequence of integers y1,...,yn using an array M of m integers,
     *  where m is a power of 2 with m much smaller than n.
     *  While you are free to use different hash functions, it is suggested that you use h from Exercise 1(calculateHashValue() method) and as f use the following heuristic hash function:
     *  f(x) = ((x*0xbc164501) & 0x7fe00000) >> 21.
     */
    public double hyperLogLogCounter(){
        // !!! parameter of ln should be a double, it changes the value of E
        double Z = getZ();
        // System.out.println("Z: " + Z);
        //System.out.println(Z);
        // int V = getV();
        // System.out.println("V int: " + V);
        double dV = (double) getV();
        // System.out.println("V double: " + dV);
        // double E = getE(Z);
        double dE = getE(Z);
        //System.out.println(E);
        if (dE < 2.5*m && dV > 0){
            // System.out.println("true");
            // double parameter = m / V;
            // System.out.println("Parameter with V as an int: " + String.format("%.6f", parameter));
            double dParameter = m / dV;
            // System.out.println("Parameter with V as a double: " + dParameter);
            // E = m * Math.log(parameter);
            // System.out.println("E with paraneter as an int: " + String.format("%.6f", E));
            dE = m * Math.log(dParameter);
            // System.out.println("E with paraneter as a double: " + String.format("%.6f", dE));
        }
        return dE;
    }

    public static void main(String[] args) {
        
        int threshold = 0;
        HyperLogLogCounter counter = new HyperLogLogCounter(1024, 32);
        
        Scanner sc = new Scanner(System.in);
        boolean isFirst=true;
        
        // for i:=1 to n do
        while(sc.hasNextInt()){
            if(isFirst){
                // saving the threshold
                threshold = sc.nextInt();
                // System.out.println("Treshold: " + threshold);
                isFirst = false;
            } else{
                // currentNumber -> y[i]
                int currentNumber = sc.nextInt();

                // add number to M[]
                counter.addSequenceElementToM(currentNumber);
                }
            }

        double count = counter.hyperLogLogCounter();

        if (count < threshold) {
            System.out.println("below");
        } else {
            System.out.println("above");
        }
    } 
}