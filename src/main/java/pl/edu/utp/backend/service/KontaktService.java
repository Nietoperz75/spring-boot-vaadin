package pl.edu.utp.backend.service;

import org.springframework.stereotype.Service;
import pl.edu.utp.backend.entity.Firma;
import pl.edu.utp.backend.entity.Kontakt;
import pl.edu.utp.backend.repository.FirmaRepository;
import pl.edu.utp.backend.repository.KontaktRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KontaktService {
    private static final Logger LOGGER = Logger.getLogger(KontaktService.class.getName());
    private KontaktRepository kontaktRepository;
    private FirmaRepository firmaRepository;

    public KontaktService(KontaktRepository kontaktRepository, FirmaRepository firmaRepository){
        this.kontaktRepository = kontaktRepository;
        this.firmaRepository = firmaRepository;
    }

    public List<Kontakt> findAll(){
        return kontaktRepository.findAll();
    }

    public List<Kontakt> findAll(String stringFilter){
        if(stringFilter == null || stringFilter.isEmpty()){
            return kontaktRepository.findAll();
        }else{
            return kontaktRepository.search(stringFilter);
        }
    }

    public long count(){
        return kontaktRepository.count();
    }

    public void delete(Kontakt contact){
        kontaktRepository.delete(contact);
    }

    public void save(Kontakt kontakt){
        if(kontakt == null){
            LOGGER.log(Level.SEVERE , "Coś nie wyszło. Kontakt jest null");
            return;
        }
        kontaktRepository.save(kontakt);
    }

    @PostConstruct
    public void populateTestData() {
        if (firmaRepository.count() == 0) {
            firmaRepository.saveAll(
                    Stream.of("ACME", "UTP", "Elektonix", "Mazpil", "Tomala")
                            .map(Firma::new)
                            .collect(Collectors.toList()));
        }

        if (kontaktRepository.count() == 0) {
            Random r = new Random(0);
            List<Firma> firmy = firmaRepository.findAll();
            kontaktRepository.saveAll(
                    Stream.of("Jan Kowalski", "Zbigniew Nowak", "Ewa Mazur","Marzanna Czarnecka","Alisa Baran","Joanna Brzezińska","Edyta Krajewska","Jowita Zalewska","Marcela Sobczak",
                            "Andrea Piotrowska","Cecylia Rutkowska","Amelia Mróz","Izabela Zakrzewska","Wanda Sokołowska","Berenika Laskowska","Arleta Szulc","Liliana Marciniak",
                            "Justyna Jasińska","Adrianna Czerwińska","Jowita Kwiatkowska","Helena Wróblewska","Andżelika Sobczak",
                            "Przemysław Kaźmierczak","Adam Pietrzak","Błażej Sikorska","Jarosław Mazurek","Jakub Włodarczyk","Marcel Szczepański","Marek Szczepański","Kajetan Woźniak",
                            "Konstanty Nowak","Kuba Włodarczyk","Kazimierz Nowak","Emil Krupa","Dariusz Zawadzki","Leszek Sawicki","Jacek Ziółkowska","Paweł Górski",
                            "Oskar Krajewski","Ignacy Zieliński","Bronisław Jasiński","Natan Krajewski")
                            .map(dane -> {
                                String[] split = dane.split(" ");
                                Kontakt kontakt = new Kontakt();
                                kontakt.setImie(split[0]);
                                kontakt.setNazwisko(split[1]);
                                kontakt.setFirma(firmy.get(r.nextInt(firmy.size())));
                                kontakt.setStatus(Kontakt.Status.values()[r.nextInt(Kontakt.Status.values().length)]);
                                String email = (kontakt.getImie() + "." + kontakt.getNazwisko() + "@" + kontakt.getFirma().getNazwa().replaceAll("[\\s-]", "") + ".com").toLowerCase();
                                kontakt.setEmail(email);
                                return kontakt;
                            }).collect(Collectors.toList()));
        }
    }

}
