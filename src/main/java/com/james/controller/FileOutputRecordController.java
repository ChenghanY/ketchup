package com.james.controller;

import com.james.db.mapper.FileOutputRecordMapper;
import com.james.db.po.FileOutputRecordPO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class FileOutputRecordController {

    @Resource
    FileOutputRecordMapper fileOutputRecordMapper;

    @ResponseBody
    @GetMapping("/test")
    public FileOutputRecordPO test() {
        return fileOutputRecordMapper.queryOneById(1L);
    }
}
