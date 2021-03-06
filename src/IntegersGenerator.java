public class IntegersGenerator{
    HyperLogLogCounter counter;
    DistinctElements distinct;

    IntegersGenerator(){
        counter = new HyperLogLogCounter(1024, 32);
        distinct = new DistinctElements();
    }
    public void distribution(){
        for(int x=1; x<=1000000;x++){
           long number = counter.getRho(distinct.calculateHashValue(x)); 
           System.out.print(x + ",");
           //long number = counter.getRho(x); <- checking the i position for testing
            System.out.print(number);
            System.out.println();
        }
    }
    public static void main(String[] args) {
        IntegersGenerator gen = new IntegersGenerator(); 
        gen.distribution();
    }
}