package pl.edu.utp.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Firma extends AbstractEntity{
    private String nazwa;

    @OneToMany(mappedBy = "firma", fetch = FetchType.EAGER)
    private List<Kontakt> pracownicy = new LinkedList<>();

    public Firma(){}

    public Firma(String nazwa){
        setNazwa(nazwa);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<Kontakt> getPracownicy(){
        return pracownicy;
    }
}
