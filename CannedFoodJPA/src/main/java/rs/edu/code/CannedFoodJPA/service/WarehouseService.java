package rs.edu.code.CannedFoodJPA.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.edu.code.CannedFoodJPA.dao.BinRepository;
import rs.edu.code.CannedFoodJPA.dao.CanRepository;
import rs.edu.code.CannedFoodJPA.model.Bin;
import rs.edu.code.CannedFoodJPA.model.Can;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class WarehouseService {

    @Autowired
    CanRepository canRepository;

    @Autowired
    BinRepository binRepository;

    public boolean init() {
        if (binRepository.count() < 1) {
            Set<Bin> bins = new LinkedHashSet<>();
            Bin bin = new Bin();
            bin.addCan(new Can("cola", LocalDate.now().plusMonths(2)));
            bin.addCan(new Can("cola", LocalDate.now().plusMonths(2)));
            bins.add(bin);
            bin = new Bin();
            bin.addCan(new Can("fanta", LocalDate.now().minusMonths(2)));
            bins.add(bin);
            for (int i = 0; i < 8; i++) {
                bins.add(new Bin());
            }
            binRepository.saveAll(bins);
            return true;
        }

        else {
            return false;
        }
    }

    public boolean addCan(String type, LocalDate expirationDate) {
        Can can = new Can();
        // id ce biti automatski generisan
        can.setType(type);
        can.setExpirationDate(expirationDate);
        canRepository.save(can); // save() cuva u bazu (insert) ili moze da radi i kao update zavisno od stanja u bazi
        return false;
    }

    // transactional anotacija odredjuje trajanje hibernate sesije i drzi otvorenu konekciju sa bazom kako bi izvrsili ucitavanje can objekata
    @Transactional
    public Can getCan(long id) {
       Can can = canRepository.findById(id).get();
       can.getBin();
       return can;
    }

    public Set<Can> getAllCansByType(String type) {
        return canRepository.getByType(type);
    }

    public Can getFirstCanByType(String type) {
        return canRepository.getFirstByTypeOrderByExpirationDate(type);
    }

    public Set<Can> getAllCansByTypeJPQL(String type) {
        return canRepository.getByTypeJPQLQuery(type);
    }

    @Transactional
    public void brziTest() {
        Bin potpunBin = binRepository.getWithCansById(1L);
        if (potpunBin.getCans() != null) {
            System.out.println("potpun bin, ne sadrzi null");
        }

       Bin nepotpunBin = binRepository.getById(1L);
       if (nepotpunBin.getCans() != null) {
           System.out.println("nepotpun, funckionise nakon dodavanja @Transactional anotacije");
       }
    }

    public Can addCanInWarehouse(Can can) {
        List<Bin> bins = binRepository.getForType(can.getType());
        if (bins.isEmpty()) {
            System.out.println("Nema odgovarajuceg bina.");
            throw new NoSuchElementException("No suitable bin found!");
        }

        else {
            Bin bin = bins.get(0);
            System.out.println("Bin id = " + bin.getId());
            bin.addCan(can);
            can = canRepository.save(can); // nije neophodno, jer prilikom cuvanja bina cuvamo i canove u binu zbog cascadeType.ALL
            binRepository.save(bin);
            return can;
        }
    }
}