package net.mapoint.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.mapoint.compare.OfferComparatorFactory;
import net.mapoint.model.CategoryDto;
import net.mapoint.model.FactDto;
import net.mapoint.model.LocationDto;
import net.mapoint.model.OfferDto;
import net.mapoint.model.SubcategoryDto;
import net.mapoint.service.CategoryService;
import net.mapoint.service.FactService;
import net.mapoint.service.LocationService;
import net.mapoint.service.OfferService;
import net.mapoint.service.SearchService;
import net.mapoint.service.ServiceException;
import net.mapoint.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/*")
public class AdminRestController {

    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private FactService factService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/get/locations", method = RequestMethod.GET)
    public Set<LocationDto> getLocations() {
        return locationService.getAll();
    }

    @RequestMapping(value = "/get/locations/{id}", method = RequestMethod.GET)
    public LocationDto getLocation(@PathVariable Integer id) {
        return locationService.get(id);
    }

    @RequestMapping(value = "/delete/offers/{id}", method = RequestMethod.DELETE)
    public void deleteOffer(@PathVariable Integer id) {
        offerService.delete(id);
    }

    @RequestMapping(value = "/delete/facts/{id}", method = RequestMethod.DELETE)
    public void deleteFact(@PathVariable Integer id) {
        factService.delete(id);
    }

    @RequestMapping(value = "/delete/locations/{id}", method = RequestMethod.DELETE)
    public void deleteLocation(@PathVariable Integer id) {
        locationService.delete(id);
    }

    @RequestMapping(value = "/add/location", method = RequestMethod.POST)
    public LocationDto addLocation(@RequestBody LocationDto locationDto) {
        return locationService.add(locationDto);
    }

    @RequestMapping(value = "/add/offer", method = RequestMethod.POST)
    public OfferDto addOffer(@RequestBody OfferDto offerDto) {
        return offerService.add(offerDto);
    }

    @RequestMapping(value = "/add/fact", method = RequestMethod.POST)
    public FactDto addFact(@RequestBody FactDto factDto) {
        return factService.add(factDto);
    }

    @RequestMapping(value = "/update/location", method = RequestMethod.PUT)
    public LocationDto updateLocation(@RequestBody LocationDto locationDto) {
        return locationService.update(locationDto);
    }

    @RequestMapping(value = "/update/offer", method = RequestMethod.PUT)
    public OfferDto updateOffer(@RequestBody OfferDto updateOfferRequest) {
        return offerService.update(updateOfferRequest);
    }

    @RequestMapping(value = "/update/fact", method = RequestMethod.PUT)
    public FactDto updateFact(@RequestBody FactDto updateFactRequest) {
        return factService.update(updateFactRequest);
    }

    @RequestMapping(value = "/get/offers", method = RequestMethod.GET)
    public List<OfferDto> getAllOffers(@RequestParam(required = false, value = "sortBy") String sortBy) {
        return offerService.getAll().stream()
            .sorted(OfferComparatorFactory.comparator(sortBy))
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/get/facts", method = RequestMethod.GET)
    public List<FactDto> getAllFacts() {
        return factService.getAllFactsOrderByApproved();
    }

    @RequestMapping(value = "/update/fact/{id}/status/{status}", method = RequestMethod.PUT)
    public void updateFactStatus(@PathVariable int id, @PathVariable boolean status) {
        factService.updateStatus(id, status);
    }

    @RequestMapping(value = "/update/offer/{id}/status/{status}", method = RequestMethod.PUT)
    public void updateOfferStatus(@PathVariable int id, @PathVariable boolean status) {
        offerService.updateStatus(id, status);
    }

    @RequestMapping(value = "/add/category", method = RequestMethod.POST)
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.add(categoryDto);
    }

    @RequestMapping(value = "/update/category", method = RequestMethod.PUT)
    public CategoryDto updateCategory(@RequestBody CategoryDto updateCategoryRequest) {
        return categoryService.update(updateCategoryRequest);
    }

    @RequestMapping(value = "/delete/category/{id}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable int id) {
        categoryService.delete(id);
    }

    @RequestMapping(value = "/add/subcategory", method = RequestMethod.POST)
    public SubcategoryDto addSubcategory(@RequestBody SubcategoryDto subcategoryDto) {
        return subcategoryService.add(subcategoryDto);
    }

    @RequestMapping(value = "/update/subcategory", method = RequestMethod.PUT)
    public SubcategoryDto updateSubcategory(@RequestBody SubcategoryDto updateSubcategoryRequest) {
        return subcategoryService.update(updateSubcategoryRequest);
    }

    @RequestMapping(value = "/delete/subcategory/{id}", method = RequestMethod.DELETE)
    public void deleteSubcategory(@PathVariable int id) {
        subcategoryService.delete(id);
    }

    @RequestMapping(value = "/get/search/", method = RequestMethod.GET)
    public Set<LocationDto> deleteSubcategory(@RequestParam(value = "q") String query) {
        Optional<Set<LocationDto>> optional = searchService.search(query);
        return optional.orElse(null);
    }

    @RequestMapping(value = "/get/search/index", method = RequestMethod.GET)
    public void buildSearchIndex() throws ServiceException {
        searchService.buildIndex();
    }

    @RequestMapping(value = "/get/locations/{lat}/{lng}/{r}", method = RequestMethod.GET)
    public Set<LocationDto> getLocationsByRadius(@PathVariable Double lat, @PathVariable Double lng, @PathVariable Double r) {
        Optional<Set<LocationDto>> optional = searchService.search(lat, lng, r);
        return optional.orElse(null);
    }

    @RequestMapping(value = "/get/categories", method = RequestMethod.GET)
    public Set<CategoryDto> getCategories() {
        return categoryService.getAll();
    }

}