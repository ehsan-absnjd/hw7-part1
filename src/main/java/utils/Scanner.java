package utils;


public class Scanner {
    java.util.Scanner sc = new java.util.Scanner(System.in );
    public int getInt(){
        int a;
        while(true){
            try{
                String st = sc.next();
                a = Integer.valueOf(st);
                break;
            }catch (Exception e){
                System.out.println("invalid input, try again");
            }
        }
        return a;
    }
    public String getString(){
        return sc.next();
    }


}