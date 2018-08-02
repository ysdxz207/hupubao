package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.ArticleEditError;
import win.hupubao.domain.Tag;
import win.hupubao.mapper.ArticleTagMapper;
import win.hupubao.mapper.TagMapper;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public PageBean<TagBean> selectTagList(TagBean tag,
                                       PageBean<TagBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "create_time desc");
        List<TagBean> tagList = tagMapper.selectList(tag);
        pageBean.setList(tagList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }


    @Transactional(rollbackFor = Exception.class)
    public void edit(TagBean tagBean) {
        int n;

        if (StringUtils.isEmpty(tagBean.getId())) {
            n = tagMapper.insertSelective(tagBean);
        } else {
            n = tagMapper.updateByPrimaryKeySelective(tagBean);
        }

        if (n == 0) {
            Throws.throwError(ArticleEditError.ARTICLE_EDIT_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        tagMapper.deleteByPrimaryKey(id);
    }
}