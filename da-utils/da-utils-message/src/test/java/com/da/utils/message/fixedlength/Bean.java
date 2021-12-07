package com.da.utils.message.fixedlength;

import java.util.List;

public class Bean {

    @Header
    private BeanHeader beanHeader;
    @Body
    private List<BeanBody> beanBody;

    public Bean() {
    }

    public Bean(BeanHeader beanHeader, List<BeanBody> beanBody) {
        this.beanHeader = beanHeader;
        this.beanBody = beanBody;
    }

    public BeanHeader getBeanHeader() {
        return beanHeader;
    }

    public void setBeanHeader(BeanHeader beanHeader) {
        this.beanHeader = beanHeader;
    }

    public List<BeanBody> getBeanBody() {
        return beanBody;
    }

    public void setBeanBody(List<BeanBody> beanBody) {
        this.beanBody = beanBody;
    }

    public static class BeanHeader {
        @MsgField(length = 5, position = 0)
        private String header1;
        @MsgField(length = 7, position = 1, fillSide = FillSide.LEFT, fillChar = '^')
        private String header2;
        @MsgField(length = 10, position = 2, fillChar = '|')
        private String header3;

        public BeanHeader(String header1, String header2, String header3) {
            this.header1 = header1;
            this.header2 = header2;
            this.header3 = header3;
        }

        public BeanHeader() {
        }

        public String getHeader1() {
            return header1;
        }

        public void setHeader1(String header1) {
            this.header1 = header1;
        }

        public String getHeader2() {
            return header2;
        }

        public void setHeader2(String header2) {
            this.header2 = header2;
        }

        public String getHeader3() {
            return header3;
        }

        public void setHeader3(String header3) {
            this.header3 = header3;
        }
    }

    public static class BeanBody {
        @MsgField(length = 10, position = 0)
        private String body1;
        @MsgField(length = 7, position = 1, fillSide = FillSide.LEFT, fillChar = '^')
        private String body2;
        @MsgField(length = 5, position = 2, fillChar = '|')
        private String body3;

        public BeanBody(String body1, String body2, String body3) {
            this.body1 = body1;
            this.body2 = body2;
            this.body3 = body3;
        }

        public BeanBody() {
        }

        public String getBody1() {
            return body1;
        }

        public void setBody1(String body1) {
            this.body1 = body1;
        }

        public String getBody2() {
            return body2;
        }

        public void setBody2(String body2) {
            this.body2 = body2;
        }

        public String getBody3() {
            return body3;
        }

        public void setBody3(String body3) {
            this.body3 = body3;
        }
    }
}
