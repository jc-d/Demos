package Order;

/**
 * Created by Jeremy Carey-dressler on 3/29/14.
 */
public class Utils {

    public static boolean hasUnicode(String value) {
        if(value==null) return false;
        for(char c : value.toCharArray()) {
            if(c >= 256) {
                return true;
            }
        }
        return false;
    }
}
