package com.da.learn.utils.csv;

import com.opencsv.bean.CsvBindByPosition;

public class Test {
    @CsvBindByPosition(required = false,position = 0)
    private String id;
    @CsvBindByPosition(required = false,position = 1)
    private String f1;
    @CsvBindByPosition(required = false,position = 2)
    private String f2;
    @CsvBindByPosition(required = false,position = 3)
    private String f3;
    @CsvBindByPosition(required = false,position = 4)
    private String f4;
    @CsvBindByPosition(required = false,position = 5)
    private String f5;
    @CsvBindByPosition(required = false,position = 6)
    private String f6;
    @CsvBindByPosition(required = false,position = 7)
    private String f7;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    public String getF5() {
        return f5;
    }

    public void setF5(String f5) {
        this.f5 = f5;
    }

    public String getF6() {
        return f6;
    }

    public void setF6(String f6) {
        this.f6 = f6;
    }

    public String getF7() {
        return f7;
    }

    public void setF7(String f7) {
        this.f7 = f7;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", f1='" + f1 + '\'' +
                ", f2='" + f2 + '\'' +
                ", f3='" + f3 + '\'' +
                ", f4='" + f4 + '\'' +
                ", f5='" + f5 + '\'' +
                ", f6='" + f6 + '\'' +
                ", f7='" + f7 + '\'' +
                '}';
    }
}
