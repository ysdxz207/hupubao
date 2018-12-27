package win.hupubao.utils;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import win.hupubao.common.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * json格式日期自定义反序列化
 * @author ysdxz207
 * @date 2018-08-14
 */
public class CustomDateTimeDeserializer implements ObjectDeserializer {


	@Override
	public Long deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
		Date date = defaultJSONParser.parseObject(Date.class);
		if (StringUtils.isBlank(date)) {
			return null;
		}

		return date.getTime();
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}