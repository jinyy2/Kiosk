package com.web.blog.dao.orderlist;

import java.util.*;

import com.web.blog.model.orderlist.Orderlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderlistDao extends JpaRepository<Orderlist, String> {

	List<Orderlist> findOrderlistByUidAndSid(int uid, int sid);

	Orderlist findTop1OrderlistByUidAndSidOrderByOrderdateDesc(int uid, int sid);

	ArrayList<Orderlist> findOrderlistByUidAndSidOrderByOrderdateDesc(int uid, int sid);

	ArrayList<Orderlist> findOrderlistByUidAndSidOrderByOrderdateAsc(int uid, int sid);

	@Query(nativeQuery = true, value = "SELECT DATE_FORMAT(o.orderdate,'%m'), SUM(b.price) FROM orderlist o join branch b on o.menuid = b.menuid GROUP BY DATE_FORMAT(o.orderdate,'%Y%m')")
	List<?> findMonthIncome();

}
