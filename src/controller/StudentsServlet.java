package controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import abstracts.JsonObject;

import abstracts.RESTGeneralError;
import abstracts.RESTGeneralSuccess;
import com.google.gson.Gson;
import entity.Student;
import entity.error.RESTError;
import entity.error.ErrorResource;
import entity.json.RESTSingleResource;
import utility.RESTFactory;
import utility.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentsServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doOptions(req, resp);
        Student s = null;
        try {
            s = RESTSingleResource.getInstanceFromRequest(req).getData().getInstance(Student.class);
        } catch (Exception e) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).addError(e.getMessage()).doResponse(resp);
            return;
        }

        long nowMls = System.currentTimeMillis();
        int status = 1;
        if (s!=null) {
            s.setId(nowMls);
            s.setCreatedAt(nowMls);
            s.setUpdatedAt(nowMls);
            s.setStatus(status);
        }
        List<ErrorResource> lErrors = Validator.getInstance().validateStudent(s);
        if (lErrors.size() > 0) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).setError(lErrors).doResponse(resp);
            return;
        }
        if (Validator.checkRollnumerExist(s.getRollNumber())) {
            RESTFactory.make(RESTGeneralError.CONFLICT).addError("409", "Rollnumber Conflict", "RollNumber existed").doResponse(resp);
            return;
        }
        ofy().save().entity(s).now();
        RESTFactory.make(RESTGeneralSuccess.CREATED).addData(s).doResponse(resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doOptions(req, resp);
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
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Origin, Token");
    }

    private void getList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int limit = 10, page = 1, total;
        String temp;
        try {
            if ((temp = req.getParameter("page")) != null) page = Integer.parseInt(temp);
            if ((temp = req.getParameter("limit")) != null) limit = Integer.parseInt(temp);
        } catch (Exception e) {
            System.err.println("Failed when parse the parameter!");
        }

        List<Student> ls = ofy().load().type(Student.class).limit(limit).offset((page - 1) * limit).list();
        total = ofy().load().type(Student.class).count();

        RESTFactory.make(RESTGeneralSuccess.OK).addData(ls)
                .putMeta("limit", limit)
                .putMeta("page", page)
                .putMeta("total", total)
                .doResponse(resp);
    }

    private void getOne(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        long id;
        try {
            id = Long.parseLong(path);
        } catch (Exception e) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).addError("Invalid id! Error when parse the id!").doResponse(resp);
            return;
        }
        Student s = ofy().load().type(Student.class).id(id).now();
        System.out.println(s);
        if (s == null) {
            RESTFactory.make(RESTGeneralError.NOT_FOUND).addError("There is no student with this id").doResponse(resp);
            return;
        }
        RESTFactory.make(RESTGeneralSuccess.OK).addData(s).doResponse(resp);
    }
}
