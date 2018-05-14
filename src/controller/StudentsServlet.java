package controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import abstracts.JsonObject;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import entity.Student;
import entity.json.JOMultiResource;
import entity.json.JOSingleResource;
import entity.json.JsonResource;

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
        Map<String, Object> attr = null;
        String rollNumber, name, email, phone, address;
        int gender, status;
        long birthday, nowMls;
        try {
            attr = gson.fromJson((String) req.getAttribute("body"), JOSingleResource.class).getData().getAttributes();
            rollNumber = attr.get("rollNumber").toString();
            name = attr.get("name").toString();
            email = attr.get("email").toString();
            phone = attr.get("phone").toString();
            address = attr.get("address").toString();
            gender = (int) Math.floor((double) attr.get("gender"));
            System.out.println(attr.get("gender").getClass().getSimpleName());
            birthday = (long) Math.floor((double) attr.get("birthday"));
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().print(e);
            e.printStackTrace();
            return;
        }

        nowMls = System.currentTimeMillis();
        status = 1;

        Student s = new Student(nowMls, rollNumber, name, gender, email, phone, address, birthday, nowMls, nowMls, status);
        ofy().save().entity(s).now();
        JOSingleResource jsr = new JOSingleResource();
        jsr.setData(JsonResource.getInstance(s));
        resp.getWriter().print(gson.toJson(jsr));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path;
        if (req.getPathInfo() == null) path = "";
        else path = req.getPathInfo().substring(1);
        JsonObject jo;
        if (path.equals("")) {
            int limit = 10, page = 1, total;
            String temp;
            try {
                if ((temp = req.getParameter("page")) != null) page = Integer.parseInt(temp);
                if ((temp = req.getParameter("limit")) != null) limit = Integer.parseInt(temp);
            } catch (Exception e) {
                System.err.println("Failed when parse the parameter!");
            }

            List<Student> ls = ofy().load().type(Student.class).list();
            List<JsonResource> ljr = new ArrayList<>();
            for (Student s: ls) {
                ljr.add(JsonResource.getInstance(s));
            }

            HashMap<String, Object> meta = new HashMap<>();
            meta.put("limit", limit);
            meta.put("page", page);
            meta.put("total", ofy().load().type(Student.class).count());

            jo = new JOMultiResource();
            jo.setData(ljr);
            jo.setMeta(meta);
        }
        else {
            long id;
            try {
                id = Long.parseLong(path);
            } catch (Exception e) {
                resp.getWriter().print("Invalid id! Error when parse the id!");
                return;
            }

            Student s = ofy().load().type(Student.class).id(id).now();
            jo = new JOSingleResource();
            jo.setData(JsonResource.getInstance(s));
        }
        resp.getWriter().print(gson.toJson(jo));
    }
}
