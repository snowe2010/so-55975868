package com.promontech.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Map2Repository extends JpaRepository<Map2, Long> {
}
