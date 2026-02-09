package com.example.smokingspotfinder.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.smokingspotfinder.dto.LatLng;
import com.example.smokingspotfinder.dto.SmokingPointMapDto;
import com.example.smokingspotfinder.entity.SmokingPoint;
import com.example.smokingspotfinder.service.SmokingPointService;

@SpringBootTest
@Transactional
public class SmokingPointServiceImplTest {
	@Autowired
	SmokingPointService spService;
	
	@Test
	void testFindAll() {
		List<SmokingPoint> list = spService.findAll();
		assertNotNull(list);
		assertFalse(list.isEmpty());		
	}
	
	@Test
	void testFindById() {
		SmokingPoint sp = spService.findById(1L);
		assertNotNull(sp);
		assertEquals(1L, sp.getId());
	}
	
	@Test
	void testInsert() {
		SmokingPoint sp = new SmokingPoint();
		sp.setName("テスト喫煙所");
		sp.setLatitude(34.12345);
		sp.setLongitude(135.12345);
		
		spService.register(sp);
		assertNotNull(sp.getId());
		assertTrue(sp.getId() > 0);

	}
	
	@Test
	void testUpdate() {
		SmokingPoint p = new SmokingPoint();
		p.setName("テスト喫煙所");
		p.setLatitude(34.12345);
		p.setLongitude(135.12345);
		p.setOpenTime("09:00");
		p.setCloseTime("18:00");
		spService.register(p);
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
		
		spService.update(u);
		
		SmokingPoint updated = spService.findById(id);
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
	void testDeleteById() {
		SmokingPoint sp = new SmokingPoint();
		sp.setName("テスト喫煙所");
		sp.setLatitude(34.12345);
		sp.setLongitude(135.12345);
		spService.register(sp);
		Long id = sp.getId();
		assertTrue(id > 0);
		
		spService.delete(id);
		SmokingPoint deleted = spService.findById(id); 
		assertNull(deleted);
	}
	
	@Test
	void testFindAllLatLng() {
	    List<LatLng> list = spService.findAllLatLng();
	    assertNotNull(list);
	    assertFalse(list.isEmpty());
	}

	@Test
	void testFindAllForMap() {
		List<SmokingPointMapDto> list = spService.findAllForMap();
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
}
