package BUS;

import java.util.ArrayList;
import java.util.HashMap;

import DAO.BuildingDAO;
import DTO.Building;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class BuildingBUS {
    private final BuildingDAO BlDAO = new BuildingDAO();

    public ArrayList<Building> listBuildings = new ArrayList<>();

    public BuildingBUS() {
        listBuildings = BlDAO.selectAll();
    }

    public ArrayList<Building> getAll() {
        return this.listBuildings;
    }

    public Building getByIndex(int index) {
        return this.listBuildings.get(index);
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
            int index = getIndexByBuildingId(building.getBuildingId());
            if (index != -1) {
                this.listBuildings.set(index, building);
            }
        }
        return updated;
    }
    private int getIndexByBuildingId(String buildingId) {
        for (int i = 0; i < listBuildings.size(); i++) {
            if (listBuildings.get(i).getBuildingId().equals(buildingId)) {
                return i; // Trả về chỉ mục khi tìm thấy
            }
        }
        return -1; 
    }

    public ArrayList<Building> search(String text, String type) {
        ArrayList<Building> result = new ArrayList<>();
        if (text == null || text.isEmpty() || type == null || type.isEmpty()) {
            // Xử lý khi dữ liệu đầu vào không hợp lệ
            return result;
        }

        for (Building i : this.listBuildings) {
            System.out.println(i); 
        }

        text = text.toLowerCase();

        switch (type) {
            case "Tất cả"  ->
                {
                    for (Building i : this.listBuildings) {
                            result.add(i);
                    }
                }
            case "Tên Tòa Nhà"  ->
                {
                    for (Building i : this.listBuildings) {
                      
                        if ((i.getNameBuilding()).toLowerCase().contains(text)) {
                            result.add(i);
                        }
                    }
                }
        }
        return result;
    }

    public ArrayList<Building> getBuildingsWithoutManager() {
        return BlDAO.getBuildingsWithoutManager();
    }

}
