package next.controller;

import core.db.DataBase;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ProfileController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);

        return "/users/profile.jsp";
    }
}
