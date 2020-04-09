package pl.edu.utp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.utp.backend.entity.Kontakt;

import java.util.List;

public interface KontaktRepository extends JpaRepository<Kontakt, Long> {

    @Query("select k from Kontakt k where lower(k.imie) like lower(concat('%', :warunekSzukania, '%' )) or lower(k.nazwisko) like lower(concat('%', :warunekSzukania, '%'))")
    List<Kontakt> search(@Param("warunekSzukania") String warunekSzukania);

}
