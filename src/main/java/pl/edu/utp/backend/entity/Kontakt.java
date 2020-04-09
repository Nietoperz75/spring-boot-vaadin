package pl.edu.utp.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Kontakt extends AbstractEntity implements Cloneable {

    public enum Status{
        Zarządzający, BrakKontaktu, Kontakt, Klient, Nieaktywny
    }

    @NotNull
    @NotEmpty
    private String imie;

    @NotNull
    @NotEmpty
    private String nazwisko = "";

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Firma firma;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Kontakt.Status status;

    @Email
    @NotNull
    @NotEmpty
    private String email = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String lastName) {
        this.nazwisko = lastName;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String firstName) {
        this.imie = firstName;
    }

    public void setFirma(Firma company) {
        this.firma = company;
    }

    public Firma getFirma() {
        return firma;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(imie).append(" ").append(nazwisko).toString();
//        return firstName + " " + lastName;
    }
}
