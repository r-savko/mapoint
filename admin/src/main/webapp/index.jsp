<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Mapoint</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Mapoint</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/board.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css"/>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js" defer></script>
    <script type="text/javascript" src="js/bootstrap-multiselect.js" defer></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js" defer></script>

</head>

<body>

<section id="app">
    <div id="spinner"></div>
    <nav-bar></nav-bar>
    <div class="container-fluid">
        <router-view></router-view>
    </div>
</section>

<template id="nav-bar">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">{{app_name}}</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <router-link :to="{ path: '/' }">Location map</router-link>
                    </li>
                    <li>
                        <router-link :to="{ path: '/location_list' }">Location list</router-link>
                    </li>
                    <li>
                        <router-link :to="{ path: '/offer_list' }">Offers</router-link>
                    </li>
                    <li>
                        <router-link :to="{ path: '/fact_list' }">Facts</router-link>
                    </li>
                    <li>
                        <router-link :to="{ path: '/category_list' }">Categories</router-link>
                    </li>
                </ul>
                <div class="navbar-form navbar-right">
                    <input type="text" class="form-control" placeholder="Search..." v-on:keyup.enter="search" v-model="q">
                </div>
            </div>
        </div>
    </nav>
</template>

<template id="add-location">

    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">

            <label style="width: 100%">
                Latitude
                <input id="latitude" name="latitude" type="text" class="form-control" v-model="latitude">
            </label>
            <label style="width: 100%">
                Longitude
                <input id="longitude" name="longitude" type="text" class="form-control" v-model="longitude">
            </label>
            <label style="width: 100%">
                Address
                <input id="address" name="address" type="text" class="form-control" v-model="address">
            </label>
            <label style="width: 100%">
                Name of place
                <input id="name" name="name" type="text" class="form-control" v-model="name">
            </label>

            <button type="button" class="btn btn-default form-control" v-on:click="createLocation">Create</button>

        </div>
        <div class="col-xs-12 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <input id="pac-input" class="controls" type="text" placeholder="Search Box">
            <div id="map" class="map-content"></div>
        </div>
    </div>
</template>

<template id="list-template">
    <div>
        <modal :show="showModal" :fact-offers="factOffers" :fact-offer="factOffer" @close="showModal = false"></modal>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Address</th>
                    <th>Type</th>
                    <th>Name</th>
                    <th>Offers</th>
                    <th>Facts</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>
                <tr v-for="item in locations"  v-on:click="routerRedirect('/location/' + item.id)">
                    <td>{{item.address}}</td>
                    <td>{{item.type}}</td>
                    <td>{{item.name}}</td>
                    <td>{{item.offers.length}}</td>
                    <td>{{item.facts.length}}</td>
                    <td>{{item.lat}}</td>
                    <td>{{item.lng}}</td>
                    <td>
                        <a class="glyphicon glyphicon-eye-open" v-bind:href="'/#/location/' + item.id"></a>
                    </td>
                </tr>
                </tbody>

            </table>
        </div>
    </div>
</template>



<template id="search-template">

    <div>

        <h2>Searching for "{{$route.params.q}}"</h2><br>

        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Address</th>
                    <th>Type</th>
                    <th>Name</th>
                    <th>Offers</th>
                    <th>Facts</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>
                <tr v-for="item in locations" v-on:click="routerRedirect('/location/' + item.id)">
                    <td>{{item.address}}</td>
                    <td>{{item.type}}</td>
                    <td>{{item.name}}</td>
                    <td>{{item.offers.length}}</td>
                    <td>{{item.facts.length}}</td>
                    <td>{{item.lat}}</td>
                    <td>{{item.lng}}</td>
                    <td>
                        <a class="glyphicon glyphicon-eye-open" v-bind:href="'/#/location/' + item.id"></a>
                    </td>
                </tr>
                </tbody>

            </table>
        </div>
    </div>
</template>

