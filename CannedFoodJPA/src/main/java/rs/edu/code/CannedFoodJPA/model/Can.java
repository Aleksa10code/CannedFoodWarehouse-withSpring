package rs.edu.code.CannedFoodJPA.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "can") // optional annotation
public class Can {

    @Id
    @GeneratedValue
    private long id;
    @Column(length = 45, nullable = false)
    private String type;
    @Column(nullable = false)
    private LocalDate expirationDate;

    /***
     * Prvo pravimo unidirectional many-to-one relaciju (can -> bin moze, bin -> can ne moze)
     *
     * @return
     */

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bin_id", foreignKey = @ForeignKey(name = "fk_bin_id")) // definisemo ime kako hibernate ne bi definisao nego generisano ime za foreign key
    private Bin bin;

    // default constructor
    public Can() {

    }

    public Can(String type, LocalDate expirationDate) {
        this.type = type;
        this.expirationDate = expirationDate;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
