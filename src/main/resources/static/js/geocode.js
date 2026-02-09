/**
 * 新規登録フォーム
 */
function searchAddress() {
    const address = document.getElementById("address").value;

    fetch("/geocode/api/search-address", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `address=${address}`
    })
    .then(res => res.json())
    .then(data => {
        if (data.error) {
            alert("住所が見つかりませんでした");
            return;
        }

        document.getElementById("latitude").value = data.latitude;
        document.getElementById("longitude").value = data.longitude;
    });
}
function reverseGeocode() {
    const lat = document.getElementById("latitude").value;
    const lng = document.getElementById("longitude").value;

    if (!lat || !lng) {
        alert("緯度と経度を入力してください");
        return;
    }

    fetch("/geocode/api/search-latlng", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `lat=${lat}&lng=${lng}`
    })
    .then(res => res.json())
    .then(data => {
        if (data.error) {
            alert("住所が見つかりませんでした");
            return;
        }

        document.getElementById("address").value = data.formattedAddress;
    });
}
function getCurrentLocation() {
    if (!navigator.geolocation) {
        alert("このブラウザは位置情報取得に対応していません");
        return;
    }

    navigator.geolocation.getCurrentPosition(
        pos => {
            document.getElementById("latitude").value = pos.coords.latitude;
            document.getElementById("longitude").value = pos.coords.longitude;
        },
        err => {
            console.error(err);
            alert("現在地を取得できませんでした");
        }
    );
}
