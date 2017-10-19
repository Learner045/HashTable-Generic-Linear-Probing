package GenericLinearProbing;

public class GenericTest {

    public static void main(String args[]){

        HashTableGeneric<Integer,Integer>table=new HashTableGeneric<>();
        table.put(1,10);
        table.put(11,20);
        table.put(111,120);
        table.put(121,30);

        table.put(131,130);
        table.put(161,230);
        table.put(181,330);

        //forms a single cluster
        table.put(3,31);
        table.put(4,90);
        table.put(5,51);
      //  System.out.println(table.get(111));

        table.remove(3);
        System.out.println(table.get(121));
        System.out.println(table.get(181));
        System.out.println(table.get(4));
        System.out.println(table.get(5));
    }


}
