package com.web.blog.controller.orderlist;

import com.web.blog.dao.branch.BranchDao;
import com.web.blog.dao.orderlist.OrderlistDao;
import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.branch.Branch;
import com.web.blog.model.orderlist.Orderlist;
import com.web.blog.model.orderlist.OrderlistRequest;
import com.web.blog.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import java.util.*;

import javax.validation.Valid;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class OrderlistController {

    @Autowired
    UserDao userDao;

    @Autowired
    BranchDao BranchDao;

    @Autowired
    OrderlistDao OrderlistDao;


    @GetMapping("/get/orderlist")
    @ApiOperation(value = "주문 메뉴 전체 목록")
    public Object getorderlist(@RequestParam(required = true) int uid, int sid) {

        ArrayList<Orderlist> orderlistlist = OrderlistDao.findOrderlistByUidAndSidOrderByOrderdateDesc(uid, sid);
        System.out.println("///////////////////////////");
        List<Branch> menulist = BranchDao.findBranchBySid(sid);
        ArrayList<Object> ret = new ArrayList<>();
        for (int i = 0; i < menulist.size(); i++) {
            for (int j = 0; j < orderlistlist.size(); j++) {
                if(menulist.get(i).getMenuid() == orderlistlist.get(j).getMenuid()){
                    ret.add(menulist.get(i));
                }
            }
        }

        ResponseEntity<Object> response = null;
        BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "주문 메뉴 전체 목록 조회 완료";
        result.object = ret;
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/get/orderlist2")
    @ApiOperation(value = "주문 메뉴 전체 목록2")
    public Object getorderlist2(@RequestParam(required = true) int uid, int sid) {
        ArrayList<Orderlist> orderlistlist = OrderlistDao.findOrderlistByUidAndSidOrderByOrderdateDesc(uid, sid);
        ResponseEntity<Object> response = null;
        BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "주문 메뉴 전체 목록 조회 완료";
        result.object = orderlistlist;
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }



    @GetMapping("/get/orderlist/recent")
    @ApiOperation(value = "최신 주문 메뉴")
    public Object getorderlistrecent(@RequestParam(required = true) int uid, int sid) {
        System.out.println("logger - 최신 주문 메뉴");
        Orderlist orderlistrecent = new Orderlist();
        ResponseEntity<Object> response = null;

        ArrayList<Orderlist> asdf;
        // orderlistrecent = OrderlistDao.find1OrderlistByUidAndSidOrderByOrderdateDesc(uid, sid);

        asdf = OrderlistDao.findOrderlistByUidAndSidOrderByOrderdateDesc(uid, sid);

        BasicResponse result = new BasicResponse();

        result.status = true;
        result.data = "최신 주문 메뉴 조회 완료";
        result.object = asdf;
        System.out.println(result.object);
        response = new ResponseEntity<>(result, HttpStatus.OK);

        return response;
    }

    @PostMapping("/create/order")
    @ApiOperation(value = "주문 메뉴")
    public Object orderMenu(@Valid @RequestBody final OrderlistRequest[] orderlistRequest) {

        ResponseEntity<Object> response = null;
        // Optional<User> user = userDao.findUserByUid(orderlistRequest.getUid());

        System.out.println("orderlistRequest" + orderlistRequest[0]);
        System.out.println("orderlistRequest" + orderlistRequest[1]);

        for (int i = 0; i < orderlistRequest.length; i++) {
            final Orderlist orderlist = new Orderlist();
            // orderlist.setUid(user.get().getUid());
            orderlist.setUid(11);
            orderlist.setMenuid(orderlistRequest[i].getMenuid());
            orderlist.setSid(orderlistRequest[i].getSid());
            final Orderlist saveOrderlist = this.OrderlistDao.save(orderlist);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "주문 메뉴 생성 완료";
            result.object = saveOrderlist;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping("/order/mymenu")
    @ApiOperation(value = "최근 먹은 주문 메뉴")
    public Object orderlistrecent(@RequestParam(required = true) int uid, int sid) {
        System.out.println("logger - 최신 주문 메뉴");

        List<Orderlist> orderlistlist = OrderlistDao.findOrderlistByUidAndSidOrderByOrderdateDesc(uid, sid);
        List<Branch> menulist = BranchDao.findBranchBySid(sid);
         
        HashSet<Object> ret = new HashSet<>();
        for (int i = 0; i < menulist.size(); i++) {
            for (int j = 0; j < orderlistlist.size(); j++) {
                if (menulist.get(i).getMenuid() == orderlistlist.get(j).getMenuid()) {
                    ret.add(menulist.get(i));
                }
            }
        }
        System.out.println(ret);
        return ret;
    }
}
