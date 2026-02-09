package com.example.smokingspotfinder.service;

import java.util.List;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.SmokingPointDistanceDto;
import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.entity.SmokingPoint;

public interface SmokingPointService {
	/** 一覧取得 */
	List<SmokingPoint> findAll();
	/** 1件取得 */
	SmokingPoint findById(Long id);
	/** 登録 */
	void register(SmokingPoint sp);
	/** 更新 */
	void update(SmokingPoint sp);
	/** 削除 */
	void delete(Long id);
	/** 緯度経度だけ取得 */
	List<LatLng> findAllLatLng();
	/** 全SmokingPointMapDto取出 */
	List<SmokingPointMapDto> findAllForMap();
	/** 近所20県抽出 */
	List<SmokingPointDistanceDto> getNearest20(Double lat,Double lng);
}
