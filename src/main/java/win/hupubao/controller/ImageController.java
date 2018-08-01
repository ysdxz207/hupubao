package win.hupubao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.domain.Image;
import win.hupubao.service.ImageService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ImageController {
    private static final BASE64Decoder decoder = new BASE64Decoder();

    private static final Pattern pattern = Pattern.compile("(data:.*;base64,)");

    @Autowired
    private ImageService imageService;

    @RequestMapping("/image/{id}")
    public void image(HttpServletRequest request,
                      HttpServletResponse response,
                      @PathVariable(value="id") String id) {

        try (ServletOutputStream out = response.getOutputStream()){
            Image image = imageService.selectByPrimaryKey(id);

            String base64 = image.getBase64();
            Matcher matcher = pattern.matcher(base64);
            if (matcher.find()) {
                base64 = matcher.replaceAll("");
            }
            ByteArrayInputStream in = new ByteArrayInputStream(decoder.decodeBuffer(base64));
            BufferedImage bi = ImageIO.read(in);
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            LoggerUtils.error("[图片][{}]", id, e);
        }
    }

}