<template id="offers-list-template">
    <div>
        <div class="form-horizontal" style="margin-top:10px">
            <div class="form-group">
                <label class="col-md-11 control-label">Sort</label>
                <div class="col-sm-1">
                <select class="form-control input-sm" id="offer_sort_by" v-model="sortBy"
                        @change="routerRedirect('/offer_list/')">
                    <option>Approved</option>
                    <option>Category</option>
                </select>
                </div>
            </div>
        </div>

        <modal :show="showModal" :fact-offers="offers" :fact-offer="selectedOffer" @close="showModal = false"></modal>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Address</th>
                    <th>Type</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Text</th>
                    <th>Full text</th>
                    <th>Date</th>
                    <th>Link</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>
                <tr v-for="offer in offers" v-on:click="routerRedirect('/location/' + offer.location.id)">
                    <td>{{offer.location.address}}</td>
                    <td>{{offer.location.type}}</td>
                    <td>{{offer.location.name}}</td>
                    <td>
                        <div v-if="offer.subcategories.length">
                            <p v-for="c in offer.subcategories">{{c.name}}</p>
                        </div>
                        <div v-else>
                            <span class="label label-danger">Empty categories</span>
                        </div>
                    </td>
                    <td>{{offer.text}}</td>
                    <td class="col-md-6">
                        <div v-if="offer.fullText && offer.fullText.length">
                            <p style="word-wrap:break-word;">
                                {{offer.fullText}}
                            </p>
                        </div>
                        <div v-else>
                            <span class="label label-danger">Empty description</span>
                        </div>
                    </td>
                    <td class="col-md-3">
                        <template v-for="date in offer.dates">
                            <div>
                                {{date.startDate}} - {{date.endDate}}
                                <template v-for="sessions in date.sessions">
                                    <span class="label label-primary">{{sessions.time}}</span>
                                </template>
                                <br/>
                            </div>
                        </template>
                    </td>
                    <td>
                        <a v-if="offer.link" v-bind:href="offer.link" v-on:click.stop>Link</a>
                    </td>
                    <td>
                        <div v-if="offer.approved">
                            <span class="label label-success">Approved</span>
                        </div>
                        <div v-else>
                            <span class="label label-danger">Moderation</span>
                        </div>
                    </td>
                    <td>
                        <a class="glyphicon glyphicon-eye-open" v-bind:href="'/#/location/'+offer.location.id" v-on:click.stop></a>
                        <a class="glyphicon glyphicon-remove" v-on:click="showModal=true, selectedOffer=offer" v-on:click.stop></a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<template id="facts-list-template">
    <div>
        <modal :show="showModal" :fact-offers="facts" :fact-offer="selectedFact" @close="showModal = false"></modal>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Address</th>
                    <th>Name</th>
                    <th>Text</th>
                    <th>Link</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>

                <tr v-for="fact in facts">
                    <td>{{fact.location.address}}</td>
                    <td>{{fact.location.name}}</td>
                    <td>{{fact.text}}</td>
                    <td>
                        <a v-if="fact.link" v-bind:href="fact.link" v-on:click.stop>Link</a>
                    </td>
                    <td>
                        <div v-if="fact.approved">
                            <span class="label label-success">Approved</span>
                        </div>
                        <div v-else>
                            <span class="label label-danger">Moderation</span>
                        </div>
                    </td>
                    <td>
                        <a class="glyphicon glyphicon-eye-open" v-bind:href="'/#/location/'+fact.location.id" v-on:click.stop></a>
                        <a class="glyphicon glyphicon-remove" v-on:click="showModal=true, selectedFact=fact" v-on:click.stop></a>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</template>


