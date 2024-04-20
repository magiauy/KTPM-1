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
        int index = getIndexByBuildingId(building.getBuildingId());
        if (index >= 0 && index < listBuildings.size()) {
            listBuildings.set(index, building);
            return true;
        } else {
   
            System.err.println("Lỗi: Chỉ số không hợp lệ khi cập nhật.");
            return false;
        }
    }
    private int getIndexByBuildingId(String buildingId) {
        for (int i = 0; i < listBuildings.size(); i++) {
            if (listBuildings.get(i).getBuildingId().equals(buildingId)) {
                return i;
            }
        }
        return -1; 
    }
    public void setTotalNumberOfBuildings(Label numberOfBuildings){
        BuildingBUS buildingBUS = new BuildingBUS();
        ArrayList<Building> buildings = buildingBUS.getAll();

        int total = 0;

        for(Building building : buildings){
            total += building.getNumberOfApartment();
        }
        numberOfBuildings.setText(String.valueOf(total));
    }
    public void setLocationOfBuildings(PieChart pieChart){
        BuildingBUS buildingBUS = new BuildingBUS();
        ArrayList<Building> buildings = buildingBUS.getAll();

        // Tạo một HashMap để lưu số lượng tòa nhà của mỗi thành phố
        HashMap<String, Integer> cityCounts = new HashMap<>();

        // Đếm số lượng tòa nhà trong mỗi thành phố
        for (Building building : buildings) {
            String city = building.getCity();
            cityCounts.put(city, cityCounts.getOrDefault(city, 0) + 1);
        }

        // Tạo danh sách dữ liệu cho Pie Chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Thêm dữ liệu vào danh sách
        for (String city : cityCounts.keySet()) {
            int count = cityCounts.get(city);
            pieChartData.add(new PieChart.Data(city + " (" + count + ")", count));
        }

        // Đặt dữ liệu cho Pie Chart
        pieChart.setData(pieChartData);
    }

}
