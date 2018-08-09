package win.hupubao;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import win.hupubao.common.http.Page;

public class Test {

    @org.junit.Test
    public void test() {
        JSONObject params = new JSONObject();
        params.put("uid", "18980622145");
        params.put("payType", "alipay");
        params.put("money", "0.01");
        params.put("signType", "RSA");
        Page.Response response = Page.create()
                .request("http://127.0.0.1:2336/port/pay/h5/pay",
                        params, Connection.Method.POST);

        System.out.println(response);
    }
}
