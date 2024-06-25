package com.yse.dev.service;

import com.yse.dev.entity.ImageFile;

import java.util.List;

public interface ImageService {
	public void insert(ImageFile imageFile);
	public ImageFile getImageById(String id);
	public List<ImageFile> getImageList();
	public void update(ImageFile imageFile);
}
