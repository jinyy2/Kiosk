package com.web.blog.controller.admin;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.admin.AdminDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.visit.Visit;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.LocalTime;
import java.util.List;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class AdminController {

    @Autowired
    private AdminDao adminDao;

    @GetMapping("/admin/getvisitorcount")
    @ApiOperation(value = "오늘 방문자 수")
    public ResponseEntity<Integer> getVisitorCount() throws SQLException, IOException {

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

        int count = 0;
        try {
            count = adminDao.countByCurrenttimeBetween(startDatetime, endDatetime);
            System.out.println("logger - count: " + count);
            return new ResponseEntity<Integer>(count, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("logger- 방문자 수 에러" + count);
            count = -1;

            return new ResponseEntity<Integer>(count, HttpStatus.NOT_FOUND);
        }

    }

    //TODO: Uid => 이름으로, 방문기록 자세히
    @GetMapping("/admin/getvisitors")
    @ApiOperation(value = "방문기록")
    public ResponseEntity<List<Visit>> getVisitors() throws SQLException, IOException {
        
        System.out.println("logger - getVisiters: ");
        List<Visit> list = null;

        try {
            list = adminDao.findAllByOrderByVidDesc();
            return new ResponseEntity<List<Visit>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<List<Visit>>(list, HttpStatus.NOT_FOUND);
        }

    }

}