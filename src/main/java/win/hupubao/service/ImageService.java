package win.hupubao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.ImageError;
import win.hupubao.domain.Image;
import win.hupubao.mapper.ImageMapper;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private ImageMapper imageMapper;

    public void upload(Image image) {
        if (StringUtils.isBlank(image.getBase64())) {
            Throws.throwError(SystemError.PARAMETER_ERROR,
                    "Parameter [base64] should not be null.");
        }

        if (StringUtils.isBlank(image.getName())) {
            Throws.throwError(SystemError.PARAMETER_ERROR,
                    "Parameter [name] should not be null.");
        }

        //生成访问链接


        image.setCreateDate(System.currentTimeMillis());
        if (imageMapper.insertSelective(image) == 0) {
            Throws.throwError(ImageError.IMAGE_UPLOAD_ERROR);
        }
    }

    public void delete(String id) {
        if (imageMapper.deleteByPrimaryKey(id) == 0) {
            Throws.throwError(ImageError.IMAGE_DELETE_ERROR);
        }
    }

    public void deleteByUrl(String url) {
        Example example = new Example(Image.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("url", url);
        Image image = imageMapper.selectOneByExample(example);
        if (image == null) {
            Throws.throwError(ImageError.IMAGE_NOT_EXISTS_ERROR);
        }
        if (imageMapper.delete(image) == 0) {
            Throws.throwError(ImageError.IMAGE_DELETE_ERROR);
        }
    }

    public PageBean<Image> selectByPage(Image image, PageBean<Image> pageBean) {

        return imageMapper.selectByPage(image);
    }

    public Image selectByPrimaryKey(String id) {
        return imageMapper.selectByPrimaryKey(id);
    }
}