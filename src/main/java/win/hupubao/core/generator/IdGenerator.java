package win.hupubao.core.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import tk.mybatis.mapper.genid.GenId;

import java.util.Date;

public class IdGenerator implements GenId<String> {
    @Override
    public String genId(String s, String s1) {
        return DateFormatUtils.format(new Date(),
                "yyyyMMddHHmmssSSS")
                + RandomStringUtils.randomNumeric(2)
                + System.currentTimeMillis();
    }
}
