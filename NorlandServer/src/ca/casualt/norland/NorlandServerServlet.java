package ca.casualt.norland;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.casualt.norland.exportformat.StatsBundle;
import ca.casualt.norland.persistence.Operations;
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
        System.out.println("InputJSON: " + json);
        StatsBundle inputData = RequestAccess.getData(req, StatsBundle.class);
        if (inputData != null) {
            if (Operations.saveToDatabase(inputData)) {
                // Success case
                resp.getWriter().println("Saved to database.");
            } else {
                // DB Error case
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().println("Problem saving data.");
            }
        } else {
            // Error case.
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Problem with input data.");
        }
        System.out.println("InputData: " + inputData);
    }
}
