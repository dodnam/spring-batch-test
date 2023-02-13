package com.nam.springbatchtest.core.repository;

import com.nam.springbatchtest.core.domain.ResultText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultTextRespository  extends JpaRepository<ResultText, Integer> {
}
