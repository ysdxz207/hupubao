package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.domain.Tag;
import win.hupubao.mapper.ArticleTagMapper;
import win.hupubao.mapper.TagMapper;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public PageBean<Tag> selectTagList(Tag tag,
                                       PageBean<Tag> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "name desc");
        List<Tag> tagList = tagMapper.selectList(tag);
        pageBean.setList(tagList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }
}