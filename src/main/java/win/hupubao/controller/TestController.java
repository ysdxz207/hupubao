package win.hupubao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.hupubao.beans.biz.RoleBean;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.service.RoleService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("")
    public String test() {
        for (int i = 0; i < 10; i ++) {

            RoleBean roleBean = new RoleBean();
            roleBean.setCode("role" + i);
            roleBean.setRoleName("role" + i);
            roleService.edit(roleBean);

            LoggerUtils.error("roleTest error");
        }

        return "";
    }
}
