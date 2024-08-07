package com.ispan.warashibe.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SpecImg;
import com.ispan.warashibe.model.SpecOneNames;
import com.ispan.warashibe.model.SpecOnes;
import com.ispan.warashibe.model.SpecTwoNames;
import com.ispan.warashibe.model.SpecTwos;
import com.ispan.warashibe.repository.ProductRepository;
import com.ispan.warashibe.repository.SpecImgRepository;
import com.ispan.warashibe.repository.SpecOneNameRepository;
import com.ispan.warashibe.repository.SpecOneRepository;
import com.ispan.warashibe.repository.SpecTwoNameRepository;
import com.ispan.warashibe.repository.SpecTwoRepository;

@Service
public class SpecService {

    @Autowired
    private SpecOneRepository specOneRepository;

    @Autowired
    private SpecOneNameRepository specOneNameRepository;

    @Autowired
    private SpecTwoRepository specTwoRepository;

    @Autowired
    private SpecTwoNameRepository specTwoNameRepository;

    @Autowired
    private SpecImgRepository specImgRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String createSpec(String jsonSpec, Integer productID, MultipartFile image) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonSpec);
            String specOneNameStr = rootNode.get("specOneName").asText();

            Optional<Products> optionalProduct = productRepository.findById(productID);
            if (!optionalProduct.isPresent()) {
                return "{\"success\": false, \"message\": \"Product not found\"}";
            }

            Products product = optionalProduct.get();
            SpecOneNames specOneName = new SpecOneNames();
            //第一層            
            specOneName.setSpecOneName(specOneNameStr);
            specOneName.setProduct(product);

            specOneName = specOneNameRepository.save(specOneName);
            Integer specOneNameID = specOneName.getSpecOneNameID();

            SpecImg specImg = null;
            if (image != null && !image.isEmpty()) {
                specImg = new SpecImg();
                specImg.setSpecImg(image.getBytes());
                specImgRepository.save(specImg);
            }

            Iterator<JsonNode> specOnesNodes = rootNode.get("specOnes").elements();
            while (specOnesNodes.hasNext()) {
                JsonNode specOneNode = specOnesNodes.next();
                SpecOnes specOne = new SpecOnes();
                //第二層                
                specOne.setSpecOne(specOneNode.get("specOne").asText());
                specOne.setSpecOneName(specOneName);
                if (specImg != null) {
                    specOne.setSpecOneImg(specImg);
                }
                specOne = specOneRepository.save(specOne);
                Integer specOneID = specOne.getSpecOneID();

                Iterator<JsonNode> specTwoNamesNodes = specOneNode.get("specTwoNames").elements();
                while (specTwoNamesNodes.hasNext()) {
                    JsonNode specTwoNameNode = specTwoNamesNodes.next();
                    SpecTwoNames specTwoName = new SpecTwoNames();
                    //第三層                    
                    specTwoName.setSpecTwoName(specTwoNameNode.get("specTwoName").asText());
                    specTwoName.setSpecOne(specOne);
                    specTwoName = specTwoNameRepository.save(specTwoName);
                    Integer specTwoNameID = specTwoName.getSpecTwoNameID();

                    Iterator<JsonNode> specTwosNodes = specTwoNameNode.get("specTwos").elements();
                    while (specTwosNodes.hasNext()) {
                        JsonNode specTwoNode = specTwosNodes.next();
                        String specTwoStr = specTwoNode.get("specTwo").asText();
                        int specTwoQt = specTwoNode.get("specTwoQt").asInt();

                        SpecTwos specTwo = new SpecTwos();
                        specTwo.setSpecTwo(specTwoStr);
                        specTwo.setSpecTwoQt(specTwoQt);
                        specTwo.setSpecTwoName(specTwoName);
//                        if (specImg != null) {
//                        	specTwo.setSpecTwoImg(specImg);
//                        }
//                        System.out.println(specTwo.toString());
                        specTwoRepository.save(specTwo);
                    }
                }
            }

            return "{\"success\": true, \"message\": \"Spec created successfully\"}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"Spec creation failed: " + e.getMessage() + "\"}";
        }
    }

    public String updateSpec(Integer specId, String jsonSpec, Integer productID, MultipartFile image) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonSpec);

            Optional<SpecOneNames> optionalSpec = specOneNameRepository.findById(specId);
            if (!optionalSpec.isPresent()) {
                return "{\"success\": false, \"message\": \"Spec not found\"}";
            }

            SpecOneNames specOneName = optionalSpec.get();
            specOneName.setSpecOneName(rootNode.get("specOneName").asText());
            Optional<Products> optionalProduct = productRepository.findById(productID);
            if (!optionalProduct.isPresent()) {
                return "{\"success\": false, \"message\": \"Product not found\"}";
            }

            Products product = optionalProduct.get();
            specOneName.setProduct(product);

            SpecImg specImg = null;
            if (image != null && !image.isEmpty()) {
                specImg = new SpecImg();
                specImg.setSpecImg(image.getBytes());
                specImgRepository.save(specImg);
            }

            Iterator<JsonNode> specOnesNodes = rootNode.get("specOnes").elements();
            while (specOnesNodes.hasNext()) {
                JsonNode specOneNode = specOnesNodes.next();
                SpecOnes specOne = new SpecOnes();
                specOne.setSpecOne(specOneNode.get("specOne").asText());
                specOne.setSpecOneName(specOneName);
                if (specImg != null) {
                    specOne.setSpecOneImg(specImg);
                }
                specOne = specOneRepository.save(specOne);
                Integer specOneID = specOne.getSpecOneID();

                Iterator<JsonNode> specTwoNamesNodes = specOneNode.get("specTwoNames").elements();
                while (specTwoNamesNodes.hasNext()) {
                    JsonNode specTwoNameNode = specTwoNamesNodes.next();
                    SpecTwoNames specTwoName = new SpecTwoNames();
                    specTwoName.setSpecTwoName(specTwoNameNode.get("specTwoName").asText());
                    specTwoName.setSpecOne(specOne);
                    specTwoName = specTwoNameRepository.save(specTwoName);
                    Integer specTwoNameID = specTwoName.getSpecTwoNameID();

                    Iterator<JsonNode> specTwosNodes = specTwoNameNode.get("specTwos").elements();
                    while (specTwosNodes.hasNext()) {
                        JsonNode specTwoNode = specTwosNodes.next();
                        String specTwoStr = specTwoNode.get("specTwo").asText();
                        int specTwoQt = specTwoNode.get("specTwoQt").asInt();

                        SpecTwos specTwo = new SpecTwos();
                        specTwo.setSpecTwo(specTwoStr);
                        specTwo.setSpecTwoQt(specTwoQt);
                        specTwo.setSpecTwoName(specTwoName);
//                        if (specImg != null) {
//                        	specTwo.setSpecTwoImg(specImg);
//                        }
                        specTwoRepository.save(specTwo);
                    }
                }
            }

            specOneNameRepository.save(specOneName);
            return "{\"success\": true, \"message\": \"Spec updated successfully\"}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"Spec update failed: " + e.getMessage() + "\"}";
        }
    }

    public String deleteSpec(Integer specId, Integer productID) {
        try {
            Optional<SpecOneNames> optionalSpec = specOneNameRepository.findById(specId);
            if (!optionalSpec.isPresent()) {
                return "{\"success\": false, \"message\": \"Spec not found\"}";
            }

            SpecOneNames specOneName = optionalSpec.get();
            if (!specOneName.getProduct().getProductID().equals(productID)) {
                return "{\"success\": false, \"message\": \"Product ID mismatch\"}";
            }

            specOneNameRepository.deleteById(specId);
            return "{\"success\": true, \"message\": \"Spec deleted successfully\"}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"Spec deletion failed: " + e.getMessage() + "\"}";
        }
    }

    public SpecOneNames getSpec(Integer specId) {
        return specOneNameRepository.findById(specId).orElse(null);
    }

    public List<SpecOneNames> getAllSpecs() {
        return specOneNameRepository.findAll();
    }

    public byte[] getImgById(Integer imgId) {
        Optional<SpecImg> optionalImg = specImgRepository.findById(imgId);
        if (optionalImg.isPresent()) {
            return optionalImg.get().getSpecImg();
        }
        return null;
    }
}
