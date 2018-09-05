package win.hupubao.core.runner;

import com.alibaba.fastjson.serializer.SerializeConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import win.hupubao.utils.CustomDateTimeSerializer;

import java.util.Date;

/**
 *
 * @author W.feihong
 * @date 2018-07-26 00:11:40
 *
 */
@Component
@Order(value = 1)
public class AppRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var1) throws Exception{

        //序列化日期
        SerializeConfig.getGlobalInstance().put(Date.class, new CustomDateTimeSerializer());
    }
}