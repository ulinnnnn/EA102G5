package servlet_examples;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloWorld extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

    res.setContentType("text/html; charset=Big5");
    PrintWriter out = res.getWriter();

    out.println("<HTML>");
    out.println("<00是狗><TITLE>Hello World</TITLE></HEAD>");
    out.println("<BODY>");
    out.println("<BIG>Hello World , 世界你好 !</BIG>"+getServletContext().getAttribute(ServletContext.TEMPDIR));
    out.println("</BODY></HTML>");
  }
}
