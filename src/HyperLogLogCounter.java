import java.util.ArrayList;
import java.util.Scanner;

public class HyperLogLogCounter{
    DistinctElements countH; 
    int m;
    int k;
    ArrayList<Integer> allNumbers;
    int threshold=0;

    HyperLogLogCounter(int m, int k){
        allNumbers = new ArrayList<>();// stores all the numbers
        scanInts();
        this.m = m;
        this.k = k;
      
    }
    private int rho(int x){
        int i = (int) (Math.log(x)/ Math.log(2));
        return i;
    }
    private int f(int x){
        return ((x*0xbc164501) & (0x7fe00000) >> 21);
    }
    private double Z(int[] M, int m) {
        int denominator = 0;
        for (int i = 0; i < m; i++) {
            denominator += (int)(Math.pow(2, -M[i]));
        }
        if (denominator != 0) return (1 / denominator);
        else return -1.0;
    }

    public int E(int m, double Z) {
        return (int) (m*m*Z* 0.7213/(1 + 1.079/m));
    }

    public int V(int[] M){
        int zeros =0;
        for (int i = 0; i<M.length; i++){
            if (M[i]==0){ zeros+=1;}
        }
        return zeros;
    }

    private void scanInts(){
        Scanner sc = new Scanner(System.in); 
        boolean isFirst=true;
        while(sc.hasNextInt()){
            if(isFirst){ this.threshold = sc.nextInt();
            isFirst = false;}
            else{
             allNumbers.add(sc.nextInt());
            }
            
        }

    }

    /**
     *  algorithm1 for estimating the number of distinct integers in a sequence of integers y1,...,yn using an array M of m integers,
     *  where m is a power of 2 with m much smaller than n.
     * While you are free to use different hash functions, it is suggested that you use h from Exercise 1(calculateHashValue() method) and as f use the following heuristic hash function:
     * f(x) = ((x*0xbc164501) & 0x7fe00000) >> 21.
     */
    public int hyperLogLogCounter(){
        int[] M = new int[m]; //for i:=0 to m-1 do M[i]:=0
        int n = allNumbers.size();
        for(int i=1;i<=n;i++){
            // j is an index
            int j = f(allNumbers.get(i-1));
            countH = new DistinctElements(allNumbers.get(i-1));
            M[j] =Math.max(M[j], rho(countH.calculateHashValue()));
         }
         double Z = Z(M,m); 
         int V = V(M);
         int E =E(m,Z);
         if (E < 2.5*m && V > 0){
              E= (int) (m * Math.log(m/V));
         }

        return E;
    }
    public int getThreshold(){
        return threshold;
    }

    // public static void main(String[] args) {
    //      //106, . . . , 2 · 106 − 1 with 1 million distinct items: <= but maybe we don't need to have it? 
      
    //      //exercise 2 ( m = 1024 and k = 32). Test the input on 106, . . . , 2 · 106 − 1 with 1 million distinct items: 
    //      //How many distinct elements are reported by your implementation? (Add this number to your report.). It also needs to take 
    //      HyperLogLogCounter counter = new HyperLogLogCounter(1024,32);
    //      int n =counter.hyperLogLogCounter();
    //      int threshold = counter.getThreshold(); 
    //      if(n>threshold){
    //          System.out.println("above");
    //      }else{
    //          System.out.println("below");
    //      }
         
    // }

}