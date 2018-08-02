package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.hupubao.beans.biz.AfuBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.AfuEditError;
import win.hupubao.core.errors.ArticleEditError;
import win.hupubao.mapper.AfuMapper;

import java.util.List;

@Service
public class AfuService {

    @Autowired
    private AfuMapper afuMapper;

    public PageBean<AfuBean> selectAfuList(AfuBean afu,
                                       PageBean<AfuBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "create_time desc");
        List<AfuBean> afuList = afuMapper.selectList(afu);
        pageBean.setList(afuList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }


    @Transactional(rollbackFor = Exception.class)
    public void edit(AfuBean afuBean) {
        int n;

        if (StringUtils.isEmpty(afuBean.getId())) {
            n = afuMapper.insertSelective(afuBean);
        } else {
            n = afuMapper.updateByPrimaryKeySelective(afuBean);
        }

        if (n == 0) {
            Throws.throwError(AfuEditError.AFU_EDIT_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        afuMapper.deleteByPrimaryKey(id);
    }
}