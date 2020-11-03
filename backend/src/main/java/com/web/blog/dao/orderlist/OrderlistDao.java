package com.web.blog.dao.orderlist;

import java.util.*;
import java.sql.Date;

import com.web.blog.model.orderlist.Orderlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderlistDao extends JpaRepository<Orderlist, String> {

	List<Orderlist> findOrderlistByUidAndSid(int uid, int sid);
	// List<Branch> findBranchBySid(int sid);

	Orderlist findTop1OrderlistByUidAndSidOrderByOrderdateDesc(int uid, int sid);

	// Orderlist[] findOrderlistByUidAndSidOrderByOrderdateDesc(int uid, int sid);
	ArrayList<Orderlist> findOrderlistByUidAndSidOrderByOrderdateDesc(int uid, int sid);

}
