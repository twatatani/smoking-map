package com.example.smokingspotfinder.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.entity.SmokingPoint;
import com.example.smokingspotfinder.mapper.SmokingPointMapper;
import com.example.smokingspotfinder.repository.SmokingPointRepository;

@Repository
public class SmokingPointRepositoryImpl implements SmokingPointRepository {
	private final SmokingPointMapper spMapper;

	public SmokingPointRepositoryImpl(SmokingPointMapper spMapper) {
		this.spMapper = spMapper;
	}

	@Override
	public List<SmokingPoint> findAll() {
		return spMapper.findAll();
	}

	@Override
	public SmokingPoint findById(Long id) {
		return spMapper.findById(id);
	}

	@Override
	public int insert(SmokingPoint sp) {
		return spMapper.insert(sp);
	}

	@Override
	public int deleteById(Long id) {
		return spMapper.deleteById(id);
	}

	@Override
	public int update(SmokingPoint sp) {
		return spMapper.update(sp);
	}

	@Override
	public List<LatLng> findAllLatLng() {
		return spMapper.findAllLatLng();
	}

	@Override
	public List<SmokingPointMapDto> findAllForMap() {
		return spMapper.findAllForMap();
	}

	public SmokingPoint findByLatLngApprox(Double lat, Double lng, Double delta) {
	    return spMapper.findByLatLngApprox(lat, lng, delta);
	}

}
