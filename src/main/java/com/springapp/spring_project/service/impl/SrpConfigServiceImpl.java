package com.springapp.spring_project.service.impl;

import com.springapp.spring_project.dao.SrpConfigRepository;
import com.springapp.spring_project.entity.Config;
import com.springapp.spring_project.service.SrpConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrpConfigServiceImpl implements SrpConfigService {

    private SrpConfigRepository repository;

    public SrpConfigServiceImpl(SrpConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Config> get(String id) {
        return repository.findAll();
    }

    @Override
    public void save(Config config) {
        repository.save(config);
    }
}
