package utility;

import abstracts.RESTGeneralError;
import abstracts.RESTResponser;

public class RESTErrorResponser extends RESTResponser{

    public RESTErrorResponser(RESTGeneralError defind) {

    }
    @Override
    public void buildResponseDocument() {

    }

    @Override
    public <T> RESTErrorResponser addData(T t) {
        return null;
    }

}
