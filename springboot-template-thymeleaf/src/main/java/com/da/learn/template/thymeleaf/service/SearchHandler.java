package com.da.learn.template.thymeleaf.service;

import com.da.learn.template.thymeleaf.bean.UploadFile;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
@Component
public class SearchHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private UploadFileService uploadFileService;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到消息：{}", msg);
        String message = msg.toString();
        if (!message.startsWith("UIM") || !message.endsWith("END") || message.length() < 25) {
            return;
        }
        String code = message.substring(3, message.indexOf("*"));
        String param = message.substring(message.indexOf("*") + 1, message.lastIndexOf("*"));

        String returnMsg = "";
        List<UploadFile> fileList = uploadFileService.search(param);
        if (ObjectUtils.isEmpty(fileList)) {
            returnMsg = "UIM" + code + "**END";
        } else {
            returnMsg = "UIM" + code + "*";
            for (UploadFile file : fileList) {
                returnMsg = returnMsg.concat(file.toText()).concat("\n");
            }
            returnMsg = returnMsg.concat("*END");
        }

        log.info("返回内容：{}", returnMsg);
        ctx.channel().writeAndFlush(returnMsg);
    }

//    public static void main(String[] args) {
//        String message = "UIM2020090316010347378*查询内容*END";
//        if (!message.startsWith("UIM") || !message.endsWith("END") || message.length() < 25) {
//            return;
//        }
//        String code = message.substring(3, message.indexOf("*"));
//        String param = message.substring(message.indexOf("*") + 1, message.lastIndexOf("*"));
//        System.out.println(code);
//        System.out.println(param);
//    }

}
