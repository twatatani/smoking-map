/**
 * mapを表示する
 */
var map = L.map('map').setView([34.573, 135.483], 13);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	maxZoom: 19,
	attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

//マーカーを置く
const markers = {};
fetch('/api/smoking-points')
	.then(response => response.json())
	.then(data => {
		data.forEach(p => {
			const marker = L.marker([p.latitude, p.longitude]).addTo(map);
			marker.bindPopup(`<a href="/smoking-points/${p.id}">${p.name}</a>`);
			marker.on('mouseover', () => { marker.openPopup(); });
			marker.on('mouseout', () => { marker.closePopup(); });
			marker.on('click', () => { window.location.href = `/smoking-points/${p.id}`; });
			markers[p.id] = marker;
		});
		// 一覧クリックで地図を移動 
		document.querySelectorAll('li[data-lat]').forEach(item => {
			item.addEventListener('click', () => {
				const lat = parseFloat(item.dataset.lat);
				const lng = parseFloat(item.dataset.lng);
				const id = item.dataset.id;
				map.setView([lat, lng], 17);
				const marker = markers[id];
				if (marker) {
					marker.openPopup();
				}
			});
		});
	});

window.onload = function() {

	const params = new URLSearchParams(window.location.search);
	if (params.has("lat") && params.has("lng")) {
		const lat = parseFloat(params.get("lat"));
		const lng = parseFloat(params.get("lng"));

		const myMarker = L.circleMarker([lat, lng], {
			radius: 8,
			color: '#2196F3',
			fillColor: '#2196F3',
			fillOpacity: 0.9
		}).addTo(map);

		myMarker.bindPopup("現在地");
		map.setView([lat, lng], 15);
		return;
	}

	navigator.geolocation.getCurrentPosition(function(pos) {
		const lat = pos.coords.latitude;
		const lng = pos.coords.longitude;

		window.location.href = `/smoking-points?lat=${lat}&lng=${lng}`;
	});
};
