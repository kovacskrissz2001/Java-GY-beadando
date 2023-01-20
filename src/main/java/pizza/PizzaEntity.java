package pizza;

import javax.persistence.*;

@Entity
@Table(name = "pizza", schema = "java_gy_bead", catalog = "")
public class PizzaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "nev")
    private String nev;
    @Basic
    @Column(name = "kategorianev")
    private String kategorianev;
    @Basic
    @Column(name = "vegetarianus")
    private boolean vegetarianus;

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getKategorianev() {
        return kategorianev;
    }

    public void setKategorianev(String kategorianev) {
        this.kategorianev = kategorianev;
    }

    public boolean isVegetarianus() {
        return vegetarianus;
    }

    public void setVegetarianus(boolean vegetarianus) {
        this.vegetarianus = vegetarianus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PizzaEntity that = (PizzaEntity) o;

        if (vegetarianus != that.vegetarianus) return false;
        if (nev != null ? !nev.equals(that.nev) : that.nev != null) return false;
        if (kategorianev != null ? !kategorianev.equals(that.kategorianev) : that.kategorianev != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nev != null ? nev.hashCode() : 0;
        result = 31 * result + (kategorianev != null ? kategorianev.hashCode() : 0);
        result = 31 * result + (vegetarianus ? 1 : 0);
        return result;
    }
}
