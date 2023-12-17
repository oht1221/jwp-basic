package next.controller;

import lombok.extern.slf4j.Slf4j;
import next.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
@Slf4j
public class DispatcherServlet extends HttpServlet {

  private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

  private static RequestMapping rm;

  @Override
  public void init() {
    rm = new RequestMapping();
    rm.initMapping();
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    Controller controller = rm.getController(request.getRequestURI());
    try {
      String viewName = controller.execute(request, response);
      move(viewName, request, response);
    } catch (Throwable e) {
      log.error("Exception: {}", e.getMessage());
      throw new ServletException(e.getMessage());
    }
  }

  private void move(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (url.startsWith(DEFAULT_REDIRECT_PREFIX)) {
      response.sendRedirect(url.substring(DEFAULT_REDIRECT_PREFIX.length()));
      return;
    }

    RequestDispatcher rd = request.getRequestDispatcher(url);
    rd.forward(request, response);
  }
}
