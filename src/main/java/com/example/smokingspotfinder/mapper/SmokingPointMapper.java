package com.example.smokingspotfinder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.entity.SmokingPoint;

@Mapper
public interface SmokingPointMapper {
	/** 全件検索 */
	List<SmokingPoint> findAll();
	/** １件検索 */
	SmokingPoint findById(@Param("id") Long id);
	/** 新規作成 */
	int insert(SmokingPoint sp);
	/** １件削除 */
	int deleteById(@Param("id") Long id);
	/** 上書き */
	int update(SmokingPoint sp);
	/** 全緯度経度取出 */
	List<LatLng> findAllLatLng();
	/** 全SmokingPointMapDto取出 */
	List<SmokingPointMapDto> findAllForMap();
	/** 周辺検索 */
	SmokingPoint findByLatLngApprox(
		    @Param("lat") Double lat,
		    @Param("lng") Double lng,
		    @Param("delta") Double delta
		);
}
