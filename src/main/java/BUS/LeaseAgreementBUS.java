package BUS;

import DAO.FurnitureDAO;
import DAO.LeaseAgreementDAO;
import DTO.Furniture;
import DTO.LeaseAgreement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class LeaseAgreementBUS {
    private ArrayList<LeaseAgreement> leaseAgreements = new ArrayList<>();

    private static LeaseAgreementBUS instance;

    public static LeaseAgreementBUS getInstance() {
        if (instance == null) {
            instance = new LeaseAgreementBUS();
        }
        return instance;
    }

    public LeaseAgreementBUS() {
        this.leaseAgreements = LeaseAgreementDAO.getInstance().selectAll();
    }

    public ArrayList<LeaseAgreement> getAll() {
        LeaseAgreementDAO leaseAgreementDAO = LeaseAgreementDAO.getInstance();
        return leaseAgreementDAO.selectAll();
    }

    public boolean add(LeaseAgreement leaseAgreement) {
        boolean check = LeaseAgreementDAO.getInstance().insert(leaseAgreement) != 0;
        if (check) {
            this.leaseAgreements.add(leaseAgreement);
        }
        return check;
    }

    public boolean delete(LeaseAgreement leaseAgreement) {
        boolean check = LeaseAgreementDAO.getInstance().delete(leaseAgreement.getLeaseAgreementID()) != 0;
        if (check) {
            this.leaseAgreements.remove(leaseAgreement);
        }
        return check;
    }

    public boolean update(LeaseAgreement leaseAgreement) {
        boolean check = LeaseAgreementDAO.getInstance().update(leaseAgreement) != 0;
        if (check) {
            int index = getIndexByLeaseAgreementID(leaseAgreement.getLeaseAgreementID());
            if (index != -1) {
                this.leaseAgreements.set(index, leaseAgreement);
            }
        }
        return check;
    }

    public ArrayList<LeaseAgreement> getLeaseAgreementsWithTenantId(String tenantId) {
        ArrayList<LeaseAgreement> leaseAgreementsWithTenantID = new ArrayList<>();
        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            if (Objects.equals(leaseAgreement.getTenantID(), tenantId)) {
                leaseAgreementsWithTenantID.add(leaseAgreement);
            }
        }
        return leaseAgreementsWithTenantID;
    }

    public ArrayList<LeaseAgreement> getLeaseAgreementsWithApartmentID(String ApartmentID) {
        ArrayList<LeaseAgreement> leaseAgreementsWithApartmentID = new ArrayList<>();
        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            if (Objects.equals(leaseAgreement.getApartmentID(), ApartmentID)) {
                leaseAgreementsWithApartmentID.add(leaseAgreement);
            }
        }
        return leaseAgreementsWithApartmentID;
    }

    public ArrayList<LeaseAgreement> getLeaseAgreementsWithBuildingManagerID(String BuildingManagerID) {
        ArrayList<LeaseAgreement> leaseAgreementsWithBuildingID = new ArrayList<>();
        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            if (Objects.equals(leaseAgreement.getBuildingManagerID(), BuildingManagerID)) {
                leaseAgreementsWithBuildingID.add(leaseAgreement);
            }
        }
        return leaseAgreementsWithBuildingID;
    }

    public int getIndexByLeaseAgreementID(String leaseAgreementID) {
        for (int i = 0; i < this.leaseAgreements.size(); i++) {
            if (this.leaseAgreements.get(i).getLeaseAgreementID().equals(leaseAgreementID)) {
                return i;
            }
        }
        return -1; // Not found
    }
    public void updateTabelLeaseAgreement(ObservableList<LeaseAgreement> leaseAgreements, Label termLabel,Label deposiLabel,Label rentLabel ) {
        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            termLabel.setText(String.valueOf(leaseAgreement.getLeaseEndDate()));
            deposiLabel.setText(String.valueOf(leaseAgreement.getDeposit()));
            rentLabel.setText(String.valueOf(leaseAgreement.getMonthlyRent()));
        }

    }

    public ArrayList<LeaseAgreement> searchApartments(String keyword, String buildingManagerID) {
        return LeaseAgreementDAO.getInstance().search(keyword, buildingManagerID);
    }
}
