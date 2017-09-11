package com.min.auto.api.repository;

import com.min.auto.api.bean.ApiBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockApiRepository extends JpaRepository<ApiBean, Integer> {

    /**
     * @param path
     * @param flag 是否有效
     * @return
     */
    public ApiBean findByPathAndFlag(String path,int flag);

    public ApiBean findByPath(String path);

}
