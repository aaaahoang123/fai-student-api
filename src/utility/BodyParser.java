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
    public static String parseBody(ServletRequest request) throws IOException {

        BufferedReader br = request.getReader();
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp)
                .append("\n");
        }

        return sb.toString();
    }
}
