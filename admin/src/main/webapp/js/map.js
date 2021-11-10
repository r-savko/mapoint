var lastMarker, lastMarkerForEdit, map, bounds;
var geocoder;


function renderMap(data, vueLocationsObject) {
    geocoder = new google.maps.Geocoder();
    bounds = new google.maps.LatLngBounds();
    var mapOptions = {
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    // Display a map on the page
    map = new google.maps.Map(document.getElementById("map"), mapOptions);
    map.setTilt(45);

    renderLocations(data);

    google.maps.event.addListener(map, 'rightclick', function (event) {
        placeMarker(event.latLng, vueLocationsObject);
    });

    // Create the search box and link it to the UI element.
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });


    var markers = [];
    // [START region_getplaces]
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function (place) {
            var icon = {
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(25, 25)
            };

            // Create a marker for each place.
            markers.push(new google.maps.Marker({
                map: map,
                icon: icon,
                title: place.name,
                position: place.geometry.location
            }));

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });

}

function initializeLocationEdit(loc) {
    geocoder = new google.maps.Geocoder();

    var mapOptions = {
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    // Display a map on the page
    var editLocationMap = new google.maps.Map(document.getElementById("editLocationMap"), mapOptions);
    var position = new google.maps.LatLng(loc.lat, loc.lng);
    editLocationMap.setZoom(17);
    editLocationMap.panTo(position);

    var marker = new google.maps.Marker({
        position: position,
        map: editLocationMap
    });

    google.maps.event.addListener(editLocationMap, 'rightclick', function (event) {

        if (lastMarkerForEdit) {
            lastMarkerForEdit.setMap(null);
        }

        var lat = event.latLng.lat();
        var lng = event.latLng.lng();

        loc.lat = lat;
        loc.lng = lng;

        geocoder.geocode({'latLng': event.latLng}, function (data, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                //this is the full address
                loc.address = data[0].formatted_address;
            }
        });

        lastMarkerForEdit = new google.maps.Marker({
            position: event.latLng,
            map: editLocationMap,
            icon: image
        });
    });

}

function renderLocations(data) {
    // Display multiple markers on a map
    var infoWindow = new google.maps.InfoWindow(), marker, i;

    // Loop through our array of markers & place each one on the map
    for (i = 0; i < data.length; i++) {
        var position = new google.maps.LatLng(data[i].lat, data[i].lng);
        bounds.extend(position);
        marker = new google.maps.Marker({
            position: position,
            map: map,
            title: data[i].name
        });

        // Allow each marker to have an info window
        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                infoWindow.setContent('<p>' + data[i].type + '</p><p>' + data[i].name + '</p><p>' + data[i].address + '</p><a href="/#/location/' + data[i].id + '">View</a>');
                infoWindow.open(map, marker);
            }
        })(marker, i));

    }
    // Automatically center the map fitting all markers on the screen
    map.fitBounds(bounds);

    // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
    var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function (event) {
        google.maps.event.removeListener(boundsListener);
    });
}

function placeMarker(location, vueLocationsObject) {

    if (lastMarker) {
        lastMarker.setMap(null);
    }

    var lat = location.lat();
    var lng = location.lng();


    vueLocationsObject.latitude = lat;
    vueLocationsObject.longitude = lng;

    //$('#latitude').val(lat);
    // $('#longitude').val(lng);


    geocoder.geocode({'latLng': location}, function (data, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var add = data[0].formatted_address; //this is the full address
            vueLocationsObject.address = add;

            //$('#address').val(add);
        }
    });

    lastMarker = new google.maps.Marker({
        position: location,
        map: map,
        icon: image
    });
}

var image = {
    url: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
};