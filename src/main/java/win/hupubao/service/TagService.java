package win.hupubao.service;

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
    @Autowired
    private ArticleTagMapper articleTagMapper;

    public PageBean<Tag> selectTagList(Tag tag,
                                       PageBean<Tag> pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Tag> tagList = tagMapper.select(tag);
        pageBean.setList(tagList);
        pageBean.setTotal(tagMapper.selectCount(tag));
        return pageBean;
    }
}