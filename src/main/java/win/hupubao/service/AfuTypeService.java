package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.hupubao.beans.biz.AfuTypeBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.AfuTypeEditError;
import win.hupubao.mapper.AfuTypeMapper;

import java.util.List;

@Service
public class AfuTypeService {

    @Autowired
    private AfuTypeMapper afuTypeMapper;

    public PageBean<AfuTypeBean> selectAfuTypeList(AfuTypeBean afuType,
                                       PageBean<AfuTypeBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "create_time desc");
        List<AfuTypeBean> afuTypeList = afuTypeMapper.select(afuType);
        pageBean.setList(afuTypeList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }


    @Transactional(rollbackFor = Exception.class)
    public void edit(AfuTypeBean afuTypeBean) {
        int n;

        if (StringUtils.isEmpty(afuTypeBean.getId())) {
            n = afuTypeMapper.insertSelective(afuTypeBean);
        } else {
            n = afuTypeMapper.updateByPrimaryKeySelective(afuTypeBean);
        }

        if (n == 0) {
            Throws.throwError(AfuTypeEditError.AFU_TYPE_EDIT_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        afuTypeMapper.deleteByPrimaryKey(id);
    }
}