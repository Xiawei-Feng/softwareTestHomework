package com.aiops.controller;

import com.aiops.model.po.Correlation;
import com.aiops.model.po.CorrelationConfirm;
import com.aiops.service.CorrelationConfirmService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/CorrelationConfirm")
public class CorrelationConfirmController {
    @Resource
    private CorrelationConfirmService correlationConfirmService;

    @RequestMapping(value = "/findAllList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAllList(
            @RequestParam(value = "callback", required = false) String callback) {
        String result;
        Gson gson = new Gson();
        List<CorrelationConfirm> dto = correlationConfirmService.selectAll();
        result = gson.toJson(dto);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }

    @RequestMapping(value = "/deleteSome", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteSome(@RequestParam(value = "ids", required = false) String ids,
                             @RequestParam(value = "callback", required = false) String callback) {
        String result;
        Gson gson = new Gson();
        if (ids == null || ids.equals("")) {
            return null;
        }
        List<Integer> idList = gson.fromJson(ids, new TypeToken<List<Integer>>() {
        }.getType());
        for (int i = 0;i<idList.size();i++){
            System.out.println(idList.get(i));
            correlationConfirmService.deleteOne(idList.get(i));
        }
        int dto = idList.size();
        result = gson.toJson(dto);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }
}
