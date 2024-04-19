package BUS;

import java.util.ArrayList;

import DAO.BuildingDAO;
import DTO.Building;

public class BuildingBUS {
    private ArrayList<Building> listBuildings = new ArrayList<>();

    public BuildingBUS() {
        
    }


    public BuildingBUS(ArrayList<Building> listBuildings) {
        this.listBuildings = listBuildings;
    }

    public ArrayList<Building> getAll() {
        BuildingDAO buildingDAO = BuildingDAO.getInstance(); 
        return buildingDAO.selectAll();
    }

    public boolean insert(Building building) {
        boolean check = BuildingDAO.getInstance().insert(building) > 0;
        if (check) {
            this.listBuildings.add(building);
        }
        return check;
    }

    public boolean delete(Building building) {
        boolean check = BuildingDAO.getInstance().delete(building.getBuildingId()) != 0;
        if (check) {
            this.listBuildings.remove(building);
        }
        return check;
    }
   
    public boolean update(Building building) {
        boolean updated = BuildingDAO.getInstance().update(building) != 0;
        if (updated) {
            int index = getIndexByBuildingID(building.getBuildingId());
            if (index != -1) {
                this.listBuildings.set(index, building);
            }
        }
        return updated;
    }

    public int getIndexByBuildingID(String buildingId) {
        for (int i = 0; i < this.listBuildings.size(); i++) {
            if (listBuildings.get(i).getBuildingId().equals(buildingId)) {
                return i; // Trả về chỉ mục khi tìm thấy
            }
        }
        return -1; 
    }

}
