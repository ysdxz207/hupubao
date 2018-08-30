package win.hupubao;

import com.alibaba.fastjson.JSON;
import org.jsoup.Connection;
import win.hupubao.common.http.Page;
import win.hupubao.common.utils.rsa.RSA;

import java.util.HashMap;
import java.util.Map;

public class TestAfu {
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIa8F8Dnb6sNqYh2I/HoiJx1ubJAUTINqoZBocaZUzn5rxV+/P5qPP1dlRfP0/ArCt5RhfLSzgyyF0eZ+aDNqqFhUao474mOEi9hGY9pBJbzsWbkQCbLJAlE4DWBthaumaKdFwPCnXtuQ5+ngBHXauh5RaJm2RrWS2dynwNZ6IKhAgMBAAECgYAveuNICIbymZrvyuo52n8h2963sTCr2eMI8uIGsYuBt7p6ccjIAzpA8xSSUpvWW+S1mbcBbkZMdQn9Ioa0oyWIvHDpjBsJR47pSszyPryNYISPikp/YC2iwtPIG175ZIlwALoQWZqJXD8Wh6I9MXA30prJ+OES1980fjrex+fRUQJBALxrAQ1alGuwrcvgVal3ZOiFtqObkMKGQmJTOk9LAM7wugC2qa5hzglD9jluKlsOpj2kqqQ2FDMX92NFH9DDbp0CQQC3D8NJFS42smoqCxzo1UFq0h+11ytFGBMTYjemo9SUVJTrf3RdSxXb0jedqK34mopc2pF/OwWQGf6SC9ShHULVAkEAuY3zmlATDL8RWU+TbPJM6QUN6a/BbyDDbYujSAWIRUQjGaLKFhbxzoQkj0hj5ArYQqrQ9ijf2bWHhzbC71gXrQJAC0bAS88vMpVY7tASbtE8H11kMKpZcv8hpa8GKJCs9pojNkJ6o4iNhyXy4RZT6R1z6nZYQhMbq52+TbbG2UnZJQJABi9Ay/uKjzLS0IoWfVTCZBkWhk6OooLlaWlkKNhgO40f3a5fxxPBMJqqWOlHuG2x44o9xFV/TK2pSfAgc+dokQ==";
    private static final String URL = "http://127.0.0.1:2015";

    @org.junit.Test
    public void testAfuList() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> bizContent = new HashMap<>();

        bizContent.put("type", "378130438620708864");

        params.put("service", "api.afu.list");
        params.put("bizContent", JSON.toJSONString(bizContent));
        params.put("signType","RSA");
        params.put("sign", RSA.getInstance().sign(PRIVATE_KEY, bizContent, RSA.SignType.RSA));

        Page.Response response = Page.create().request(URL, params, Connection.Method.POST);

        System.out.println(response.bodyToJson(true));
    }

    @org.junit.Test
    public void testAfuEdit() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> bizContent = new HashMap<>();

        bizContent.put("type", "378130438620708864");
        bizContent.put("name", "afu teest1");
        bizContent.put("content", "afu teest contetn");

        params.put("service", "api.afu.edit");
        params.put("bizContent", JSON.toJSONString(bizContent));
        params.put("signType","RSA");
        params.put("sign", RSA.getInstance().sign(PRIVATE_KEY, bizContent, RSA.SignType.RSA));

        Page.Response response = Page.create().request(URL, params, Connection.Method.POST);

        System.out.println(response.bodyToJson(true));
    }
}
