package com.da.learn.template.thymeleaf.test;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TestSendService {


    private String ip = "127.0.0.1";
    private Integer port = 8000;
    private Integer timeout = 5000;
    private String charset = "utf-8";

    public boolean send() {
        Socket socket = null;
        InputStream in = null;
        BufferedInputStream rcvStream = null;
        OutputStream out = null;
        BufferedOutputStream sendEntStream = null;
        ByteArrayOutputStream writer = null;
        try {
            socket = new Socket(ip, port);
            socket.setSoTimeout(timeout);
            log.info("连接短信服务器成功");
            out = socket.getOutputStream();
            sendEntStream = new BufferedOutputStream(out);
            byte[] sendBytes = getDataBytes();

            log.info("send to--> {}:{}, message: {}", ip, port, new String(sendBytes, charset));
            sendEntStream.write(sendBytes);
            sendEntStream.flush();

            in = socket.getInputStream();
            writer = new ByteArrayOutputStream();
            rcvStream = new BufferedInputStream(in);

            receiveEnt(rcvStream, writer);
            byte[] recBytes = writer.toByteArray();
            writer.close();

            String recBody = new String(recBytes, charset);
            log.info("Receive from--> {}:{}, body: {}", ip, port, recBody);

        } catch (Exception e) {
            log.error("发送异常", e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (rcvStream != null) {
                try {
                    rcvStream.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (sendEntStream != null) {
                try {
                    sendEntStream.close();
                } catch (IOException e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }


        return false;
    }

    private void receiveEnt(BufferedInputStream rcvStream,
                            ByteArrayOutputStream bytearray) throws Exception {


        byte[] buf = new byte[1024];
        while (true) {
            int readLen = rcvStream.read(buf);
            if (readLen > 0) {
                bytearray.write(buf, 0, readLen);
                bytearray.flush();
//                count += readLen;
            }
        }
    }


    private byte[] getDataBytes() throws Exception {
        String msg = "UIM2020090316010347378*文件*END";

        return msg.getBytes(StandardCharsets.UTF_8);
    }


    public static void main(String[] args) {
        TestSendService service = new TestSendService();
        service.send();
    }
}
