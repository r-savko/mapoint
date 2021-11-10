package net.mapoint.controller;

import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.mapoint.converter.LocationResponseDtoConverter;
import net.mapoint.converter.LocationResponseDtoSimpleConverter;
import net.mapoint.model.CategoryDto;
import net.mapoint.model.FactDto;
import net.mapoint.model.LocationDto;
import net.mapoint.model.LocationResponse;
import net.mapoint.model.OfferDto;
import net.mapoint.payload.AddFactPayload;
import net.mapoint.payload.AddOfferPayload;
import net.mapoint.payload.GetLocationsPayload;
import net.mapoint.service.CategoryService;
import net.mapoint.service.LocationService;
import net.mapoint.service.OfferService;
import net.mapoint.service.SearchService;
import net.mapoint.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
public class ClientRestController {

    @Autowired
    private LocationResponseDtoConverter locationResponseDtoConverter;
    @Autowired
    private LocationResponseDtoSimpleConverter locationResponseDtoSimpleConverter;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/get/locations/{lat}/{lng}/{r}", method = RequestMethod.POST)
    @ApiOperation(value = "Get locations in circle with radius r and center (lat lng)")
    public List<LocationResponse> getLocationsByRadiusAndSubcategoriesWithFactsFilter(@RequestBody GetLocationsPayload getLocationsPayload,
        @PathVariable Double lat, @PathVariable Double lng, @PathVariable Double r) {
        LocationDto fromPoint = new LocationDto(lat, lng);
        List<LocationDto> list = locationService.getLocationsByRadiusAndSubcategories(
            fromPoint, r, getLocationsPayload.getSubcategories(), getLocationsPayload.isIncludeFacts(), getLocationsPayload.getTimeRange()
        );
        return list.stream().map(l -> locationResponseDtoConverter.convert(l)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/get/locations/{lat}/{lng}/", method = RequestMethod.GET)
    @ApiOperation(value = "Get locations in circle with radius 0.5 km")
    public List<LocationResponse> getLocationsByRadiusAndSubcategoriesWithFactsFilter(@PathVariable Double lat, @PathVariable Double lng) {
        LocationDto fromPoint = new LocationDto(lat, lng);
        List<LocationDto> list = locationService.getLocationsByRadius(fromPoint, 0.5);
        return list.stream().map(l -> locationResponseDtoConverter.convertWithLocationInfoOnly(l)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/get/categories", method = RequestMethod.GET)
    @ApiOperation(value = "Get all location categories")
    public Set<CategoryDto> getCategories() {
        return categoryService.getAll();
    }

    @RequestMapping(value = "/search/locations", method = RequestMethod.GET)
    @ApiOperation(value = "Search locations using term")
    public List<LocationResponse> searchLocations(@RequestParam("q") String q) {
        return searchService.search(q).orElse(Collections.emptySet()).stream()
            .map(l -> locationResponseDtoSimpleConverter.convert(l))
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/add/offer", method = RequestMethod.POST)
    @ApiOperation(value = "Add offer")
    public void addOffer(@RequestBody AddOfferPayload addOfferPayload) {
        Integer id = addOfferPayload.getLocationId();
        if (id != null && id != 0) {
            LocationDto location = locationService.get(id);
            OfferDto offer = new OfferDto()
                .setFullText(addOfferPayload.getFullText())
                .setText(addOfferPayload.getText())
                .setLocation(location);
            if (addOfferPayload.getCategories() != null) {
                offer.setSubcategories(
                    addOfferPayload.getCategories().stream().map(c -> subcategoryService.get(c)).collect(Collectors.toList()));
            }
            offer.setLink(addOfferPayload.getLink());
            offerService.add(offer);
        } else {
            throw new RuntimeException("Incorrect location id");
        }
    }

    @RequestMapping(value = "/add/fact", method = RequestMethod.POST)
    @ApiOperation(value = "Add fact")
    public void addFact(@RequestBody AddFactPayload addFactPayload) {
        LocationDto location = new LocationDto()
            .setAddress(addFactPayload.getAddress())
            .setLat(addFactPayload.getLat())
            .setLng(addFactPayload.getLng());
        FactDto fact = new FactDto().setLink(addFactPayload.getLink()).setText(addFactPayload.getText());
        locationService.add(location, fact);
    }

}
