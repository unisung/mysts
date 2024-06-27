package com.yse.dev.service;

import com.yse.dev.entity.ImageFile;

import java.util.List;

public interface ImageService {
	public void insert(ImageFile imageFile);
	public ImageFile getImageById(String id);
	public List<ImageFile> getImageList();
	public void update(ImageFile imageFile);
	//max값 가져오는 메소드 추가
	public Long findMaxSeqNo();
}
