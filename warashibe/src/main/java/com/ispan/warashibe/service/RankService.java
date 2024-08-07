package com.ispan.warashibe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.Rank;
import com.ispan.warashibe.repository.RankRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@Service
public class RankService {

    @Autowired
    private RankRepository rankRepository;

    public List<Rank> findAll() {
        return rankRepository.findAll();
    }

    public Rank findById(Integer id) {
        Optional<Rank> rank = rankRepository.findById(id);
        return rank.orElse(null);
    }

    public Rank save(String jsonRank) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Rank rank = objectMapper.readValue(jsonRank, Rank.class);
        return rankRepository.save(rank);
    }

    public Rank update(Integer id, String jsonRank) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Rank rankDetails = objectMapper.readValue(jsonRank, Rank.class);
        Rank rank = findById(id);
        if (rank != null) {
            rank.setRanking(rankDetails.getRanking());
            rank.setRankmsg(rankDetails.getRankmsg());
            rank.setRankDate(rankDetails.getRankDate());
            rank.setProduct(rankDetails.getProduct());
            rank.setMember(rankDetails.getMember());
            rank.setOrder(rankDetails.getOrder());
            return rankRepository.save(rank);
        } else {
            throw new RuntimeException("Rank not found");
        }
    }

    public void delete(Integer id) {
        rankRepository.deleteById(id);
    }

    public List<Rank> searchRanksByRanking(Integer ranking, Pageable pageable) {
        return rankRepository.findByRanking(ranking, pageable);
    }
    
    public List<Rank> getRanksByMemberID(Integer memberID) {
        return rankRepository.findByMember_MemberID(memberID);
    }
    
    public List<Rank> getRanksByProductID(Integer productID) {
        return rankRepository.findByProduct_ProductID(productID);
    }    
}
