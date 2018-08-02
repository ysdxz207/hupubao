package win.hupubao.domain;

import com.alibaba.fastjson.annotation.JSONField;
import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;
import win.hupubao.utils.CustomDateTimeSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String name;
	private String base64;
	private String url;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Long createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}