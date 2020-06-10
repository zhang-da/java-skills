package com.da.learn.javafx;

import de.felixroske.jfxsupport.SplashScreen;

/**
 * 启动界面
 * @author panmingzhi
 */
public class CustomSplash extends SplashScreen {
    @Override
    public boolean visible() {
        return super.visible();
    }

    @Override
    public String getImagePath() {
        return "/image/banner.jpg";
    }
}
