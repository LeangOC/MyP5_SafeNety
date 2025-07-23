package oc.p5.SafeNety.service;
import oc.p5.SafeNety.dto.FirestationDTO;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestationService {

    @Autowired
    private FirestationRepository repository;

    public void addFirestation(FirestationDTO dto) {
        Firestation f = new Firestation();
        f.setAddress(dto.address);
        f.setStation(dto.station);
        repository.save(f);
    }

    public void updateFirestation(FirestationDTO dto) {
        repository.update(new Firestation(dto.address, dto.station));
    }

    public void deleteByAddress(String address) {
        repository.deleteByAddress(address);
    }

    public void deleteByStation(String station) {
        repository.deleteByStation(station);
    }

}