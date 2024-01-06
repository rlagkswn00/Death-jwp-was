package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import util.HttpRequestUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class ListUserController extends AbstractController{
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String cookie = httpRequest.getHeader("Cookie");
        if (cookie == null) {
            httpResponse.forward("/user/login.html");
        }

        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookie);
        String logined = cookies.getOrDefault("logined", null);
        if (logined == null || Boolean.parseBoolean(logined)) {
            httpResponse.forwardBody("/user/login.html");
        }
        Collection<User> users = DataBase.findAll();

        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        for (User user : users) {
            sb.append("<tr>")
                    .append("<td>" + user.getUserId() + "</td>")
                    .append("<td>" + user.getName() + "</td>")
                    .append("<td>" + user.getEmail() + "</td>")
                    .append("</tr>");
        }
        sb.append("</table>");
        byte[] body = sb.toString().getBytes();
        httpResponse.forwardBody(body.toString());
    }
}