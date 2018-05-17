package controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import abstracts.JsonObject;
import com.google.appengine.repackaged.com.google.gson.Gson;
import entity.Student;
import entity.error.ErrorAPI;
import entity.error.ErrorResource;
import entity.json.JOMultiResource;
import entity.json.JOSingleResource;
import entity.json.JsonResource;
import utility.Validator;
import utility.BodyParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentsServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doOptions(req, resp);
        Map<String, Object> attr = null;
        String rollNumber, name, email, phone, address, avatar;
        int gender, status;
        long birthday, nowMls;
        try {

            attr = gson.fromJson(BodyParser.parseBody(req), JOSingleResource.class).getData().getAttributes();
            rollNumber = attr.get("rollNumber").toString();
            name = attr.get("name").toString();
            email = attr.get("email").toString();
            phone = attr.get("phone").toString();
            address = attr.get("address").toString();
            if (!attr.get("gender").getClass().getSimpleName().equals("Double"))
                throw new ClassCastException("Gender must be integer");
            gender = (int) Math.floor((double) attr.get("gender"));
            if (!attr.get("birthday").getClass().getSimpleName().equals("Double"))
                throw new ClassCastException("Birthday must be long");
            birthday = (long) Math.floor((double) attr.get("birthday"));
            avatar = attr.get("avatar").toString();
        } catch (Exception e) {
            ErrorAPI errorAPI = new ErrorAPI();
            errorAPI.addRs(ErrorResource.getInstance("400", "Format Data invalid", e.getMessage()));
            resp.setStatus(400);
            resp.getWriter().print(gson.toJson(errorAPI));
            return;
        }

        nowMls = System.currentTimeMillis();
        status = 1;

        Student s = new Student(nowMls, rollNumber, name, gender, email, phone, address, birthday, avatar, nowMls, nowMls, status);
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
        JOSingleResource jsr = new JOSingleResource();
        jsr.setData(JsonResource.getInstance(s));
        resp.setStatus(201);
        resp.getWriter().print(gson.toJson(jsr));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path;
        if (req.getPathInfo() == null) path = "";
        else path = req.getPathInfo().substring(1);
        JsonObject jo;
        if (path.equals("")) {
            jo = getList(req, resp);
        }
        else {
            jo = getOne(req, resp, path);
        }

        doOptions(req, resp);
        resp.getWriter().write(gson.toJson(jo));
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Origin, Token");
    }

    private JsonObject getList(HttpServletRequest req, HttpServletResponse resp) {
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
        List<JsonResource> ljr = new ArrayList<>();
        for (Student s : ls) {
            ljr.add(JsonResource.getInstance(s));
        }

        HashMap<String, Object> meta = new HashMap<>();
        meta.put("limit", limit);
        meta.put("page", page);
        meta.put("total", total);

        JsonObject<List<JsonResource>> jo = new JOMultiResource();
        jo.setData(ljr);
        jo.setMeta(meta);

        return jo;
    }

    private JsonObject getOne(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        long id;
        try {
            id = Long.parseLong(path);
        } catch (Exception e) {
            resp.getWriter().print("Invalid id! Error when parse the id!");
            return null;
        }

        Student s = ofy().load().type(Student.class).id(id).now();

        JsonObject<JsonResource> jo = new JOSingleResource();
        jo.setData(JsonResource.getInstance(s));
        return jo;
    }
}
