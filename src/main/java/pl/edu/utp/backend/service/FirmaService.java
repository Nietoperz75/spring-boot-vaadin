package pl.edu.utp.backend.service;

import org.springframework.stereotype.Service;
import pl.edu.utp.backend.entity.Firma;
import pl.edu.utp.backend.repository.FirmaRepository;

import java.util.List;

@Service
public class FirmaService {

    private FirmaRepository firmaRepository;

    public FirmaService(FirmaRepository firmaRepository){
        this.firmaRepository = firmaRepository;

    }

    public List<Firma> findAll(){
        return firmaRepository.findAll();
    }
}
