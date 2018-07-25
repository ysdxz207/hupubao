package win.hupubao.service;

import com.alibaba.fastjson.JSON;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.Validatable;

public class BaseService {

    protected <T extends Validatable> T getEntity(RequestBean requestBean,
                                                  Class<T> clazz,
                                                  boolean validate) {
        T t = JSON.toJavaObject(JSON.parseObject(requestBean.getBizContent()), clazz);
        if (validate) {
            t.validate();
        }
        return t;
    }
}
