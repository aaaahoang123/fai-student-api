package controller;


import abstracts.ErrorObject;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;
import entity.error.EOSingleResource;
import entity.error.ErrorResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UploadServlet extends HttpServlet {
    BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
    Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(bs.createUploadUrl("/api/upload"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlobKey bk = bs.getUploads(req).get("myImg").get(0);
        if (bk == null) {
            ErrorObject<ErrorResource> err = new EOSingleResource();
            err.setErrors(ErrorResource.getInstance("500", "Server Error!", "Can not upload file!"));
            resp.getWriter().print(gson.toJson(err));
            return;
        }
        String result = req.getRequestURL().toString().replace(req.getRequestURI(), "/api/show-file/") + bk.getKeyString();
        resp.getWriter().write(result);
    }
}
