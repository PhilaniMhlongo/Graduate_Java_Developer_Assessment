package com.enviro.assessment.grad001.philanimhlongo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;

public interface RecyclingTipRepository extends JpaRepository<RecyclingTip,Integer>{
    List<RecyclingTip> findByWasteCategoryId(int wasteCategoryId);
}
