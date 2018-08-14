package win.hupubao.mapper.hupubao;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.domain.Image;
import win.hupubao.utils.mybatis.MyMapper;

@Repository
public interface ImageMapper extends MyMapper<Image> {


    PageBean<Image> selectByPage(Image image);
}
