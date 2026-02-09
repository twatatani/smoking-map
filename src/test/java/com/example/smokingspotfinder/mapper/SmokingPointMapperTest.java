package com.example.smokingspotfinder.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.entity.SmokingPoint;

@SpringBootTest
@Transactional
public class SmokingPointMapperTest {
	@Autowired
	SmokingPointMapper mapper;

	@Test
	void testFindAll() {
		List<SmokingPoint> list = mapper.findAll();
		assertNotNull(list);
		assertEquals(3, list.size());
		for (SmokingPoint p : list) {
			assertNotNull(p.getId());
			assertTrue(p.getId() > 0);
			assertNotNull(p.getName());
			assertFalse(p.getName().isBlank());
			assertNotNull(p.getLatitude());
			assertNotNull(p.getLongitude());
		}
	}

	@Test
	void testFindById() {
		SmokingPoint p = mapper.findById(1L);
		assertNotNull(p);
		assertEquals(1L, p.getId());
		assertTrue(p.getId() > 0);
		assertNotNull(p.getName());
		assertFalse(p.getName().isBlank());
		assertNotNull(p.getLatitude());
		assertNotNull(p.getLongitude());
	}

	@Test
	void testFindById_notFound() {
		SmokingPoint p = mapper.findById(9999L);
		assertNull(p);
	}
	
	@Test
	void testInsert() {
		SmokingPoint p = new SmokingPoint();
		p.setName("テスト喫煙所");
		p.setLatitude(34.12345);
		p.setLongitude(135.12345);
		p.setOpenTime(null);
		p.setCloseTime(null);
		p.setAddress(null);
		p.setDescription(null);
		
		int result = mapper.insert(p);
		assertEquals(1, result);
		
		assertTrue(p.getId()>0);
		SmokingPoint inserted = mapper.findById(p.getId());
		assertNotNull(inserted);
		assertEquals("テスト喫煙所",inserted.getName());
		assertEquals(34.12345,inserted.getLatitude());
		assertEquals(135.12345,inserted.getLongitude());
		assertNull(inserted.getOpenTime()); 
		assertNull(inserted.getCloseTime()); 
		assertNull(inserted.getAddress()); 
		assertNull(inserted.getDescription());
	}
	
	
	@Test
	void testUpdate() {
		SmokingPoint p = new SmokingPoint();
		p.setName("テスト喫煙所");
		p.setLatitude(34.12345);
		p.setLongitude(135.12345);
		p.setOpenTime("09:00");
		p.setCloseTime("18:00");
		mapper.insert(p);
		Long id = p.getId();
		assertTrue(id > 0);
		
		SmokingPoint u = new SmokingPoint();
		u.setId(id);
		u.setName("アップデート喫煙所");
		u.setLatitude(34.23456);
		u.setLongitude(135.23456);
		u.setOpenTime(null);
		u.setCloseTime(null);
		u.setAddress("大阪府堺市白鷺町1丁");
		u.setDescription("電子タバコのみ");
		
		int result =mapper.update(u);
		assertEquals(1,result);
		
		SmokingPoint updated = mapper.findById(id);
		assertNotNull(updated);
		assertEquals("アップデート喫煙所",updated.getName());
		assertEquals(34.23456,updated.getLatitude());
		assertEquals(135.23456,updated.getLongitude());
		assertNull(updated.getOpenTime()); 
		assertNull(updated.getCloseTime());
		assertEquals("大阪府堺市白鷺町1丁",updated.getAddress()); 
		assertEquals("電子タバコのみ",updated.getDescription());
		
	}
	
	@Test
	void testUpdate_notFound() {
		SmokingPoint p = new SmokingPoint();
		p.setId(9999L);
		p.setName("存在しない喫煙所");
		int result=mapper.update(p);
		assertEquals(0, result);
	}
	
	@Test
	void testDelete() {
		SmokingPoint p = new SmokingPoint();
		p.setName("テスト喫煙所");
		p.setLatitude(34.12345);
		p.setLongitude(135.12345);
		mapper.insert(p);
		Long id = p.getId();
		assertTrue(id > 0);
		
		int result = mapper.deleteById(id);
		assertEquals(1,result);
		
		SmokingPoint deleted = mapper.findById(id);
		assertNull(deleted);
	}
	
	@Test
	void testDelete_notFound() {
	    int result = mapper.deleteById(9999L);
	    assertEquals(0, result);
	}
	
	@Test
	void testFindAllLatLng() {
		List<LatLng> list=mapper.findAllLatLng();
		assertFalse(list.isEmpty());
		assertEquals(3, list.size());
		for (LatLng p : list) {
			assertNotNull(p.getLatitude());
			assertNotNull(p.getLongitude());
		}
	}

	@Test
	void testFindAllForMap() {
		List<SmokingPointMapDto> list=mapper.findAllForMap();
		assertFalse(list.isEmpty());
		assertEquals(3, list.size());
		for (SmokingPointMapDto p : list) {
			assertNotNull(p.getId());
			assertTrue(p.getId() > 0);
			assertNotNull(p.getName());
			assertFalse(p.getName().isBlank());
			assertNotNull(p.getLatitude());
			assertNotNull(p.getLongitude());
		}
	}
}
