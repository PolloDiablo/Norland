package ca.casualt.norland;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.casualt.norland.exportformat.StatsBundle;
import ca.casualt.norland.shared.RequestAccess;

@SuppressWarnings("serial")
public class NorlandServerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, world");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String json = RequestAccess.getData(req);
        System.out.println(json);

        StatsBundle testData = RequestAccess.getData(req, StatsBundle.class);
        System.out.println(testData);
    }

    // private static class TestDataClass {
    // }

}