<template id="categories-list-template">
    <div>
        <%-- IMPROVE ATTRIBUTES!!! --%>
        <modal :show="showModal" :item-id="id" :deletion-state="state" @close="showModal = false"></modal>

        <edit-category-modal :show="showEditCategory" :category="editingCategory" @close="showEditCategory = false"></edit-category-modal>
        <edit-subcategory-modal :show="showEditSubcategory" :subcategory="editingSubcategory" @close="showEditSubcategory = false"></edit-subcategory-modal>

        <div class="btn-group container" style="margin-bottom: 20px; padding-top: 30px">
            <button type="button" class="btn btn-primary btn-sm"
                    v-on:click="showAddCategory=!showAddCategory, showAddSubcategory=false">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add category
            </button>

            <button type="button" class="btn btn-primary btn-sm"
                    v-on:click="showAddSubcategory=!showAddSubcategory, showAddCategory=false">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add subcategory
            </button>

        </div>

        <div class="form-horizontal col-md-9 " v-if="showAddCategory" style="float: none">

            <div class="form-group">
                <label class="col-sm-1 control-label">Name</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control input-sm" v-model="category.name"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label">Name</label>
                <div class="col-sm-5">
                    <input type="color" class="form-control input-sm" v-model="category.color"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label">Link to icon</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control input-sm" v-model="category.icon"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-1 col-sm-5">
                    <button type="button" class="btn btn-success form-control btn-sm" v-on:click="createCategory">Create
                    </button>
                </div>
            </div>

        </div>

        <div class="form-horizontal col-md-9 " v-if="showAddSubcategory" style="float: none">

            <div class="form-group">
                <label class="col-sm-1 control-label">Name</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control input-sm" v-model="subcategory.name"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label">Category</label>
                <div class="col-sm-6 col-md-4">
                    <select id="category-for-subcategory" class="form-control" v-model="subcategory.categoryId">
                        <option v-for="cat in categories" v-bind:value="cat.id">
                            {{cat.name}}
                        </option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-1 col-sm-5">
                    <button type="button" class="btn btn-success form-control btn-sm" v-on:click="createSubcategory">Create
                    </button>
                </div>
            </div>

        </div>

        <div v-for="cat in categories">
            <div class="panel panel-default">
                <div class="panel-heading">
                    {{cat.name}}
                    <a class="glyphicon glyphicon-edit" v-on:click="showEditCategory=true, editingCategory=cat"></a>
                    <a class="glyphicon glyphicon-remove" v-on:click="showModal=true, id=cat.id, state='category'"></a>
                    <span class="glyphicon glyphicon-tint" v-bind:style="{color: cat.color}"></span>
                    <img v-if="cat.icon" class="glyphicon" v-bind:style="{fill: cat.color}" v-bind:src="cat.icon"/>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item" v-for="subcat in cat.subcategories">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-sm-11">{{subcat.name}}</div>
                                    <div class="col-sm-1">
                                        <a class="glyphicon glyphicon-edit"
                                           v-on:click="showEditSubcategory=true, editingSubcategory=subcat"></a>
                                        <a class="glyphicon glyphicon-remove"
                                           v-on:click="showModal=true, id=subcat.id, state='subcategory'"></a>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>


