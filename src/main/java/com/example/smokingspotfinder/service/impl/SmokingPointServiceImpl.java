package com.example.smokingspotfinder.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.SmokingPointDistanceDto;
import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.entity.SmokingPoint;
import com.example.smokingspotfinder.repository.SmokingPointRepository;
import com.example.smokingspotfinder.service.SmokingPointService;
@Service
@Transactional
public class SmokingPointServiceImpl implements SmokingPointService {
	private final SmokingPointRepository spRepository;
	public SmokingPointServiceImpl(SmokingPointRepository spRepository) {
		this.spRepository=spRepository;
	}
	
	@Override
	public List<SmokingPoint> findAll() {
		return spRepository.findAll();
	}

	@Override
	public SmokingPoint findById(Long id) {
		return spRepository.findById(id);
	}

	@Override
	public void register(SmokingPoint sp) {
		spRepository.insert(sp);
	}

	@Override
	public void update(SmokingPoint sp) {
		spRepository.update(sp);
	}

	@Override
	public void delete(Long id) {
		spRepository.deleteById(id);
	}

	@Override
	public List<LatLng> findAllLatLng() {
		return spRepository.findAllLatLng();
	}

	@Override
	public List<SmokingPointMapDto> findAllForMap() {
		return spRepository.findAllForMap();
	}

	@Override
	public List<SmokingPointDistanceDto> getNearest20(Double lat, Double lng) {
		List<SmokingPoint> allList = spRepository.findAll();
		return allList.stream()
				.map(sp -> {
					Double d = distance(lat, lng, sp.getLatitude(), sp.getLongitude());
					Double meter = Math.sqrt(d)*111000;
					return new SmokingPointDistanceDto(sp, meter);
				})
				.sorted(Comparator.comparingDouble(SmokingPointDistanceDto::getDistance))
				.limit(20).toList();
	}
	
	private Double distance(Double latA,Double lngA,Double latB,Double lngB){
		return (latA-latB)*(latA-latB)+(lngA-lngB)*(lngA-lngB);
	}
	
}
