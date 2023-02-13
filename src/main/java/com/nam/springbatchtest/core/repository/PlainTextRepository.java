package com.nam.springbatchtest.core.repository;

import com.nam.springbatchtest.core.domain.PlainText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlainTextRepository extends JpaRepository<PlainText, Integer> {

    Page<PlainText> findBy(Pageable pageable); // page의 사이즈만큼 읽어옴

}
