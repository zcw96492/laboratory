package com.newcore.laboratory.utils;

import com.newcore.laboratory.utils.enumclass.BusinessExceptionCodeEnum;
import com.newcore.laboratory.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码生成工具类
 * @author zhouchaowei
 */
public class VerificationCodeGeneratorUtils {

    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeGeneratorUtils.class);

    /**
     * 生成图片验证码
     * @param type 验证码类型  1.纯数字   2.纯字母   3.字母 + 数字   4.汉字
     * @param length 验证码字符长度
     */
    public static void generatePicVerificationCode(int type, int length, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        OutputStream os = response.getOutputStream();

        /** 返回验证码和图片的map */
        Map<String,Object> returnMap = new HashMap<String, Object>();

        switch (type){
            case 1:
                logger.info("生成纯数字验证码");
                returnMap = generatePureFigure(length,86, 37, os,returnMap);
                break;
            case 2:
                logger.info("生成纯字母验证码");
                returnMap = generatePureLetter(length,86, 37, os,returnMap);
                break;
            case 3:
                logger.info("生成字母数字混合验证码");
                returnMap = generateFigureAndLetter(length,86, 37, os,returnMap);
                break;
            case 4:
                logger.info("生成汉字验证码");
                returnMap = generatePureChineseCharacters(length,86, 37, os,returnMap);
                break;
            default:
                logger.info("无匹配验证码类型 || 将默认使用字母数字混合验证码");
                break;
        }

        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, returnMap.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", DateUtils.getCurrentDateTime());

        try {
            ImageIO.write((BufferedImage) returnMap.get("image"), "jpg", os);
        } catch (IOException e) {
            logger.info(BusinessExceptionCodeEnum.F0323.getMessage(),e);
            throw new BusinessException(BusinessExceptionCodeEnum.F0323.getCode(),BusinessExceptionCodeEnum.F0323.getMessage());
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

    /**
     * 生成纯数字的验证码
     * @param length 验证码字符长度
     * @param width 验证码图片宽度
     * @param height 验证码图片高度
     * @param outputStream 验证码图片输出流
     * @param map
     * @return
     */
    private static Map<String, Object> generatePureFigure(int length, int width, int height, OutputStream outputStream, Map<String, Object> map){
        if (width <= 0 || height <= 0) {
            width = 60;
            height = 20;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** 获取图形上下文 */
        Graphics graphics = image.getGraphics();
        /** 生成随机类 */
        Random random = new Random();
        /** 设定背景色 */
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        /** 设定字体 */
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        /** 随机产生168条干扰线，使图象中的认证码不易被其它程序探测到 */
        graphics.setColor(getRandColor(160, 200));

        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        /** 取随机产生的码 */
        String strEnsure = "";
        char[] pureFigureTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        /** length代表验证码字符的位数,如果要生成更多位的认证码,则加大length的数值 */
        for (int i = 0; i < length; ++i) {
            strEnsure += pureFigureTable[(int) (pureFigureTable.length * Math.random())];
            /** 将认证码显示到图象中 */
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            /** 直接生成 */
            String str = strEnsure.substring(i, i + 1);
            /** 设置随便码在背景图图片上的位置 */
            graphics.drawString(str, 13 * i + 20, 25);
        }

        /** 释放图形上下文 */
        graphics.dispose();
        map.put("image",image);
        map.put("strEnsure",strEnsure);
        return map;
    }

    /**
     * 生成纯字母的验证码
     * @param length 验证码字符长度
     * @param width 验证码图片宽度
     * @param height 验证码图片高度
     * @param outputStream 验证码图片输出流
     * @return
     */
    private static Map<String, Object> generatePureLetter(int length, int width, int height, OutputStream outputStream, Map<String, Object> map){
        if (width <= 0 || height <= 0) {
            width = 60;
            height = 20;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** 获取图形上下文 */
        Graphics graphics = image.getGraphics();
        /** 生成随机类 */
        Random random = new Random();
        /** 设定背景色 */
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        /** 设定字体 */
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        /** 随机产生168条干扰线，使图象中的认证码不易被其它程序探测到 */
        graphics.setColor(getRandColor(160, 200));

        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        /** 取随机产生的码 */
        String strEnsure = "";
        char[] pureLetterTable = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        /** length代表验证码字符的位数,如果要生成更多位的认证码,则加大length的数值 */
        for (int i = 0; i < length; ++i) {
            strEnsure += pureLetterTable[(int) (pureLetterTable.length * Math.random())];
            /** 将认证码显示到图象中 */
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            /** 直接生成 */
            String str = strEnsure.substring(i, i + 1);
            /** 设置随便码在背景图图片上的位置 */
            graphics.drawString(str, 13 * i + 20, 25);
        }

        /** 释放图形上下文 */
        graphics.dispose();
        map.put("image",image);
        map.put("strEnsure",strEnsure);
        return map;
    }

    /**
     * 生成数字字母混合的验证码
     * @param length 验证码字符长度
     * @param width 验证码图片宽度
     * @param height 验证码图片高度
     * @param outputStream 验证码图片输出流
     * @return
     */
    private static Map<String, Object> generateFigureAndLetter(int length, int width, int height, OutputStream outputStream, Map<String, Object> map){
        if (width <= 0 || height <= 0) {
            width = 60;
            height = 20;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** 获取图形上下文 */
        Graphics graphics = image.getGraphics();
        /** 生成随机类 */
        Random random = new Random();
        /** 设定背景色 */
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        /** 设定字体 */
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        /** 随机产生168条干扰线，使图象中的认证码不易被其它程序探测到 */
        graphics.setColor(getRandColor(160, 200));

        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        /** 取随机产生的码 */
        String strEnsure = "";
        char[] pureLetterTable = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                '0','1','2','3','4','5','6','7','8','9'};
        /** length代表验证码字符的位数,如果要生成更多位的认证码,则加大length的数值 */
        for (int i = 0; i < length; ++i) {
            strEnsure += pureLetterTable[(int) (pureLetterTable.length * Math.random())];
            /** 将认证码显示到图象中 */
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            /** 直接生成 */
            String str = strEnsure.substring(i, i + 1);
            /** 设置随便码在背景图图片上的位置 */
            graphics.drawString(str, 13 * i + 20, 25);
        }

        /** 释放图形上下文 */
        graphics.dispose();
        map.put("image",image);
        map.put("strEnsure",strEnsure);
        return map;
    }

    /**
     * 生成纯汉字的验证码
     * @param length 验证码字符长度
     * @param width 验证码图片宽度
     * @param height 验证码图片高度
     * @param outputStream 验证码图片输出流
     */
    private static Map<String, Object> generatePureChineseCharacters(int length, int width, int height, OutputStream outputStream, Map<String, Object> map){
        if (width <= 0 || height <= 0) {
            width = 60;
            height = 20;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** 获取图形上下文 */
        Graphics graphics = image.getGraphics();
        /** 生成随机类 */
        Random random = new Random();
        /** 设定背景色 */
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        /** 设定字体 */
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        /** 随机产生168条干扰线，使图象中的认证码不易被其它程序探测到 */
        graphics.setColor(getRandColor(168, 200));

        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        /** 取随机产生的码 */
        String strEnsure = "";
        char[] pureLetterTable = {'爱','购','时','尚','电','子','商','城'};
        /** length代表验证码字符的位数,如果要生成更多位的认证码,则加大length的数值 */
        for (int i = 0; i < length; ++i) {
            strEnsure += pureLetterTable[(int) (pureLetterTable.length * Math.random())];
            /** 将认证码显示到图象中 */
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            /** 直接生成 */
            String str = strEnsure.substring(i, i + 1);
            /** 设置随便码在背景图图片上的位置 */
            graphics.drawString(str, 13 * i + 20, 25);
        }

        /** 释放图形上下文 */
        graphics.dispose();
        map.put("image",image);
        map.put("strEnsure",strEnsure);
        return map;
    }

    /**
     * 给定范围获得随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255){
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
