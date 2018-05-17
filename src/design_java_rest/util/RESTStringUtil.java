package design_java_rest.util;

import java.util.Collection;
import java.util.Iterator;

public class RESTStringUtil {
    /**
     * This fucntion join all collection string item to a string seperate by a
     * character that developer defined. This function will not append null or
     * empty element.
     *
     * @param collection
     *            collection string to join.
     * @param seperator
     *            seperate character.
     *
     * */

    public static String joinCollectionString(Collection<String> collection, String seperator){
        if(collection.isEmpty()){
            return "";
        }

        Iterator<String> iterator = collection.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(next != null && !next.isEmpty()){
                sb.append(seperator);
                sb.append(next);
            }
        }
        return sb.deleteCharAt(0).toString();
    }
}
