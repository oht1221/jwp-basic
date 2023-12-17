package next.controller;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {

  private final String forwardUrl;
  public ForwardController(String path) {
    this.forwardUrl = path;

    if (StringUtils.isEmpty(this.forwardUrl)) {
      throw new RuntimeException("invalid forward url");
    }
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return this.forwardUrl;
  }
}
