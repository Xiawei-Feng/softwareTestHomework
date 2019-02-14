package com.aiops.controller;

import com.aiops.model.po.CorrelationConfirm;
import com.aiops.service.CorrConService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
  *  类{@code CorrConController} 规则确认控制层.
 *  
 * <p>主要包括查找规则列表、删除规则功能
 * 
 * @see CorrelationConfirm
 * @author YeYunpeng
 * @since 2018/12/30 
 */

@Controller
@RequestMapping("/CorrelationConfirm")
public class CorrConController 
{
	/** 绑定服务层 {@value} */
    @Resource
    private CorrConService corrConService;

    /**
	  *  查找规则列表
	 * 
	 *  <p>查找Apriori算法计算得到的所有规则
	 *  
	 * @param callback 前端回调函数
	 * @return 规则列表
	 */
    @RequestMapping(value = "/findAllList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    
    public String findAllList(
            @RequestParam(value = "callback", required = false) String callback) 
    {
        String result;
        Gson gson = new Gson();
        List<CorrelationConfirm> dto = corrConService.selectAll();
        result = gson.toJson(dto);
        if (callback != null)
        {
            return callback + "(" + result + ")";
        }
        else 
        {
            return result;
        }
    }
    
    /**
	  *  删除某些冗余规则
	 * 
	 * <p> 删除规则列表中人工选中为冗余的规则
	 * 
	 * @param ids 规则的id
	 * @param callback 前端回调函数
	 * @return 删除某些规则后的规则列表
	 */
    @RequestMapping(value = "/deleteSome", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody

    public String deleteSome(@RequestParam(value = "ids", required = false) String ids,
                             @RequestParam(value = "callback", required = false) String callback) 
    {
        String result;
        Gson gson = new Gson();
        if (ids == null || ids.equals("")) 
        {
            return null;
        }
        List<Integer> idList = gson.fromJson(ids, new TypeToken<List<Integer>>() 
        {
        }.getType());
        for (int i = 0;i<idList.size();i++)
        {
            System.out.println(idList.get(i));
            correlationConfirmService.deleteOne(idList.get(i));
        }
        int dto = idList.size();
        result = gson.toJson(dto);
        if (callback != null) 
        {
            return callback + "(" + result + ")";
        }
        else
        {
            return result;
        }
    }
}
