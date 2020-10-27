package com.web.blog.controller.branch;

import com.web.blog.dao.branch.BranchDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.branch.Branch;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import java.util.*;


@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class BranchController {

    @Autowired
    BranchDao BranchDao;

    @GetMapping("/branch/menu")
    @ApiOperation(value = "키오스 메뉴 전체 목록")
    public Object menu(@RequestParam(required = true) int sid) {

    List<Branch> menulist = BranchDao.findBranchBySid(sid);
    for (int i = 0; i < menulist.size(); i++) {
        System.out.println(menulist.get(i));
    }
    ResponseEntity<Object> response = null;
    
    BasicResponse result = new BasicResponse();
    result.status = true;
    result.data = "키오스 메뉴 전체 목록 조회 완료";
    result.object = menulist;
    response = new ResponseEntity<>(result, HttpStatus.OK);
    return response;
    }
}
