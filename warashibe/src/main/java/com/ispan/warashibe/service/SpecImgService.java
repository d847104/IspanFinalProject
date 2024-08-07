package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.warashibe.model.SpecImg;
import com.ispan.warashibe.repository.SpecImgRepository;

@Service
public class SpecImgService {

    @Autowired
    private SpecImgRepository specImgRepository;

    public void saveSpecImg(SpecImg specImg, MultipartFile image) throws Exception {
        if (image != null && !image.isEmpty()) {
            specImg.setSpecImg(image.getBytes());
        }
        specImgRepository.save(specImg);
    }

    public void updateSpecImg(Integer id, SpecImg updatedSpecImg, MultipartFile image) throws Exception {
        SpecImg specImg = specImgRepository.findById(id)
                .orElseThrow(() -> new Exception("SpecImg not found with ID " + id));

        if (image != null && !image.isEmpty()) {
            specImg.setSpecImg(image.getBytes());
        }

        specImgRepository.save(specImg);
    }

    public void deleteSpecImg(Integer id) throws Exception {
        SpecImg specImg = specImgRepository.findById(id)
                .orElseThrow(() -> new Exception("SpecImg not found with ID " + id));
        specImgRepository.delete(specImg);
    }

    public SpecImg getSpecImgById(Integer id) throws Exception {
        return specImgRepository.findById(id)
                .orElseThrow(() -> new Exception("SpecImg not found with ID " + id));
    }

    public List<SpecImg> getAllSpecImgs() {
        return specImgRepository.findAll();
    }
}
