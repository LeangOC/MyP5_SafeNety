package oc.p5.SafeNety.repository;

import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.utils.DataLoader;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FirestationRepository {

    private List<Firestation> firestations;

    public FirestationRepository() {
        this.firestations = DataLoader.loadData().getFirestations(); // Charge depuis data.json
    }

    public List<Firestation> findAll() {
        return firestations;
    }

    public void save(Firestation firestation) {
        firestations.add(firestation);
        DataLoader.saveData(); // Sauvegarde dans data.json
    }

    public void update(Firestation updated) {
        for (Firestation f : firestations) {
            if (f.getAddress().equalsIgnoreCase(updated.getAddress())) {
                f.setStation(updated.getStation());
                break;
            }
        }
        DataLoader.saveData();
    }

    public void deleteByAddress(String address) {
        firestations.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
        DataLoader.saveData();
    }

    public void deleteByStation(String station) {
        firestations.removeIf(f -> f.getStation() == station);
        DataLoader.saveData();
    }
}
