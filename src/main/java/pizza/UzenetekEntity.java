package pizza;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "uzenetek", schema = "gyakbead", catalog = "")
public class UzenetekEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "tartalom")
    @Size(min = 10, message = "Legalább 10 karakter hosszú legyen az üzenet")
    private String tartalom;
    @Basic
    @Column(name = "irta")
    private String irta;
    @Basic
    @Column(name = "datum")
    private Timestamp datum;



    public UzenetekEntity(String tartalom) {
        this.tartalom = tartalom;
    }

    public UzenetekEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTartalom() {
        return tartalom;
    }

    public void setTartalom(String tartalom) {
        this.tartalom = tartalom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UzenetekEntity that = (UzenetekEntity) o;

        if (id != that.id) return false;
        if (tartalom != null ? !tartalom.equals(that.tartalom) : that.tartalom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tartalom != null ? tartalom.hashCode() : 0);
        return result;
    }

    public String getIrta() {
        return irta;
    }

    public void setIrta(String irta) {
        this.irta = irta;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }
}