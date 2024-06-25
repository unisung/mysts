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

    @Override
    public void update(ImageFile imageFile) {
        ImageFile imageFile1  = repository.findById(imageFile.getId()).orElse(null);
        imageFile1.setTruthTitle(imageFile.getTruthTitle());
        imageFile1.setPredTitle1(imageFile.getPredTitle1());
        imageFile1.setPredTitle2(imageFile.getPredTitle2());
        imageFile1.setPredTitle3(imageFile.getPredTitle3());
        repository.save(imageFile1);
    }
}
