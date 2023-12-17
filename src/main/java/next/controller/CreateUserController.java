package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import core.db.DataBase;
import next.model.User;

@Slf4j
public class CreateUserController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
                request.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);

        return "redirect:/";
    }
}
