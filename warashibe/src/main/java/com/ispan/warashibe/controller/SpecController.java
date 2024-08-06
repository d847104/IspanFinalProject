package com.ispan.warashibe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.warashibe.model.*;
import com.ispan.warashibe.service.SpecService;

import java.util.List;

@RestController
@RequestMapping("/api/spec")
@CrossOrigin
public class SpecController {

    @Autowired
    private SpecService specService;

    @PostMapping("/create")
    public String createSpec(@RequestParam("jsonSpec") String jsonSpec, 
                             @RequestParam("productID") Integer productID, 
                             @RequestParam("image") MultipartFile image) {
        return specService.createSpec(jsonSpec, productID, image);
    }

    @PutMapping("/update/{specId}")
    public String updateSpec(@PathVariable Integer specId, 
                             @RequestParam("jsonSpec") String jsonSpec, 
                             @RequestParam("productID") Integer productID, 
                             @RequestParam("image") MultipartFile image) {
        return specService.updateSpec(specId, jsonSpec, productID, image);
    }

    @DeleteMapping("/delete/{specId}/{productID}")
    public String deleteSpec(@PathVariable Integer specId, @PathVariable Integer productID) {
        return specService.deleteSpec(specId, productID);
    }

    @GetMapping("/get/{specId}")
    public SpecOneNames getSpec(@PathVariable Integer specId) {
        return specService.getSpec(specId);
    }

    @GetMapping("/all")
    public List<SpecOneNames> getAllSpecs() {
        return specService.getAllSpecs();
    }

    @GetMapping(path = "/img/{imgId}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] getImgById(@PathVariable Integer imgId) {
        return specService.getImgById(imgId);
    }
}
