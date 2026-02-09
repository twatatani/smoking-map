package com.example.smokingspotfinder.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.smokingspotfinder.entity.SmokingPoint;
import com.example.smokingspotfinder.repository.SmokingPointRepository;

@Service
public class ScrapingService {
	private static final double LOCATION_DELTA = 0.0001;
	private static final Logger log = LoggerFactory.getLogger(ScrapingService.class);
	private SmokingPointRepository smokingPointRepository;
	public ScrapingService(SmokingPointRepository smokingPointRepository) {
		this.smokingPointRepository = smokingPointRepository;
	}
	
	public void scrapeAndSave(String url) throws IOException{
		List<SmokingPoint> spList = scrapeAll(url);
		for(SmokingPoint sp : spList) {
			smokingPointRepository.insert(sp);
		}
		log.info("新規追加件数: {}", spList.size());
	}
	
	public List<SmokingPoint> scrapeAll(String url) throws IOException{
		List<SmokingPoint> spList= new ArrayList<>();
		Document doc = Jsoup.connect(url).get();
		Elements nameBlocks = doc.select("ul.ul_type01");
		for(Element nameBlock:nameBlocks) {
			Element nameEl = nameBlock.selectFirst("strong");
			if(nameEl == null) {
				continue;
			}
			Element detailP = nameBlock.nextElementSibling();
			if(detailP == null||!detailP.tagName().equals("p")) {
				continue;
			}
			SmokingPoint sp = scrapeOne(nameEl,detailP);
			if(sp == null) {
				continue;
			}
			spList.add(sp);
		}
		return spList;
	}
	
	public SmokingPoint scrapeOne(Element nameEl,Element detailP){
		SmokingPoint sp = new SmokingPoint();
		
		String desc = detailP.text();
		sp.setRawDescription(desc);
		
		extractName(nameEl,sp);
		desc = extractAddress(desc, sp);
		desc = extractOpenCloseTime(desc, sp);
		sp.setDescription(desc);

		String href = detailP.select("a").attr("href");
		if (href != null && !href.isEmpty()) {
		extractLatLngFromUrl(href, sp);
		}
		
		SmokingPoint existing = smokingPointRepository.findByLatLngApprox(sp.getLatitude(), sp.getLongitude(), LOCATION_DELTA);
		if(existing != null) {
			return null;
		}
		return sp;
	}
	
	private void extractName(Element nameEl, SmokingPoint sp) {
		if(nameEl == null) {
			throw new IllegalStateException("名称が取得できませんでした");
		}
		sp.setName(nameEl.text());
	}
	
	private String extractAddress(String desc,SmokingPoint sp) {
		Pattern patAddress = Pattern.compile("所在地：(.+?)(?=\\s|$)");
		Matcher matchAddress = patAddress.matcher(desc);
		if(matchAddress.find()) {
			String address = matchAddress.group(1).trim();
			sp.setAddress(address);
			desc =desc.replace("所在地："+address, "").trim();
		}
		return desc;
	}
	
	private void extractLatLngFromUrl(String url,SmokingPoint sp) {
		try {
			URI uri = new URI(url);
			String query = uri.getQuery();
			if (query == null || query.isEmpty()) {return;}
			String[] params = query.split("&");
			
			for(String param:params) {
				if(param.startsWith("mpx=")) {
					sp.setLongitude(Double.parseDouble(param.substring(4)));
				}
				if(param.startsWith("mpy=")) {
					sp.setLatitude(Double.parseDouble(param.substring(4)));
				}
			}
		} catch (URISyntaxException e) {
			log.error("緯度経度の抽出に失敗しました。URL: {}", url, e);
		}
	}
	
	private String extractOpenCloseTime(String desc, SmokingPoint sp) {
		Pattern patTime = Pattern.compile("供用時間：(.+?)(?=\\s|$)");
		Matcher matchTime = patTime.matcher(desc);
		if(matchTime.find()) {
			String time = matchTime.group(1).trim();
			parseOpenCloseTime(time, sp);
			desc = desc.replace("供用時間："+time, "").trim();
		}
		return desc;
	}
	
	private void parseOpenCloseTime(String timeText, SmokingPoint sp) {
	    Pattern timePattern = Pattern.compile("(\\d{1,2}(?:時\\d{1,2}分|時|[:：]\\d{1,2}))");
	    Matcher m = timePattern.matcher(timeText);

	    List<String> times = new ArrayList<>();
	    while (m.find()) {
	        times.add(m.group(1));
	    }

	    if (times.size() >= 1) sp.setOpenTime(times.get(0));
	    if (times.size() >= 2) sp.setCloseTime(times.get(1));
	}

}
