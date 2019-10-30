import java.util.*;

public class DistinctElements {

    private int[] A = {0x21ae4036, 0x32435171, 0xac3338cf, 0xea97b40c, 0x0e504b22, 0x9ff9a4ef, 0x111d014d, 0x934f3787, 0x6cd079bf, 0x69db5c31, 0xdf3c28ed, 0x40daf2ad, 0x82a5891c, 0x4659c7b0, 0x73dc0ca8, 0xdad3aca2, 0x00c74c7e, 0x9a2521e2, 0xf38eb6aa, 0x64711ab6, 0x5823150a, 0xd13a3a9a, 0x30a5aa04, 0x0fb9a1da, 0xef785119, 0xc9f0b067, 0x1e7dde42, 0xdda4a7b2, 0x1a1c2640, 0x297c0633, 0x744edb48, 0x19adce93};
    private int x;

    DistinctElements(int x){
        this.x = x;
    }

    /**
     * Exercise 1.
     * This method returns a integer hashcode for x using A matrix. 
     */
    public int calculateHashValue(){

        int result = 0;
        int pos = 31;

        for(int i = 0; i < 32; i++) {

            int temp = x & A[i];

            int hashValuePositioni = Integer.bitCount(temp) % 2;
            //System.out.println(hashValuePositioni);

            if ((Integer.bitCount(x & A[i]) % 2) == 1) {
            if (hashValuePositioni == 1)  {
                result |= ((hashValuePositioni) << pos);
            }
        }
        pos--;
        }
        return result;
    }

    public static void main(String[] args) {
        //106, . . . , 2 · 106 − 1 with 1 million distinct items: <= but maybe we don't need to have it? 
     
        //exercise 2 ( m = 1024 and k = 32). Test the input on 106, . . . , 2 · 106 − 1 with 1 million distinct items: 
        //How many distinct elements are reported by your implementation? (Add this number to your report.). It also needs to take 
        HyperLogLogCounter counter = new HyperLogLogCounter(1024,32);
        int n =counter.hyperLogLogCounter();
        int threshold = counter.getThreshold(); 
        if(n<=threshold){
            System.out.println("above");
        }else{
            System.out.println("below");
        }
        
   }
}







//exercise 1 main
   // public static void main(String[] args) {
       // HyperLogLogCounter counter = new HyperLogLogCounter()
        // Scanner sc = new Scanner(System.in);

        // while(sc.hasNextInt()){
        //     int x = sc.nextInt();
        //     // System.out.println("Input number: " + x);
        
        //     DistinctElements de = new DistinctElements(x);
        //     // System.out.println("Hash value: " + de.calculateHashValue(x));
        //     System.out.println(de.calculateHashValue(x));
        // }
       
    
