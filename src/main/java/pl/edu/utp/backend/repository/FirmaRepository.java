package pl.edu.utp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.utp.backend.entity.Firma;

public interface FirmaRepository extends JpaRepository<Firma, Long> {
}
