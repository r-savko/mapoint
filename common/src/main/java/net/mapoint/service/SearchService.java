package net.mapoint.service;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.converter.LocationDtoConverter;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.entity.Location;
import net.mapoint.model.LocationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.hibernate.search.spatial.Coordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Service
@Transactional
public class SearchService {

    private static final Logger LOGGER = LogManager.getLogger(SearchService.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private LocationDtoConverter locationDtoConverter;


    @Autowired
    private LocationDao locationDao;

    public Optional<Set<LocationDto>> search(String term) {
        Session session = sessionFactory.openSession();
        try (FullTextSession fullTextSession = Search.getFullTextSession(session)) {

            QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Location.class).get();
            org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("name", "address").matching(term).createQuery();

            FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Location.class);
            fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.QUERY);
            fullTextQuery.setProjection("id");

            List result = fullTextQuery.list();
            List<Integer> ids = Lists.newLinkedList();
            for (Object o : result) {
                Object[] searchResult = ((Object[]) o);
                Integer id = (Integer) searchResult[0];
                ids.add(id);
            }

            if (ids.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(
                locationDao.getLocations(ids).stream()
                    .map(c -> locationDtoConverter.toDto(c))
                    .collect(Collectors.toCollection(TreeSet::new))
            );
        }
    }

    public Optional<Set<LocationDto>> search(double lat, double lng, double radius) {
        Session session = sessionFactory.openSession();
        try (FullTextSession fullTextSession = Search.getFullTextSession(session)) {

            QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Location.class).get();

            Coordinates coordinates = new Coordinates() {
                @Override
                public Double getLatitude() {
                    return lat;
                }

                @Override
                public Double getLongitude() {
                    return lng;
                }
            };

            org.apache.lucene.search.Query luceneQuery = queryBuilder
                .spatial()
                .within(radius, Unit.KM)
                .ofCoordinates(coordinates).createQuery();

            FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Location.class);
            fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.QUERY);
            fullTextQuery.setProjection("id", "has_approved_offers", "has_approved_facts");

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            List result = fullTextQuery.list();
            List<Integer> ids = Lists.newLinkedList();
            for (Object o : result) {
                Object[] searchResult = ((Object[]) o);
                Integer id = (Integer) searchResult[0];
                Boolean hasApprovedFacts = Boolean.TRUE.equals(searchResult[1]);
                Boolean hasApprovedOffers = Boolean.TRUE.equals(searchResult[2]);
                if (hasApprovedFacts || hasApprovedOffers) {
                    ids.add(id);
                }
            }
            if (ids.isEmpty()) {
                return Optional.empty();
            }
            stopWatch.stop();
            LOGGER.info("Find ids - " + stopWatch.prettyPrint());
            stopWatch.start();
            Set<LocationDto> locations = locationDao.getLocations(ids).stream().map(c -> locationDtoConverter.toDto(c))
                .collect(Collectors.toCollection(TreeSet::new));
            stopWatch.stop();
            LOGGER.info("Fetch locations - " + stopWatch.prettyPrint());

            return Optional.of(locations);
        }
    }

    public void buildIndex() throws ServiceException {
        LOGGER.error("Application context has been started. Begin data indexing");
        Session session = sessionFactory.openSession();
        try (FullTextSession fullTextSession = Search.getFullTextSession(session)) {
            fullTextSession.createIndexer().purgeAllOnStart(true)
                .optimizeAfterPurge(true)
                .optimizeOnFinish(true)
                .startAndWait();
        } catch (InterruptedException e) {
            throw new ServiceException(e);
        }
        LOGGER.error("Data indexing has been completed successfully");
    }


}
