package Reflections;

import java.lang.reflect.Method;

/**
 * Created by Jeremy Carey-dressler on 3/13/14.
 */
public class Methods {
    public static Object runMethodWithNoArgs(Object object, String command) {
        if(object==null) throw new NullPointerException("Invalid object.");
        try {
            Method m = object.getClass().getMethod(command, null);
            return m.invoke(object, null);
        }catch(Throwable t) {
            throw new Error("Unable to run method", t);
        }
    }
}