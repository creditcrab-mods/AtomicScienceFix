package atomicscience;

public class RFDisplay {
    public static String displayRF(int rf){
        if(rf < 1000){
            return rf + " RF";
        }
        else if( rf < 1000000){
            int thousands = rf / 1000;
            int remainder = rf % 1000;
            return thousands + "." + remainder / 100 + "k RF";
        }
        int millions = rf / 1000000;
        int remainder = rf % 1000000;
        return millions + "." + remainder / 100000 +"M RF";

    }
}
