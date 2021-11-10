Array.prototype.getIndexBy = function (name, value) {
    for (let i = 0; i < this.length; i++) {
        if (this[i][name] === value) {
            return i;
        }
    }
    return -1;
};

//---------Vue.js----------

const notificationMixin = {
    methods: {
        successNotify: function (message) {
            $.notify({
                icon: 'glyphicon glyphicon-ok',
                message: message
            }, {
                type: 'success',
                offset: {
                    x: 20,
                    y: 50
                },
                placement: {
                    from: "top",
                    align: "right"
                }
            });
        },
        errorNotify: function (message) {
            $.notify({
                icon: 'glyphicon glyphicon-warning-sign',
                message: message
            }, {
                type: 'danger',
                offset: {
                    x: 20,
                    y: 50
                },
                placement: {
                    from: "top",
                    align: "right"
                }
            });
        },
        routerRedirect: function (path) {
            router.push(path);
        }
    }
};

const LocationMap = Vue.extend({

    template: '#add-location',

    mounted: function () {
        this.initialize(this);
    },

    data: function () {
        return {
            locations: [],
            latitude: '',
            longitude: '',
            address: '',
            name: ''
        }
    },

    methods: {

        initialize: function () {
            storage.fetchAllLocations(this.success, this.error)
        },

        success: function (data) {
            renderMap(data, this);
        },

        error: function () {
        },

        createLocation: function () {
            const self = this;
            const locationToCreate = {
                lat: self.latitude,
                lng: self.longitude,
                address: self.address,
                name: self.name
            };
            storage.createLocation(locationToCreate, this.createLocationCallback);
        },
        createLocationCallback: function (data) {
            router.push('/location/' + data.id);
        }
    }
});


const SearchResult = Vue.extend({

    template: '#search-template',

    mixins: [notificationMixin],

    data: function () {
        return {
            locations: []
        }
    },

    mounted: function () {
        this.search(this.$route.params.q);
    },

    watch: {
        '$route': function () {
            this.search(this.$route.params.q);
        }
    },

    methods: {
        search: function (term) {
            storage.search(term, this.searchCallback);
        },
        searchCallback: function (data) {
            this.locations = data;
        }
    }
});





const LocationsList = Vue.extend({

    mixins: [notificationMixin],

    template: '#list-template',

    data: function () {
        return {
            locations: [],
            showModal: false,
            factOffers: [],
            factOffer: {}
        }
    },

    mounted: function () {
        this.fetch();
    },

    methods: {
        fetch: function () {
            storage.fetchAllLocations(this.successCallback, this.errorCallback);
        },
        successCallback: function (data) {
            this.locations = data;

        },
        errorCallback: function (data) {
        }
    }
});


const CategoriesList = Vue.extend({

    template: '#categories-list-template',

    mixins: [notificationMixin],

    data: function () {
        return {
            category: {},
            subcategory: {},
            categories: [],
            showModal: false,

            //deletion state
            state: '',
            id: 0,

            showEditCategory: false,
            showEditSubcategory: false,
            editingCategory: {},
            editingSubcategory: {},
            showAddCategory: false,
            showAddSubcategory: false
        }
    },

    mounted: function () {
        this.fetch();
    },

    methods: {
        fetch: function () {
            storage.fetchAllCategories(this.successCallback,this.errorCallback);
        },
        successCallback: function (data) {
            this.categories = data;
        },
        errorCallback: function (data) {
        },

        createCategory: function () {
            const self = this;
            self.category.subcategories = [];
            storage.createCategory(self.category, function (data) {
                self.categories.push(data);
                self.showAddCategory = false;
            })
        },
        createSubcategory: function () {
            const self = this;
            debugger;
            storage.createSubcategory(self.subcategory, function (data) {
                const categoryForAddedSubcategory = _.find(self.categories, function (category) {
                    return category.id === self.subcategory.categoryId;
                });
                categoryForAddedSubcategory.subcategories.push(self.subcategory);
                self.showAddSubcategory = false;
            });
        }
    }
});

