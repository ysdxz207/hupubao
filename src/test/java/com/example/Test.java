package com.example;

import com.alibaba.fastjson.JSON;
import org.jsoup.Connection;
import win.hupubao.common.http.Page;

public class Test {

//    @org.junit.Test
    public void test() {
        Page.Response res = Page.create().request("http://dev-gateway.lamic.cn/tianfuPay/openIdAndUserIdNotify", JSON.parseObject(""), Connection.Method.POST);

        System.out.println(res);
    }
}
