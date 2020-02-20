package com.springapp.spring_project.service;

import com.springapp.spring_project.entity.Config;

import java.util.List;

public interface SrpConfigService {
    public List<Config> get(String id);
    public void save(Config config);
}
