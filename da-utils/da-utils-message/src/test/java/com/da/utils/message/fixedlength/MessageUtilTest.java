package com.da.utils.message.fixedlength;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MessageUtilTest {

    @Test
    public void testBeanToMessageAllInOne() {
        Bean.BeanHeader header = new Bean.BeanHeader("h1", "h2", "h3");
        Bean.BeanBody body1 = new Bean.BeanBody("b1", "b2", "b3");
        Bean.BeanBody body2 = new Bean.BeanBody("b1", "b2", "b3");
        List<Bean.BeanBody> bodyList = new ArrayList<>();
        bodyList.add(body1);
        bodyList.add(body2);
        Bean bean = new Bean(header, bodyList);
        String s = MessageUtil.beanToMessage(bean, "utf-8", MessageUtil.ALL_IN_ONE_LINE_SEPARATOR);
        Assert.assertEquals(s, "h1   ^^^^^h2h3||||||||b1        ^^^^^b2b3|||b1        ^^^^^b2b3|||");
    }

    @Test
    public void testMessageToBeanAllInOne() {
        String s = "h1   ^^^^^h2h3||||||||b1        ^^^^^b2b3|||b1        ^^^^^b2b3|||";
        Bean bean = MessageUtil.messageToBean(s, Bean.class, Bean.BeanBody.class, "utf-8", true);
        Assert.assertEquals(bean.getBeanHeader().getHeader1(), "h1");
        Assert.assertEquals(bean.getBeanHeader().getHeader2(), "h2");
        Assert.assertEquals(bean.getBeanHeader().getHeader3(), "h3");
        Assert.assertEquals(bean.getBeanBody().get(0).getBody1(), "b1");
        Assert.assertEquals(bean.getBeanBody().get(0).getBody2(), "b2");
        Assert.assertEquals(bean.getBeanBody().get(0).getBody3(), "b3");
        Assert.assertEquals(bean.getBeanBody().get(1).getBody1(), "b1");
        Assert.assertEquals(bean.getBeanBody().get(1).getBody2(), "b2");
        Assert.assertEquals(bean.getBeanBody().get(1).getBody3(), "b3");
    }

    @Test
    public void testBeanToMessageNotOneLine() {
        Bean.BeanHeader header = new Bean.BeanHeader("h1", "h2", "h3");
        Bean.BeanBody body1 = new Bean.BeanBody("b1", "b2", "b3");
        Bean.BeanBody body2 = new Bean.BeanBody("b1", "b2", "b3");
        List<Bean.BeanBody> bodyList = new ArrayList<>();
        bodyList.add(body1);
        bodyList.add(body2);
        Bean bean = new Bean(header, bodyList);
        String s = MessageUtil.beanToMessage(bean, "utf-8", MessageUtil.DEFAULT_LINE_SEPARATOR);
        Assert.assertEquals(s, "h1   ^^^^^h2h3||||||||\r\n" +
                "b1        ^^^^^b2b3|||\r\n" +
                "b1        ^^^^^b2b3|||\r\n");
    }

    @Test
    public void testMessageToBeanNotOneLine() {
        String s = "h1   ^^^^^h2h3||||||||\r\n" +
                "b1        ^^^^^b2b3|||\r\n" +
                "b1        ^^^^^b2b3|||\r\n";
        Bean bean = MessageUtil.messageToBean(s, Bean.class, Bean.BeanBody.class, "utf-8", false);
        Assert.assertEquals(bean.getBeanHeader().getHeader1(), "h1");
        Assert.assertEquals(bean.getBeanHeader().getHeader2(), "h2");
        Assert.assertEquals(bean.getBeanHeader().getHeader3(), "h3");
        Assert.assertEquals(bean.getBeanBody().get(0).getBody1(), "b1");
        Assert.assertEquals(bean.getBeanBody().get(0).getBody2(), "b2");
        Assert.assertEquals(bean.getBeanBody().get(0).getBody3(), "b3");
        Assert.assertEquals(bean.getBeanBody().get(1).getBody1(), "b1");
        Assert.assertEquals(bean.getBeanBody().get(1).getBody2(), "b2");
        Assert.assertEquals(bean.getBeanBody().get(1).getBody3(), "b3");
    }

}