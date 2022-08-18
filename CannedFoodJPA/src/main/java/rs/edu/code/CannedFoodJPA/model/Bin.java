package rs.edu.code.CannedFoodJPA.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.engine.internal.Cascade;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

// jsonIdentityInfo nam je potreban kako bi jackson prepoznao da se radi o bin entity-u
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "bin")
public class Bin {

    @Id
    @GeneratedValue
    private long id;
    private String type;

    /***
     * sada imamo biDirectional vezu OneToMany (bin -> can)
     *
     */

    // mapirano je u bin field-u u klasi Can
    // Razlika izmedju fetch LAZY i EAGER, kada ucitavamo bin objekat iz baze nece se ucitati cans dok god mi ne pokusamo
    // da pristupimo can-u.
    @OneToMany(mappedBy = "bin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Can> cans = new LinkedHashSet<>();


    public void addCan(Can can) {
        if (cans.isEmpty()) {
            type = can.getType();
        }

        if (!type.equals(can.getType())) {
            throw new IllegalArgumentException("Neodgovarajuci tip: " + can.getType());
        }
        // usaglasavamo strane can i bin
        can.setBin(this);
        cans.add(can);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Can> getCans() {
        return cans;
    }

    public void setCans(Set<Can> cans) {
        this.cans = cans;
    }
}
