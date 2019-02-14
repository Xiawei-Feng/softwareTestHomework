package com.aiops.controller;

import com.aiops.model.dto.BPCAggregationDTO;
import com.aiops.model.dto.BPCAlarmTimeDTO;
import com.aiops.model.dto.BPCListDTO;
import com.aiops.model.dto.BPCWarningDTO;
import com.aiops.model.po.BPCSource;
import com.aiops.model.vo.IdNameVO;
import com.aiops.service.BPCService;
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
  *  类{@code BPCController} BPC表格控制层
 * 
 * <p>主要包括获取BPC数据、删除某条数据、按触发时间查找、按告警时间查找、按事件来源查找、按告警事件查找等功能
 *
 * @author YeYunpeng
 * @since 2018/12/10
 */
@Controller
@RequestMapping("/BPC")
public class BPCController 
{
	/**绑定BPC服务层 {@value}*/
    @Resource
    private BPCService bpcService;

    /**
	  * 显示BPC数据
	 * 
	 * <p>显示符合筛选条件的所有BPC数据
	 * 
	 * @param page 当前页数
	 * @param source 事件来源
	 * @param event 事件类型
	 * @param isAsc 是否升序
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param callback 前端回调函数
	 * @return bpc数据
	 */
    @RequestMapping(value = "/getAllList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    
    public String getAllList(@RequestParam("page") int page,
                             @RequestParam(value = "source", required = false) Integer source,
                             @RequestParam(value = "event", required = false) Integer event,
                             @RequestParam(value = "isAsc", required = false) Boolean isAsc,
                             @RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "callback", required = false) String callback) 
    {
        if (startDate != null && startDate.equals("")) 
        {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) 
        {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        // 默认是降序
        if (isAsc == null) 
        {
            isAsc = false;
        }
        BPCListDTO bpcList = bpcService.findAllBPC(page, source, event, isAsc, startDate, endDate);
        result = gson.toJson(bpcList);
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
	  *  获取指定触发时间内的BPC数据
	 * <p>查找触发时间在指定时间段内的BPC数据
	 * 
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param callback 前端回调函数
	 * @return 过滤后的BPC数据
	 */
    @RequestMapping(value = "/getAggregation", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    
    public String getAggregation(@RequestParam(value = "startDate", required = false) String startDate,
                                 @RequestParam(value = "endDate", required = false) String endDate,
                                 @RequestParam(value = "callback", required = false) String callback) 
    {
        if (startDate != null && startDate.equals("")) 
        {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) 
        {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        BPCAggregationDTO dto = bpcService.findAggregation(startDate, endDate);
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
	  *  获取指定告警时间的BPC数据
	 * 
	 * <p>查找告警时间在指定时间段内的BPC数据
	 * 
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param callback 前端回调函数
	 * @return 过滤后的BPC数据
	 */
    @RequestMapping(value = "/getAlarmTime", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAlarmTime(@RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "callback", required = false) String callback) 
    {
        if (startDate != null && startDate.equals("")) 
        {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) 
        {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        List<BPCAlarmTimeDTO> dto = bpcService.findAlarmTime(startDate, endDate);
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
	  *  获取指定告警持续时间的BPC数据
	 * 
	 * <p>查找告警持续时间在指定时间段内的BPC数据
	 * 
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param callback 前端回调函数
	 * @return 过滤后的BPC数据
	 */
    @RequestMapping(value = "/getWarning", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody

    public String getWarning(@RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "callback", required = false) String callback) 
    {
        if (startDate != null && startDate.equals("")) 
        {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) 
        {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        List<BPCWarningDTO> dto = bpcService.findWarning(startDate, endDate);
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
	  *  获取指定事件来源的BPC数据
	 * 
	 * <p>查找指定时间来源的BPC数据
	 * 
	 * @param callback 前端回调函数
	 * @return 过滤后的BPC数据
	 */
    @RequestMapping(value = "/getSource", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody

    public String getSource(@RequestParam(value = "callback", required = false) String callback) 
    {
        String result;
        Gson gson = new Gson();
        List<BPCSource> dto = bpcService.getAllBpcSource();
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
	  *  获取某些原因引起的BPC告警数据
	 * 
	 * <p>查找指定事件原因的BPC数据
	 * 
	 * @param callback 前端回调函数
	 * @return 过滤后的BPC数据
	 */
    @RequestMapping(value = "/getEvent", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody

    public String getEvent(@RequestParam(value = "callback", required = false) String callback) 
    {
        String result;
        Gson gson = new Gson();
        List<IdNameVO> dto = bpcService.getAllBpcEvent();
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
	  *  删除某些BPC告警信息
	 * 
	 * <p>删除指定id的告警信息
	 * 
	 * @param ids 告警事件id列表
	 * @param callback 前端回调函数
	 * @return 删除后的告警信息列表
	 */
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody

    public String deleteByIds(@RequestParam(value = "ids", required = false) String ids
            , @RequestParam(value = "callback", required = false) String callback) 
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
        result = gson.toJson(bpcService.deleteByIds(idList));
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
