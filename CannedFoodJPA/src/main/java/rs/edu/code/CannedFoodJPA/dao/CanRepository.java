package rs.edu.code.CannedFoodJPA.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.edu.code.CannedFoodJPA.model.Can;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Set;

public interface CanRepository extends JpaRepository<Can, Long> {

     // Spring Data - derived query
     // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
     Set<Can> getByType(String type);

     Can getFirstByTypeOrderByExpirationDate(String type);

     // Spring JPQL - JAVA PERSISTENT QUERY LANGUAGE
     @Query("SELECT c FROM Can AS c WHERE c.type = ?1")
     Set<Can> getByTypeJPQLQuery(String type);

     //SQL Native Query
     @Query(value = "SELECT * FROM Can WHERE type = ?1", nativeQuery = true)
     Set<Can> getByTypeSQLNativeQuery(String type);

}
