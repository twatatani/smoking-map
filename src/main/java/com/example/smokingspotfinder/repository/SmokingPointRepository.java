package com.example.smokingspotfinder.repository;

import java.util.List;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.entity.SmokingPoint;

public interface SmokingPointRepository {
	/** 全喫煙場所取出 */
	List<SmokingPoint> findAll();
	/** 喫煙場所取出 */
	SmokingPoint findById(Long id);
	/** 新規登録 */
	int insert(SmokingPoint sp);
	/** 1件消去 */
	int deleteById(Long id);
	/** 上書き */
	int update(SmokingPoint sp);
	/** 全緯度経度取出 */
	List<LatLng> findAllLatLng();
	/** 全SmokingPointMapDto取出 */
	List<SmokingPointMapDto> findAllForMap();
	/** 周辺検索 */
	SmokingPoint findByLatLngApprox(Double lat, Double lng,Double delta);
}
