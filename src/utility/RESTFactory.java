package utility;

import abstracts.RESTGeneralError;
import abstracts.RESTGeneralSuccess;

public class RESTFactory {

    public static RESTSuccessResponser make(RESTGeneralSuccess general) {
        return new RESTSuccessResponser(general);
    }

    public static RESTErrorResponser make(RESTGeneralError general) {
        return new RESTErrorResponser(general);
    }
}
