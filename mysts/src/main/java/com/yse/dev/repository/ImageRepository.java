package com.yse.dev.repository;

import com.yse.dev.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository 
extends JpaRepository<ImageFile,String>, CrudRepository<ImageFile,String>{

}
