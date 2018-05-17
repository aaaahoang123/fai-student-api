package controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import abstracts.JsonObject;

import abstracts.RESTGeneralSuccess;
import com.google.gson.Gson;
import entity.Student;
import entity.error.ErrorAPI;
import entity.error.ErrorResource;
import entity.json.RESTSingleResource;
import entity.json.JsonResource;
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
            ErrorAPI errorAPI = new ErrorAPI();
            errorAPI.addRs(ErrorResource.getInstance("400", "Format Data invalid", e.getMessage()));
            resp.setStatus(400);
            resp.getWriter().print(gson.toJson(errorAPI));
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
        ErrorAPI errorAPI = new ErrorAPI();
        if (lErrors.size() > 0) {
            errorAPI.setErrors(lErrors);
            resp.setStatus(400);
            resp.getWriter().print(gson.toJson(errorAPI));
            return;
        }
        if (Validator.checkRollnumerExist(s.getRollNumber())) {
            errorAPI.addRs(ErrorResource.getInstance("409", "Rollnumber Conflict", "RollNumber existed"));
            resp.setStatus(409);
            resp.getWriter().print(gson.toJson(errorAPI));
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
        JsonObject jo;
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
            resp.getWriter().print("Invalid id! Error when parse the id!");
            return;
        }
        Student s = ofy().load().type(Student.class).id(id).now();
        RESTFactory.make(RESTGeneralSuccess.OK).addData(s).doResponse(resp);
    }
}