const Location = Vue.extend({

    template: '#view-location-template',

    mixins: [notificationMixin],

    mounted: function () {
        this.fetchLocation();
        this.fetchAllCategories();
        window.setTimeout(this.renderCategories, 500);

    },

    data: function () {
        return {
            thisLocation: {},
            fact: {},
            offer: {
                dates: [{
                    sessions: []
                }]
            },
            showAddFact: false,
            showAddOffer: false,
            showModal: false,
            showEditLocation: false,
            showEditOffer: false,
            showEditFact: false,
            showEditStatus: false,
            factOffers: [],
            factOffer: {},
            locationId: 0,
            subcategories: {},
            selected: '',
            daysOfWeek:[
                "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"
            ]
        }
    },

    methods: {
        fetchLocation: function () {
            const self = this;
            storage.fetchLocations(this.$route.params.id, function (data) {
                self.thisLocation = data;
            });
        },
        fetchAllCategories: function () {
            const self = this;
            storage.fetchAllCategories(function (data) {
                self.subcategories = data;
            });
        },
        createFact: function () {
            const self = this;
            self.fact.location = {};
            self.fact.location.id = self.thisLocation.id;
            storage.createFact(self.fact, function (data) {
                self.thisLocation.facts.push(data);
                self.showAddFact = false;
            });
            self.successNotify("Fact has been created successfully")

        },
        createOffer: function () {
            const self = this;
            self.offer.location = {};
            self.offer.location.id = self.thisLocation.id;
            self.offer.subcategories = [];
            const selectedCat = $('#example-getting-started option:selected');
            selectedCat.each(function () {
                self.offer.subcategories.push({id: this.value, name: this.innerHTML})
            });
            storage.createOffer(self.offer, function (data) {
                self.thisLocation.offers.push(data);
                self.showAddOffer = false;
                self.successNotify("Offer has been created successfully")
            });
        },
        renderCategories: function () {
            $('#example-getting-started').multiselect({
                enableClickableOptGroups: true,
                maxHeight: 200
            });
        },
        addDate: function () {
            this.offer.dates.push({
                sessions: []
            });
        },
        removeDate: function () {
            this.offer.dates.pop()
        },
        addTime: function (date) {
            date.sessions.push({});
        },
        removeTime: function (date) {
            date.sessions.pop();
        }

    }
});

const OffersList = Vue.extend({

    template: '#offers-list-template',

    mixins: [notificationMixin],

    data: function () {
        return {
            offers: [],
            showModal: false,
            selectedOffer: {},
            sortBy: null
        }
    },

    mounted: function () {
        this.fetch();
    },

  watch: {
    sortBy: function (sortBy) {
      this.fetch(sortBy);
    }
  },

    methods: {
        fetch: function (sortBy) {
            this.offers = storage.fetchAllOffers(sortBy,this.successCallback, this.errorCallback);
        },
        successCallback: function (data) {
            this.offers = data;
        },
        errorCallback: function (data) {
        }
    }

});

const FactsList = Vue.extend({

    template: '#facts-list-template',

    mixins: [notificationMixin],


    data: function () {
        return {
            facts: [],
            showModal: false,
            selectedFact: {}
        }
    },

    mounted: function () {
        this.fetch();
    },

    methods: {
        fetch: function () {
            this.facts = storage.fetchAllFacts(this.successCallback,this.errorCallback);
        },
        successCallback: function (data) {
            this.facts = data;
        },
        errorCallback: function () {
        }
    }

});

Vue.component('nav-bar', {
    template: '#nav-bar',
    data: function () {
        return {
            app_name: 'Mapoint',
            q: null
        }
    },

    methods: {
        search: function () {
            router.push({name: 'search', params: {q: this.q}})
        }
    }
});

