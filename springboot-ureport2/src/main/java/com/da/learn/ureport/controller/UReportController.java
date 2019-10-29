package com.da.learn.ureport.controller;

import com.bstek.ureport.Utils;
import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportConfigureImpl;
import com.bstek.ureport.export.ExportManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UReportController {

    @Resource(name = ExportManager.BEAN_ID)
    private ExportManager exportManager;

    @GetMapping("/ureport_test")
    public void getReportByParam(@RequestParam(required = false) Map<String, Object> parameters,
            HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;");
        if (parameters == null) {
            parameters = new HashMap<>();
        }

//        ExportManager exportManager = (ExportManager) Utils.getApplicationContext().getBean(ExportManager.BEAN_ID);
//        HtmlReport htmlReport = exportManager.exportHtml("file:demo.ureport.xml",request.getContextPath(),parameters);

        ExportConfigure configure = new ExportConfigureImpl("file:abc.ureport.xml", parameters, response.getOutputStream());
        exportManager.exportPdf(configure);


    }
}
