package ua.hillelit.lms.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * {@link CustomServlet} is the class for customization HttpServlet
 *
 * @author Dmytro Trotsenko on 2/5/23
 */
@Slf4j
public class CustomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("CustomServlet doGet() method is invoked");
        resp.setContentType("text/html");
        resp.setStatus(200);
        resp.getWriter().println("""
                <!DOCTYPE HTML>
                <html lang="en">
                    <head>
                        <meta charset="utf-8">
                        <title>Custom Servlet</title>
                    </head>
                    <body>
                        <h1 style="color: darkorange">Custom Servlet</h1>
                    </body>
                </html>
                """);
        resp.getWriter().println(LocalDateTime.now());
        resp.getWriter().close();
    }

}
