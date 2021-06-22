package com.da.learn.template.thymeleaf.controller;

import com.da.learn.template.thymeleaf.bean.UploadFile;
import com.da.learn.template.thymeleaf.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 跳转到搜索页面
     */
    @GetMapping("")
    public String toSearch(Model model) {
        return "search/search";
    }


    /**
     * 跳转到结果页面
     */
    @GetMapping("/result")
    public String toResult(Model model) {
        return "search/result";
    }

    /**
     * 跳转到上传页面
     */
    @GetMapping("/upload")
    public String toUpload(Model model) {
        return "search/upload";
    }


    /**
     * 保存
     *
     * @param uploadFile
     * @param modelMap
     * @return
     */
    @PostMapping("/upload/save")
    public String uploadSave(UploadFile uploadFile, ModelMap modelMap) {
        uploadFileService.save(uploadFile);
        modelMap.addAttribute("errorMsg", "success");
        return "search/upload";
    }

    @PostMapping("doSearch")
    public String doSearch(String param, ModelMap modelMap) {
        modelMap.addAttribute("results", uploadFileService.searchHighlight(param));
        return "search/result";
    }
}