<template id="view-location-template">
    <div>

        <modal :show="showModal" :fact-offers="factOffers" :fact-offer="factOffer"
               :location-id="locationId" @close="showModal = false"></modal>

        <edit-offer-modal :show="showEditOffer" :offer="factOffer" @close="showEditOffer = false"></edit-offer-modal>
        <edit-fact-modal :show="showEditFact" :fact="factOffer" @close="showEditFact = false"></edit-fact-modal>
        <edit-location-modal :show="showEditLocation" :location="thisLocation" :days-of-week="daysOfWeek" @close="showEditLocation = false"></edit-location-modal>
        <edit-status-modal :show="showEditStatus" :fact-offer="factOffer" @close="showEditStatus = false"></edit-status-modal>

        <div class="row">
            <div class="col-md-3">

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th colspan="2">Location info</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Id</td>
                        <td>{{thisLocation.id}}</td>
                    </tr>
                    <tr>
                        <td>Address</td>
                        <td>{{thisLocation.address}}</td>
                    </tr>
                    <tr>
                        <td>Latitude</td>
                        <td>{{thisLocation.lat}}</td>
                    </tr>
                    <tr>
                        <td>Longitude</td>
                        <td>{{thisLocation.lng}}</td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td>{{thisLocation.name}}</td>
                    </tr>
                    <tr>
                        <td>Type</td>
                        <td>{{thisLocation.type}}</td>
                    </tr>
                    </tbody>
                </table>

            </div>
            <div class="col-md-2">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Phones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="phone in thisLocation.phones">
                        <td>{{phone}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-md-2">

                <table class="table">
                    <thead>
                    <tr>
                        <th>Day</th>
                        <th>Start</th>
                        <th>End</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="time in thisLocation.workingTimes">
                        <td>{{daysOfWeek[time.dayNumber]}}</td>

                        <td v-if="time.weekend" colspan="2" style="text-align: center">Выходной</td>
                        <td v-if="time.fullTime" colspan="2" style="text-align: center">Круглосуточно</td>
                        <td v-if="!time.weekend && !time.fullTime">
                            <div>{{time.startTime}}</div>
                        </td>
                        <td v-if="!time.weekend && !time.fullTime">
                            <div> {{time.endTime}}</div>
                        </td>
                    </tr>
                    </tbody>
                </table>

            </div>

        </div>


        <div class="row">
            <div class="btn-group" style="margin-bottom: 20px;">
                <button type="button" class="btn btn-primary btn-sm" v-on:click="showAddFact=!showAddFact, showAddOffer=false">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add fact
                </button>

                <button type="button" class="btn btn-primary btn-sm" v-on:click="showAddOffer=!showAddOffer, showAddFact=false">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add offer
                </button>

                <button type="button" class="btn btn-primary btn-sm" v-on:click="showEditLocation=true">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Edit location
                </button>

                <button type="button" class="btn btn-danger btn-sm"
                        v-on:click="showModal=true, locationId=thisLocation.id, deleteLocation">
                    <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Delete location
                </button>
            </div>
        </div>

        <div class="row">
            <div class="form-horizontal col-md-9 " v-if="showAddFact">

                <div class="form-group">
                    <label class="col-sm-1 control-label">Text</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" v-model="fact.text"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-1 control-label">Link</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" v-model="fact.link"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-5">
                        <button type="button" class="btn btn-success form-control btn-sm" v-on:click="createFact">Create
                        </button>
                    </div>
                </div>

            </div>
        </div>

        <div class="row">
            <div class="form-horizontal col-md-9" v-show="showAddOffer" v-on:change="renderCategories">

                <div class="form-group">
                    <label class="col-sm-1 control-label">Text</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" v-model="offer.text"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-1 control-label">Full text</label>
                    <div class="col-sm-5">
                        <textarea type="text" class="form-control input-sm" v-model="offer.fullText"></textarea>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-1 control-label">Link</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control input-sm" v-model="offer.link"/>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-sm-5 col-sm-offset-1">
                        <button type="button" class="btn btn-sm" v-on:click="addDate">
                            <span class="glyphicon glyphicon-plus"></span> Add date
                        </button>
                        <button type="button" class="btn btn-sm" v-on:click="removeDate">
                            <span class="glyphicon glyphicon-minus"></span> Remove date
                        </button>
                    </div>
                </div>


                <template v-for="(date,index) in offer.dates">
                    <div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">Date range{{ index + 1 }}</label>
                            <div class="col-sm-5">
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <input type="date" class="form-control input-sm" style="width: 50%" v-model="date.startDate"/>
                                        <input type="date" class="form-control input-sm" style="width: 50%" v-model="date.endDate"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-5 col-sm-offset-1">
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-sm" v-on:click="addTime(date)">
                                            <span class="glyphicon glyphicon-plus"></span> Add time
                                        </button>
                                        <button type="button" class="btn btn-sm" v-on:click="removeTime(date)" v-if="date.sessions">
                                            <span class="glyphicon glyphicon-plus"></span> Remove time
                                        </button>
                                    </div>

                                    <div v-for="sessions in date.sessions">
                                        <input type="time" class="form-control input-sm" style="width: 110px" v-model="sessions.time"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </template>

                <div class="form-group">
                    <label class="col-sm-1 control-label">Category</label>
                    <div class="col-sm-5">
                        <select id="example-getting-started" multiple="multiple">
                            <optgroup v-for="item in subcategories" v-bind:label="item.name">
                                <option v-for="subitem in item.subcategories" v-bind:value="subitem.id">{{subitem.name}}
                                </option>
                            </optgroup>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-5">
                        <button type="button" class="btn btn-success form-control btn-sm" v-on:click="createOffer">Create
                        </button>
                    </div>
                </div>

            </div>
        </div>

        <div class="row">

            <div class="table-responsive">

                <table class="table table-hover" v-if="thisLocation.facts && thisLocation.facts.length > 0 ">

                    <thead>
                    <tr>
                        <th colspan="10" class="info">Facts</th>
                    </tr>
                    <tr>
                        <th>Text</th>
                        <th>Link</th>
                        <th><span class="glyphicon glyphicon-heart"></span></th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>


                    <tr v-for="fact in thisLocation.facts" v-on:click.prevent="showEditFact=true, factOffer=fact">
                        <td>{{fact.text}}</td>
                        <td>
                            <a v-if="fact.link" v-bind:href="fact.link" v-on:click.stop>Link</a>
                        </td>
                        <td>{{fact.likes}}</td>
                        <td v-on:click.stop="showEditStatus=true, factOffer=fact">
                            <div v-if="fact.approved">
                                <span class="label label-success">Approved</span>
                            </div>
                            <div v-else>
                                <span class="label label-danger">Moderation</span>
                            </div>
                        </td>
                        <td>
                            <a class="glyphicon glyphicon-edit" v-on:click.stop="showEditFact=true, factOffer=fact"></a>
                            <a class="glyphicon glyphicon-remove"
                               v-on:click.stop="showModal=true, factOffers=thisLocation.facts, factOffer=fact"></a>
                        </td>
                    </tr>

                </table>
            </div>

            <div class="table-responsive">

                <table class="table table-hover" v-if="thisLocation.offers && thisLocation.offers.length > 0">


                    <thead>
                    <tr>
                        <th colspan="10" class="info">Offers</th>
                    </tr>
                    <tr>
                        <th>Category</th>
                        <th>Text</th>
                        <th>Full text</th>
                        <th>Dates</th>
                        <th>Link</th>
                        <th><span class="glyphicon glyphicon-heart"></span></th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tr v-for="offer in thisLocation.offers" v-on:click="showEditOffer=true, factOffer=offer">
                        <td class="col-md-1">
                            <div v-if="offer.subcategories.length">
                                <p v-for="c in offer.subcategories">{{c.name}}</p>
                            </div>
                            <div v-else>
                                <span class="label label-danger">Empty categories</span>
                            </div>
                        </td>
                        <td class="col-md-1">{{offer.text}}</td>
                        <td class="col-md-6">
                            <div v-if="offer.fullText && offer.fullText.length">
                                <p style="word-wrap:break-word;">
                                    {{offer.fullText}}
                                </p>
                            </div>
                            <div v-else>
                                <span class="label label-danger">Empty description</span>
                            </div>
                        </td>
                        <td class="col-md-2">
                            <template v-for="date in offer.dates">
                                <div>
                                    {{date.startDate}} - {{date.endDate}}
                                    <template v-for="sessions in date.sessions">
                                        <span class="label label-primary">{{sessions.time}}</span>
                                    </template>
                                    <br/>
                                </div>
                            </template>
                        </td>
                        <td>
                            <a v-if="offer.link" v-bind:href="offer.link" v-on:click.stop>Link</a>
                        </td>
                        <td>{{offer.likes}}</td>
                        <td v-on:click.stop="showEditStatus=true, factOffer=offer">
                            <div v-if="offer.approved">
                                <span class="label label-success">Approved</span>
                            </div>
                            <div v-else>
                                <span class="label label-danger">Moderation</span>
                            </div>

                        </td>
                        <td>
                            <a class="glyphicon glyphicon-edit" v-on:click.stop="showEditOffer=true, factOffer=offer"></a>
                            <a class="glyphicon glyphicon-remove"
                               v-on:click.stop="showModal=true, factOffers=thisLocation.offers, factOffer=offer"></a>
                        </td>
                    </tr>

                </table>
            </div>

        </div>
    </div>
</template>

<template id="modal-template">
    <div class="modal-mask" v-show="show" transition="modal">
        <div class="modal-wrapper">
            <div class="modal-container">
                <div class="modal-header">
                    Are you sure?
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" @click="proceed">
                        Yes
                    </button>
                    <button class="btn btn-danger" @click="$emit('close')">
                        No
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<template id="modal-edit-offer-template">
    <div class="modal-mask" v-show="show" transition="modal">
        <div class="modal-wrapper">
            <div class="modal-container" style="width: 800px">
                <div class="modal-header">
                    Edit offer
                </div>
                <div class="modal-body" style="overflow-y: scroll; height:500px;">

                    <div class="container-fluid row">

                        <div class="form-horizontal">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">Text</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control input-sm" v-model="offer.text"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">Category</label>
                                <div class="col-sm-9">

                                    <select id="categories-multiply" multiple="multiple">
                                        <optgroup v-for="item in subcategories" v-bind:label="item.name">
                                            <option v-for="subitem in item.subcategories"
                                                    :selected="offer.subcategories.getIndexBy('id', subitem.id)>=0"
                                                    v-bind:value="subitem.id">{{subitem.name}}
                                            </option>
                                        </optgroup>
                                    </select>

                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">Full text</label>
                                <div class="col-sm-9">
                                    <textarea style="height: 200px" type="text" class="form-control input-sm"
                                              v-model="offer.fullText"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">Link</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control input-sm" v-model="offer.link"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-9 col-sm-offset-2">
                                    <button type="button" class="btn btn-sm" v-on:click="addDate">
                                        <span class="glyphicon glyphicon-plus"></span> Add date
                                    </button>
                                    <button type="button" class="btn btn-sm" v-on:click="removeDate">
                                        <span class="glyphicon glyphicon-minus"></span> Remove date
                                    </button>
                                </div>
                            </div>


                            <template v-for="(date,index) in offer.dates">
                                <div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Date range{{ index + 1 }}</label>
                                        <div class="col-sm-9">
                                            <div class="input-group">
                                                <div class="input-group-btn">
                                                    <input type="date" class="form-control input-sm" style="width: 50%" v-model="date.startDate"/>
                                                    <input type="date" class="form-control input-sm" style="width: 50%" v-model="date.endDate"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-9 col-sm-offset-2">
                                            <div class="input-group">
                                                <div class="input-group-btn">
                                                    <button type="button" class="btn btn-sm" v-on:click="addTime(date)">
                                                        <span class="glyphicon glyphicon-plus"></span> Add time
                                                    </button>
                                                    <button type="button" class="btn btn-sm" v-on:click="removeTime(date)" v-if="date.sessions">
                                                        <span class="glyphicon glyphicon-plus"></span> Remove time
                                                    </button>
                                                </div>

                                                <div v-for="sessions in date.sessions">
                                                    <input type="time" class="form-control input-sm" style="width: 110px" v-model="sessions.time"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </template>

                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" @click="proceed">
                        Save
                    </button>
                    <button class="btn btn-danger" @click="close">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<template id="modal-edit-fact-template">
    <div class="modal-mask" v-show="show" transition="modal">
        <div class="modal-wrapper">
            <div class="modal-container">
                <div class="modal-header">
                    Edit fact
                </div>
                <div class="modal-body">
                    <label style="width: 100%">
                        Text
                        <input name="latitude" type="text" class="form-control" v-model="fact.text">
                    </label>
                    <label style="width: 100%">
                        Link
                        <input name="longitude" type="text" class="form-control" v-model="fact.link">
                    </label>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" @click="proceed">
                        Save
                    </button>
                    <button class="btn btn-danger" @click="close">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<template id="modal-edit-category-template">
    <div class="modal-mask" v-show="show" transition="modal">
        <div class="modal-wrapper">
            <div class="modal-container">
                <div class="modal-header">
                    Edit category
                </div>
                <div class="modal-body">
                    <label style="width: 100%">
                        Name
                        <input type="text" class="form-control" v-model="category.name">
                    </label>
                    <input type="color" class="form-control input-sm" v-model="category.color"/>
                    <label style="width: 100%">
                        Icon link
                        <input type="text" class="form-control" v-model="category.icon">
                    </label>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" @click="proceed">
                        Save
                    </button>
                    <button class="btn btn-danger" @click="close">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<template id="modal-edit-subcategory-template">
    <div class="modal-mask" v-show="show" transition="modal">
        <div class="modal-wrapper">
            <div class="modal-container">
                <div class="modal-header">
                    Edit subcategory
                </div>
                <div class="modal-body">
                    <label style="width: 100%">
                        Name
                        <input type="text" class="form-control" v-model="subcategory.name">
                    </label>

                    <label>Category</label>
                    <select id="category-for-editing-subcategory" class="form-control" v-model="subcategory.categoryId">
                        <option v-for="cat in categories" v-bind:value="cat.id">
                            {{cat.name}}
                        </option>
                    </select>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" @click="proceed">
                        Save
                    </button>
                    <button class="btn btn-danger" @click="close">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<template id="modal-edit-status-template">
    <div class="modal-mask" v-show="show" transition="modal">
        <div class="modal-wrapper">
            <div class="modal-container">
                <div class="modal-header">
                    Edit status
                </div>
                <div class="modal-body">
                    <p>Current status: </p>
                    <div v-if="factOffer.approved">
                        <span class="label label-success">Approved</span>
                    </div>
                    <div v-else>
                        <span class="label label-danger">Moderation</span>
                    </div>
                    <br/>

                    <div class="form-group">
                        <select class="form-control" v-model="status">
                            <option value="false">Moderation</option>
                            <option value="true" selected>Approved</option>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" @click="proceed">
                        Save
                    </button>
                    <button class="btn btn-danger" @click="close">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<template id="modal-edit-location-template">
    <div class="modal-mask" v-show="show" transition="modal">
        <div class="modal-wrapper container-fluid">
            <div class="modal-container" style="width: 950px">
                <div class="modal-header">
                    Edit location
                </div>
                <div class="modal-body row" style="overflow-y: scroll; height:500px;">
                    <div class="col-sm-4">
                        <label style="width: 100%">
                            Широта
                            <input name="latitude" type="text" class="form-control input-sm" v-model="location.lat">
                        </label>
                        <label style="width: 100%">
                            Долгота
                            <input name="longitude" type="text" class="form-control input-sm" v-model="location.lng">
                        </label>
                        <label style="width: 100%">
                            Адрес
                            <input name="address" type="text" class="form-control input-sm" v-model="location.address">
                        </label>
                        <label style="width: 100%">
                            Имя заведения
                            <input name="name" type="text" class="form-control input-sm" v-model="location.name">
                        </label>
                        <button class="btn btn-sm" @click="addPhone()">
                            <span class="glyphicon glyphicon-plus"></span>
                            Add phone
                        </button>
                        <template v-for="(phone, index) in location.phones">
                            <label style="width: 100%">
                                Телефон{{index+1}}
                                <input type="text" class="form-control input-sm" v-model="location.phones[index]">
                            </label>
                        </template>
                        <button class="btn btn-sm" @click="addWorkingTimes()">
                            <span class="glyphicon glyphicon-plus"></span>
                            Add working hours
                        </button>
                        <template v-for="workingTime in location.workingTimes">
                            <label style="width: 100%">
                                {{daysOfWeek[workingTime.dayNumber]}}
                                <div class="input-group">
                                    <input type="time" class="form-control input-sm" style="width: 50%" v-model="workingTime.startTime">
                                    <input type="time" class="form-control input-sm" style="width: 50%" v-model="workingTime.endTime">
                                    <label><input type="checkbox" class="form-control input-sm" v-model="workingTime.weekend">Выходной?</label>
                                    <label><input type="checkbox" class="form-control input-sm" v-model="workingTime.fullTime">Круглосуточно?</label>
                                </div>
                            </label>
                        </template>
                    </div>
                    <div class="col-sm-4" style="height: 300px; width: 530px">
                        <div id="editLocationMap" class="map-content"></div>
                    </div>
                </div>
                <div class="modal-footer row">
                    <button class="btn btn-success" @click="proceed">
                        Save
                    </button>
                    <button class="btn btn-danger" @click="close">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>
<script src="js/bootstrap.js" defer></script>
<script src="js/map.js" defer></script>
<script src="js/storage.js" defer></script>
<script src="//maps.googleapis.com/maps/api/js?key=AIzaSyD2NfNBwgRHWAdMYIF1FwoE4hZB4oEdJ3s&libraries=places" defer></script>
<script type="text/javascript" src="js/bootstrap-multiselect.js" defer></script>
<script type="text/javascript" src="js/bootstrap-notify.js" defer></script>
<script type="text/javascript" src="js/moment.js" defer></script>
<script src="js/app.js" defer></script>
</body>

</html>