Vue.component('modal', {
    template: '#modal-template',

    mixins: [notificationMixin],

    props: {
        show: {
            type: Boolean,
            required: true
        },

        factOffers: {
            type: Array
        },

        factOffer: {
            type: Object
        },

        locationId: {
            type: Number
        },

        deletionState: {
            type: String
        },

        itemId: {
            type: Number
        }
    },

    methods: {
        proceed: function () {
            const self = this;
            debugger;
            //IMPROVE Offers-facts deletion!!!
            if (self.deletionState === 'category') {
                storage.deleteCategory(self.itemId, self.close)
            } else if (self.deletionState === 'subcategory') {
                storage.deleteSubcategory(self.itemId, self.close)
            } else if (self.factOffer && self.factOffer.type === 'FACT') {
                storage.deleteFact(self.factOffer.id, self.callback)
            } else if (self.factOffer && self.factOffer.type === 'OFFER') {
                storage.deleteOffer(self.factOffer.id, self.callback)
            } else {
                storage.deleteLocation(self.locationId, self.deleteLocationCallback);
            }
        },

        deleteLocationCallback: function () {
            router.push('/location_list');
            this.successNotify("Successful operation")
        },

        callback: function () {
            const self = this;
            const i = self.factOffers.indexOf(self.factOffer);
            if (i > -1) {
                self.factOffers.splice(i, 1);
            }
            self.$emit('close');
        }
    }

});

Vue.component('edit-offer-modal', {
    template: '#modal-edit-offer-template',

    mixins: [notificationMixin],


    props: {
        show: {
            type: Boolean,
            required: true
        },

        offer: {
            type: Object,
            required: true
        }
    },

    data: function () {
        return {
            subcategories: {}
        }
    },

    watch: {
        show: function () {
            const self = this;
            if (self.show === true) {
                self.renderOfferCategories();
            }
        },
        subcategories: function () {
            setTimeout(function() {
                //ToDo: fix it.
                $('#categories-multiply').multiselect({
                    enableClickableOptGroups: true,
                    maxHeight: 200
                });
                $('#categories-multiply').multiselect('refresh');

            }, 200);
        }
    },

    methods: {

        proceed: function () {
            const self = this;
            self.offer.subcategories = [];
            const selectedCat = $('#categories-multiply').find('option:selected');
            selectedCat.each(function () {
                self.offer.subcategories.push({id: this.value})
            });
            storage.updateOffer(self.offer, self.success, self.error)
        },

        success: function () {
            const self = this;
            self.close();
            this.successNotify("Successful operation")

        },

        error: function () {
            const self = this;
            self.close();
            this.errorNotify("Unable to update offer")
        },

        close: function () {
            const self = this;
            self.$emit('close');
            this.$parent.fetchLocation();
        },

        renderOfferCategories: function () {
            const self = this;
            storage.fetchAllCategories(
                function (data) {
                    self.subcategories = data;
                },
                function (data) {
                    debugger;
                }
            );
        },
        addDate: function () {
            this.offer.dates.push({
                sessions: []
            });
        },
        removeDate: function () {
            this.offer.dates.pop()
        },
        addTime: function (date) {
            date.sessions.push({});
        },
        removeTime: function (date) {
            date.sessions.pop();
        }
    }

});

Vue.component('edit-fact-modal', {
    template: '#modal-edit-fact-template',

    mixins: [notificationMixin],

    props: {
        show: {
            type: Boolean,
            required: true,
        },

        fact: {
            type: Object,
            required: true
        }
    },

    methods: {
        proceed: function () {
            const self = this;
            storage.updateFact(self.fact, self.proceedCallback)
        },

        proceedCallback: function () {
            const self = this;
            self.close();
            this.successNotify("Successful operation")
        },

        close: function () {
            const self = this;
            self.$emit('close');
            this.$parent.fetchLocation();
        }
    }

});


