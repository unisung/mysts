package com.yse.dev.service;

import com.yse.dev.entity.ImageFile;
import com.yse.dev.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImageRepository repository;

    @Override
    public void insert(ImageFile imageFile) {
      repository.save(imageFile);
    }

    @Override
    public ImageFile getImageById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ImageFile> getImageList() {
        return List.of();
    }
}
