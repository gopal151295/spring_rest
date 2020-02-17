package com.springapp.spring_project.repo;

import java.util.List;

public interface UrlRepo {
    public void saveUrl(String key, String value);
    public List getAllUrls();
    public String getUrl(String key);
    public void updateUrl(String key);

}
