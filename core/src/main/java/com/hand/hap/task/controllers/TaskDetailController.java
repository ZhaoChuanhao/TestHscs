//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hand.hap.task.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.task.dto.TaskDetail;
import com.hand.hap.task.info.TaskDataInfo;
import com.hand.hap.task.service.ITaskDetailService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/sys/task/detail", "/api/sys/task/detail"})
public class TaskDetailController extends BaseController {
    @Autowired
    private ITaskDetailService service;

    public TaskDetailController() {
    }

    @RequestMapping(
            value = {"/queryAll"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ResponseData queryAll(TaskDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = this.createRequestContext(request);
        page = 1;
        pageSize = Integer.MAX_VALUE;
        return new ResponseData(this.service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(
            value = {"/query"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ResponseData query(TaskDetail dto, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize, HttpServletRequest request) {
        IRequest requestContext = this.createRequestContext(request);
        return new ResponseData(this.service.select(requestContext, dto, page, pageSize));
    }

    @PostMapping({"/submit"})
    public ResponseData update(@RequestBody List<TaskDetail> dto, BindingResult result, HttpServletRequest request) {
        this.getValidator().validate(dto, result);
        if(result.hasErrors()) {
            ResponseData iRequest1 = new ResponseData(false);
            iRequest1.setMessage(this.getErrorMessage(result, request));
            return iRequest1;
        } else {
            IRequest iRequest = this.createRequestContext(request);
            if(dto!=null && !dto.isEmpty()){
                for (int i = 0; i < dto.size(); i++) {
                    if(dto.get(i).getTaskId()==null){
                        dto.get(i).set__status("add");
                    }else{
                        dto.get(i).set__status("update");
                    }
                }
            }
            return new ResponseData(this.service.batchUpdate(iRequest, dto));
        }
    }

    @PostMapping({"/remove"})
    public ResponseData delete(HttpServletRequest request, @RequestBody List<TaskDetail> dto) {
        this.service.remove(dto);
        return new ResponseData();
    }

    @PostMapping({"/getById"})
    public ResponseData getById(HttpServletRequest request, @RequestBody TaskDetail dto) {
        IRequest iRequest = this.createRequestContext(request);
        return new ResponseData(Arrays.asList(new TaskDetail[]{(TaskDetail)this.service.selectByPrimaryKey(iRequest, dto)}));
    }

    @PostMapping({"/getGroupById"})
    public ResponseData getGroupById(HttpServletRequest request, @RequestBody TaskDetail dto) {
        IRequest iRequest = this.createRequestContext(request);
        TaskDetail group = this.service.getGroupById(iRequest, dto);
        return new ResponseData(Arrays.asList(new TaskDetail[]{group}));
    }

    @PostMapping({"/selectUnboundTasks"})
    public ResponseData queryUnboundTasks(TaskDetail dto, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize, HttpServletRequest request) {
        IRequest iRequest = this.createRequestContext(request);
        Object idList = new ArrayList();
        if(dto.getIds() != null) {
            idList = Arrays.asList(dto.getIds().split(","));
        }

        return new ResponseData(this.service.queryUnboundTasks(iRequest, dto, (List)idList, page, pageSize));
    }

    @PostMapping({"/executeQuery"})
    public ResponseData executeQuery(TaskDetail taskDetail, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize, HttpServletRequest request) {
        IRequest iRequest = this.createRequestContext(request);
        return new ResponseData(this.service.executeQuery(iRequest, taskDetail, page, pageSize));
    }

    @PostMapping({"/execute"})
    public ResponseData execute(@RequestBody TaskDataInfo taskDataInfo, HttpServletRequest request) throws Exception {
        IRequest iRequest = this.createRequestContext(request);
        return new ResponseData(this.service.execute(iRequest, taskDataInfo));
    }

    @PostMapping({"/updateChildrenTasks"})
    public ResponseData updateChildrenTasks(@RequestBody TaskDetail data, HttpServletRequest request) {
        IRequest iRequest = this.createRequestContext(request);
        this.service.updateChildrenTasks(iRequest, data);
        return new ResponseData();
    }

    @PostMapping({"/detail"})
    public ResponseData detailByTId(@RequestBody TaskDetail taskDetail, HttpServletRequest request) throws Exception {
        IRequest iRequest = this.createRequestContext(request);
        return new ResponseData(this.service.queryTaskDetail(iRequest, taskDetail));
    }
}
