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
 * Create by 叶云鹏 on 2018/12/10
 */
@Controller
@RequestMapping("/BPC")
public class BPCController {
    @Resource
    private BPCService bpcService;

    @RequestMapping(value = "/getAllList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllList(@RequestParam("page") int page,
                             @RequestParam(value = "source", required = false) Integer source,
                             @RequestParam(value = "event", required = false) Integer event,
                             @RequestParam(value = "isAsc", required = false) Boolean isAsc,
                             @RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "callback", required = false) String callback) {
        if (startDate != null && startDate.equals("")) {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        // 默认是降序
        if (isAsc == null) {
            isAsc = false;
        }
        BPCListDTO bpcList = bpcService.findAllBPC(page, source, event, isAsc, startDate, endDate);
        result = gson.toJson(bpcList);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }

    @RequestMapping(value = "/getAggregation", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAggregation(@RequestParam(value = "startDate", required = false) String startDate,
                                 @RequestParam(value = "endDate", required = false) String endDate,
                                 @RequestParam(value = "callback", required = false) String callback) {
        if (startDate != null && startDate.equals("")) {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        BPCAggregationDTO dto = bpcService.findAggregation(startDate, endDate);
        result = gson.toJson(dto);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }

    @RequestMapping(value = "/getAlarmTime", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAlarmTime(@RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "callback", required = false) String callback) {
        if (startDate != null && startDate.equals("")) {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        List<BPCAlarmTimeDTO> dto = bpcService.findAlarmTime(startDate, endDate);
        result = gson.toJson(dto);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }

    @RequestMapping(value = "/getWarning", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getWarning(@RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "callback", required = false) String callback) {
        if (startDate != null && startDate.equals("")) {
            startDate = null;
        }
        if (endDate != null && endDate.equals("")) {
            startDate = null;
        }
        String result;
        Gson gson = new Gson();
        List<BPCWarningDTO> dto = bpcService.findWarning(startDate, endDate);
        result = gson.toJson(dto);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }

    @RequestMapping(value = "/getSource", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getSource(@RequestParam(value = "callback", required = false) String callback) {
        String result;
        Gson gson = new Gson();
        List<BPCSource> dto = bpcService.getAllBpcSource();
        result = gson.toJson(dto);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }

    @RequestMapping(value = "/getEvent", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getEvent(@RequestParam(value = "callback", required = false) String callback) {
        String result;
        Gson gson = new Gson();
        List<IdNameVO> dto = bpcService.getAllBpcEvent();
        result = gson.toJson(dto);
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }

    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteByIds(@RequestParam(value = "ids", required = false) String ids
            , @RequestParam(value = "callback", required = false) String callback) {
        String result;
        Gson gson = new Gson();
        if (ids == null || ids.equals("")) {
            return null;
        }
        List<Integer> idList = gson.fromJson(ids, new TypeToken<List<Integer>>() {
        }.getType());
        result = gson.toJson(bpcService.deleteByIds(idList));
        if (callback != null)
            return callback + "(" + result + ")";
        else return result;
    }
}
