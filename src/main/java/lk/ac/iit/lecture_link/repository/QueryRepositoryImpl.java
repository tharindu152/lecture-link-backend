package lk.ac.iit.lecture_link.repository;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class QueryRepositoryImpl implements QueryRepository {

    @PersistenceContext
    private EntityManager em;

}
