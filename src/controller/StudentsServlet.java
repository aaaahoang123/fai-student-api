package controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.gson.Gson;
import com.googlecode.objectify.cmd.Query;
import design_java_rest.RESTFactory;
import design_java_rest.RESTGeneralError;
import design_java_rest.RESTGeneralSuccess;
import design_java_rest.RESTHandle;
import design_java_rest.entity.RESTDocumentSingle;
import design_java_rest.entity.RESTError;
import entity.Student;
import utility.Validator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentsServlet extends HttpServlet {
    private static ArrayList<String> arrayAccept = new ArrayList<>();
    static {
        arrayAccept.add("GET");
        arrayAccept.add("OPTIONS");
        arrayAccept.add("HEAD");
        arrayAccept.add("PUT");
        arrayAccept.add("POST");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RESTHandle.passRequest(resp,arrayAccept);
        Student student;
        try {
            RESTDocumentSingle documentSingle = RESTDocumentSingle.getInstanceFromRequest(req);
            student = documentSingle.getData().getInstance(Student.class);
        } catch (Exception e) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Format Data invalid",e.getMessage()).doResponse(resp);
            return;
        }
        student.setId(System.currentTimeMillis());
        student.setCreatedAt(System.currentTimeMillis());
        student.setUpdatedAt(System.currentTimeMillis());
        student.setStatus(1);
        List<RESTError> lErrors = Validator.getInstance().validateStudent(student);
        if (lErrors.size() > 0) {
            RESTFactory.make(RESTGeneralError.FORBIDDEN).putErrors(lErrors).doResponse(resp);
            return;
        }
        if (Validator.checkRollnumerExist(student.getRollNumber())) {
            RESTFactory.make(RESTGeneralError.CONFLICT).putErrors(RESTGeneralError.CONFLICT.code(), "RollNumber Conflict", "RollNUmber existed");
            return;
        }
        ofy().save().entity(student).now();
        RESTFactory.make(RESTGeneralSuccess.CREATED).putData(student).doResponse(resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RESTHandle.passRequest(resp,arrayAccept);
        String path;
        if (req.getPathInfo() == null) path = "";
        else path = req.getPathInfo().substring(1);

        if (path.equals("")) {
            getList(req, resp);
        }
        else {
            getOne(req, resp, path);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RESTHandle.doOption(resp, arrayAccept);
    }

    private void getList(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int limit = 10, page = 1, totalItem, totalPage;
        String temp;
        try {
            if ((temp = req.getParameter("page")) != null) page = Integer.parseInt(temp);
            if ((temp = req.getParameter("limit")) != null) limit = Integer.parseInt(temp);
        } catch (Exception e) {
            System.err.println("Failed when parse the parameter!");
        }
        Query<Student> query = ofy().load().type(Student.class).filter("status", 1);
        totalItem = query.count();
        totalPage = (int) Math.ceil((double)totalItem/limit);
        List<Student> lStudent = query.limit(limit).offset((page - 1) * limit).list();

        RESTFactory.make(RESTGeneralSuccess.OK).putData(lStudent).putMeta("totalPage", totalPage)
                .putMeta("totalItem", totalItem).putMeta("page", page).doResponse(resp);
    }

    private void getOne(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        long id;
        try {
            id = Long.parseLong(path);
        } catch (Exception e) {
            RESTFactory.make(RESTGeneralError.NOT_FOUND).putErrors(RESTGeneralError.NOT_FOUND.code(), "Not Found", "Not Found").doResponse(resp);
            return;
        }
        Student student = ofy().load().type(Student.class).id(id).now();
        if(student == null){
            RESTFactory.make(RESTGeneralError.NOT_FOUND).putErrors(RESTGeneralError.NOT_FOUND.code(), "Student not exist or deleted", "").doResponse(resp);
            return;
        }
        RESTFactory.make(RESTGeneralSuccess.OK).putData(student).doResponse(resp);
    }
}
