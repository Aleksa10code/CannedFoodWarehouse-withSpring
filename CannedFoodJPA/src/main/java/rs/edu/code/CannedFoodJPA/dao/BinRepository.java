package rs.edu.code.CannedFoodJPA.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.edu.code.CannedFoodJPA.model.Bin;

import java.util.List;

public interface BinRepository extends JpaRepository<Bin, Long> {

    // anotacija govori da unutar jednog entiteta mozemo da imamo vise drugih entiteta, kao sto imamo unutar bina cans.
    @EntityGraph(attributePaths = {"cans"})
    Bin getWithCansById(long id);

    // JPQL
    @Query("SELECT b FROM Bin AS b WHERE (b.type = ?1 AND b.cans.size < 10) OR b.cans.size = 0 ORDER BY b.cans.size DESC")
    List<Bin> getForType(String type);

}
