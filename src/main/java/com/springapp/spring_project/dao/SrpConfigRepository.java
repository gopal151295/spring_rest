package com.springapp.spring_project.dao;

import com.springapp.spring_project.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SrpConfigRepository extends JpaRepository<Config, String> {
}
