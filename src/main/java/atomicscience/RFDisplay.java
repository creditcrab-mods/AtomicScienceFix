package atomicscience;

public class RFDisplay {
    public static String displayRF(int rf){
        if(rf < 1000){
            return rf + " RF";
        }

        int thousands = rf / 1000;
        int remainder = rf % 1000;
        return thousands + "." + remainder / 100 + "k RF";
    }
}
