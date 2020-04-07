package com.da.learn.learnboot.maintainpush.excel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintainItemRepository extends JpaRepository<MaintainItem, Long> {
}
