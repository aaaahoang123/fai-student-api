package utility;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Description: The class use to parse the body of request to json string.
 */

public class BodyParser {
    /**
     * @Return a string of json, that the body of request
     */
    public static String parseBody(ServletRequest request) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = request.getReader();
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp)
                        .append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return sb.toString();
    }
}