Vue.component('edit-status-modal', {
    template: '#modal-edit-status-template',

    mixins: [notificationMixin],

    data: function () {
        return {
            status: true
        }
    },

    props: {
        show: {
            type: Boolean,
            required: true,
        },

        factOffer: {
            type: Object,
            required: true
        }
    },

    methods: {
        proceed: function (event) {
            const self = this;
            if (self.factOffer.type === 'OFFER') {
                storage.updateOfferStatus(self.factOffer.id, self.status, self.proceedCallback)
            } else if (self.factOffer.type === 'FACT') {
                storage.updateFactStatus(self.factOffer.id, self.status, self.proceedCallback)
            }
            event.preventDefault()
        },

        proceedCallback: function () {
            const self = this;
            self.close();
            this.successNotify("Successful operation")
        },

        close: function () {
            const self = this;
            self.$emit('close');
            this.$parent.fetchLocation();
        }
    }

});

Vue.component('edit-location-modal', {
    template: '#modal-edit-location-template',
    mixins: [notificationMixin],
    props: {
        show: {
            type: Boolean,
            required: true,
        },

        location: {
            type: Object,
            required: true
        },

        daysOfWeek:{
            type: Array,
            required: true
        }
    },

    watch: {
        show: function () {
            const self = this;
            initializeLocationEdit(self.location);
        }
    },

    methods: {

        proceed: function () {
            const self = this;
            storage.updateLocation(self.location, self.proceedCallback)
        },

        proceedCallback: function () {
            const self = this;
            self.close();
            this.successNotify("Successful operation")
        },

        close: function () {
            const self = this;
            self.$emit('close');
            this.$parent.fetchLocation();
        },

        addPhone: function(){
            if(this.location.phones){
                this.location.phones.push('')
            } else {
                this.location.phones = []
            }
        },

        addWorkingTimes: function () {
            if (this.location.workingTimes.length === 0) {
                this.location.workingTimes = [{dayNumber: 0}, {dayNumber: 1}, {dayNumber: 2}, {dayNumber: 3}, {dayNumber: 4}, {dayNumber: 5}, {dayNumber: 6}]
            }
        }

    }

});

Vue.component('edit-category-modal', {
    template: '#modal-edit-category-template',
    mixins: [notificationMixin],
    props: {
        show: {
            type: Boolean,
            required: true,
        },

        category: {
            type: Object,
            required: true
        }
    },

    methods: {
        proceed: function () {
            const self = this;
            storage.updateCategory(self.category, self.proceedCallback)
        },

        proceedCallback: function () {
            const self = this;
            self.close();
            this.successNotify("Successful operation")
        },

        close: function () {
            const self = this;
            self.$emit('close');
        }
    }

});

Vue.component('edit-subcategory-modal', {
    template: '#modal-edit-subcategory-template',
    mixins: [notificationMixin],
    props: {
        show: {
            type: Boolean,
            required: true
        },

        subcategory: {
            type: Object,
            required: true
        }
    },

    data: function () {
        return {
            categories: {}
        }
    },

    watch: {
        show: function () {
            const self = this;
            if (self.show === true) {
                self.renderCategories();
            }
        }
    },

    methods: {
        proceed: function () {
            const self = this;
            storage.updateSubcategory(self.subcategory, self.proceedCallback)
        },

        proceedCallback: function () {
            const self = this;
            self.close();
            this.successNotify("Successful operation")
        },

        close: function () {
            this.$emit('close');
        },

        renderCategories: function () {
            const self = this;
            storage.fetchAllCategories(function (data) {
                self.categories = data;
            });
        }
    }

});


const routes = [
    { path: '/', component: LocationMap },
    { path: '/location_list',  component: LocationsList },
    { path: '/offer_list',  component: OffersList },
    { path: '/fact_list', component: FactsList },
    { path: '/category_list', component: CategoriesList },
    { path: '/location/:id', component: Location },
    { path: '/search/:q',name:'search', component: SearchResult}
];

const router = new VueRouter({routes});


const app = new Vue({
    router: router
}).$mount('#app');