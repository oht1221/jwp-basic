package next;

import next.controller.*;

import java.util.Map;

public class RequestMapping {
  private Map<String, Controller> requestMap;

  public void initMapping() {
    requestMap.put("/", new HomeController());
    requestMap.put("/users/form", new ForwardController("/user/form.jsp"));
    requestMap.put("/users/create", new CreateUserController());
    requestMap.put("/users/updateForm", new UpdateUserFormController());
    requestMap.put("/users/update", new UpdateUserController());
    requestMap.put("/users/profile", new ProfileController());
    requestMap.put("/users/logout", new LogoutController());
    requestMap.put("/users", new ListUserController());
    requestMap.put("/users/loginForm", new ForwardController("/user/loginForm.jsp"));
    requestMap.put("/users/login", new LoginController());
  }

  public Controller getController(String url) {
    return requestMap.get(url);
  }
}
