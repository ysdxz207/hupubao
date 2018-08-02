package win.hupubao.beans.biz;

import win.hupubao.domain.Category;
import win.hupubao.domain.Tag;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Table(name = "category")
public class CategoryBean extends Category {
	private static final long serialVersionUID = 1L;
}