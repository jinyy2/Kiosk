
package com.web.blog.dao.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import com.web.blog.model.user.User;
import com.web.blog.model.visit.Visit;

public interface VisitDao extends JpaRepository<User, Integer> {
    void saveVisit(Visit visit);
}
