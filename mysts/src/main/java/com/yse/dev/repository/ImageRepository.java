package com.yse.dev.repository;

import com.yse.dev.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository 
extends JpaRepository<ImageFile,String>, CrudRepository<ImageFile,String>{
    // 순번의 최대값 얻기 메소드 추가
    @Query("SELECT COALESCE(MAX(f.seqNo), 0) FROM ImageFile f")
    Long findMaxSeqNo();

}
