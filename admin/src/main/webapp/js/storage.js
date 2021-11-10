const path = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');

$(document).on({
    ajaxStart: function () {
        $('#spinner').addClass("loading");
    },
    ajaxStop: function () {
        $('#spinner').removeClass("loading");
    }
});

const storage = {

    search: function (term, callback) {
        $.ajax({
            type: 'GET',
            url: path + "/admin/get/search/?q=" + term,
            success: function (data) {
                callback(data);
            }
        });
    },

    fetchAllLocations: function (successCallback, errorCallback) {
        $.ajax({
            type: 'GET',
            url: path + "/admin/get/locations/",
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                errorCallback();
            }
        });
    },

    fetchAllCategories: function (successCallback, errorCallback) {
        $.ajax({
            type: 'GET',
            url: path + "/admin/get/categories/",
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                errorCallback();
            }
        });
    },

    fetchLocations: function (locationId, callback) {
        $.ajax({
            type: 'GET',
            url: path + "/admin/get/locations/" + locationId,
            success: function (data) {
                callback(data);
            }
        });
    },

    fetchAllOffers: function (sortBy,successCallback, errorCallback) {
        $.ajax({
            type: 'GET',
            url: path + "/admin/get/offers/" +
                        (sortBy ? "?sortBy=" + sortBy : ""),
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                errorCallback();
            }
        });
    },

    fetchAllFacts: function (successCallback, errorCallback) {
        $.ajax({
            type: 'GET',
            url: path + "/admin/get/facts/",
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                errorCallback();
            }
        });
    },

    deleteFact: function (factId, callback) {
        $.ajax({
            type: 'DELETE',
            url: path + "/admin/delete/facts/" + factId,
            success: function (data) {
                callback(data);
            }
        });
    },

    deleteOffer: function (offerId, callback) {
        $.ajax({
            type: 'DELETE',
            url: path + "/admin/delete/offers/" + offerId,
            success: function (data) {
                callback(data);
            }
        });
    },

    deleteLocation: function (locationId, callback) {
        $.ajax({
            type: 'DELETE',
            url: path + "/admin/delete/locations/" + locationId,
            success: function (data) {
                callback(data);
            }
        });
    },

    deleteCategory: function (categoryId, callback) {
        $.ajax({
            type: 'DELETE',
            url: path + "/admin/delete/category/" + categoryId,
            success: function (data) {
                callback(data);
            }
        });
    },

    deleteSubcategory: function (subcategoryId, callback) {
        $.ajax({
            type: 'DELETE',
            url: path + "/admin/delete/subcategory/" + subcategoryId,
            success: function (data) {
                callback(data);
            }
        });
    },

    createLocation: function (locationToCreate, callback) {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(locationToCreate),
            dataType: 'json',
            url: path + "/admin/add/location",
            success: function (data) {
                callback(data);
            }
        });
    },

    createOffer: function (offerToCreate, callback) {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(offerToCreate),
            dataType: 'json',
            url: path + "/admin/add/offer",
            success: function (data) {
                callback(data);
            }
        });
    },

    createFact: function (factToCreate, callback) {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(factToCreate),
            dataType: 'json',
            url: path + "/admin/add/fact",
            success: function (data) {
                callback(data);
            }
        });
    },

    createCategory: function (categoryToCreate, callback) {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(categoryToCreate),
            dataType: 'json',
            url: path + "/admin/add/category",
            success: function (data) {
                callback(data);
            }
        });
    },

    createSubcategory: function (subcategoryToCreate, callback) {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(subcategoryToCreate),
            dataType: 'json',
            url: path + "/admin/add/subcategory",
            success: function (data) {
                callback(data);
            }
        });
    },

    updateLocation: function (location, callback) {
        $.ajax({
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(location),
            dataType: 'json',
            url: path + "/admin/update/location",
            success: function (data) {
                callback(data);
            }
        });
    },

    updateOffer: function (offer, successCallback, errorCallback) {
        $.ajax({
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(offer),
            dataType: 'json',
            url: path + "/admin/update/offer",
            success: function (data) {
                successCallback(data);
            },
            error: function (data) {
                errorCallback(data)
            }
        });
    },

    updateFact: function (fact, callback) {
        $.ajax({
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(fact),
            dataType: 'json',
            url: path + "/admin/update/fact",
            success: function (data) {
                callback(data);
            }
        });
    },

    updateFactStatus: function (factId, newStatus, callback) {
        $.ajax({
            type: 'PUT',
            contentType: "application/json",
            url: path + "/admin/update/fact/" + factId + "/status/" + newStatus,
            success: function (data) {
                callback(data);
            }
        });
    },

    updateOfferStatus: function (offerId, newStatus, callback) {
        $.ajax({
            type: 'PUT',
            contentType: "application/json",
            url: path + "/admin/update/offer/" + offerId + "/status/" + newStatus,
            success: function (data) {
                callback(data);
            }
        });
    },

    updateCategory: function (category, callback) {
        $.ajax({
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(category),
            dataType: 'json',
            url: path + "/admin/update/category",
            success: function (data) {
                callback(data);
            }
        });
    },

    updateSubcategory: function (subcategory, callback) {
        $.ajax({
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(subcategory),
            dataType: 'json',
            url: path + "/admin/update/subcategory",
            success: function (data) {
                callback(data);
            }
        });
    }
};
