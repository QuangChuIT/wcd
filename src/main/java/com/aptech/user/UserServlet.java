package com.aptech.user;


import com.aptech.common.BaseResponse;
import com.aptech.common.BaseResponseBuilder;
import com.aptech.user.payload.CreateUserReq;
import com.aptech.user.payload.UpdateUserReq;
import com.aptech.utils.AESUtil;
import com.aptech.utils.JSONConverter;
import com.aptech.utils.UserStatus;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "UserServlet", urlPatterns = {"/admin/users"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50

)
public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            this.doGetList(req, resp);
        } else if (action.equals("edit")) {
            this.showFormEdit(req, resp);
        } else if (action.equals("update")) {
            this.doUpdate(req, resp);
        } else if (action.equals("delete")) {
            this.doDelete(req, resp);
        } else if (action.equals("list")) {
            this.doGetUsers(req, resp);
        } else if (action.equals("getList")) {
            this.doGetAllUsers(req, resp);
        } else if (action.equals("create")) {
            this.doCreateUser(req, resp);
        } else if (action.equals("detail")) {
            this.doGetUserDetail(req, resp);
        }
    }

    protected void doGetUserDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long userId = Long.parseLong(request.getParameter("userId"));
        String request_id = UUID.randomUUID().toString();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Optional<User> user = UserDao.getInstance().getById(userId);
        BaseResponse<Object> resp;
        resp = user.<BaseResponse<Object>>map(value -> BaseResponseBuilder.of(value, request_id, null, BaseResponseBuilder.CODE_OK))
                .orElseGet(() -> BaseResponseBuilder.of(null, request_id, null, "WCD-00000404"));
        out.println(gson.toJson(resp));
        out.flush();
    }

    protected void doCreateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String request_id = UUID.randomUUID().toString();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        BaseResponse<Object> resp;
        try {
            String jsonReq = JSONConverter.readJson(request);
            CreateUserReq createUserReq = gson.fromJson(jsonReq, CreateUserReq.class);
            User user = new User();
            user.setUsername(createUserReq.getUsername());
            user.setPassword(AESUtil.encrypt(createUserReq.getPassword()));
            user.setMobile(createUserReq.getMobile());
            user.setEmail(createUserReq.getEmail());
            user.setName(createUserReq.getName());
            Date now = new Date();
            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user.setLoginFailCount(0);
            user.setStatus(UserStatus.ACTIVE.getValue());
            UserDao.getInstance().create(user);
            resp = BaseResponseBuilder.of(null, request_id, null, BaseResponseBuilder.CODE_OK);
        } catch (Exception e) {
            resp = BaseResponseBuilder.of(null, request_id, e.getMessage(), BaseResponseBuilder.CODE_INTERNAL_SERVER_ERROR);
        }
        out.println(gson.toJson(resp));
        out.flush();
    }

    protected void doGetAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = UserDao.getInstance().getAll();
        PrintWriter out = response.getWriter();
        String json = gson.toJson(users);
        System.out.println(json);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        out.println(json);
        out.flush();
    }

    protected void doGetList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = UserDao.getInstance().getAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/user/users.jsp").forward(request, response);
    }

    protected void doGetUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String request_id = UUID.randomUUID().toString();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            List<User> users = UserDao.getInstance().getAll();
            BaseResponse<List<User>> resp = BaseResponseBuilder.of(users, request_id, null, BaseResponseBuilder.CODE_OK);
            String json = gson.toJson(resp);
            out.println(json);
            out.flush();
        } catch (Exception e) {
            BaseResponse<Object> resp = BaseResponseBuilder.of(null, e.getMessage(), BaseResponseBuilder.CODE_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson(resp));
            out.flush();
        }

    }

    protected void showFormEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        long userId = Long.parseLong(id);
        Optional<User> user = UserDao.getInstance().getById(userId);
        if (user.isPresent()) {
            request.setAttribute("user", user.get());
            request.getRequestDispatcher("/views/user/edit.jsp").forward(request, response);
        } else {
            this.doGetList(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = JSONConverter.readJson(req);
        LOGGER.info("Request delete " + body);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        String request_id = UUID.randomUUID().toString();
        BaseResponse<Object> response;
        try {
            if (body != null && !body.equals("")) {
                JSONObject jsonObject = new JSONObject(body);
                LOGGER.info("Request delete " + jsonObject);
                JSONArray userIds = jsonObject.getJSONArray("userIds");
                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < userIds.length(); i++) {
                    ids.add(userIds.getLong(i));
                }
                UserDao.getInstance().deleteUsers(ids);
                response = BaseResponseBuilder.of(null, request_id, null, BaseResponseBuilder.CODE_OK);
            } else {
                response = BaseResponseBuilder.of(null, request_id, "Invalid user id", BaseResponseBuilder.CODE_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response = BaseResponseBuilder.of(null, e.getMessage(), BaseResponseBuilder.CODE_INTERNAL_SERVER_ERROR);
        }
        out.println(gson.toJson(response));
        out.flush();
    }

    public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String request_id = UUID.randomUUID().toString();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        BaseResponse<Object> resp;
        try {
            String jsonReq = JSONConverter.readJson(request);
            UpdateUserReq req = gson.fromJson(jsonReq, UpdateUserReq.class);
            Long userId = req.getId();
            User user = UserDao.getInstance().getById(userId).get();
            user.setName(req.getName());
            user.setStatus(req.getStatus());
            user.setEmail(req.getEmail());
            user.setMobile(req.getMobile());
            UserDao.getInstance().update(user);
            resp = BaseResponseBuilder.of(null, request_id, null, BaseResponseBuilder.CODE_OK);
        } catch (Exception e) {
            resp = BaseResponseBuilder.of(null, request_id, e.getMessage(), BaseResponseBuilder.CODE_INTERNAL_SERVER_ERROR);
        }
        out.println(gson.toJson(resp));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private final static Logger LOGGER = LogManager.getLogger(JSONConverter.class);
}
