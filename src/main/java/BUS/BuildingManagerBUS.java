package BUS;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import DAO.BuildingManagerDAO;
import DTO.BuildingManager;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;


public class BuildingManagerBUS {
    private List<BuildingManager> listBuildingManagers = new ArrayList<>();
    private int MAX = 100;

    public BuildingManagerBUS() {
    }
    public BuildingManagerBUS(List<BuildingManager> listBuildingManagers) {
        this.listBuildingManagers = listBuildingManagers;
    }
    public ArrayList<BuildingManager> getAll() {
        BuildingManagerDAO buildingManagerDAO = BuildingManagerDAO.getInstance();
        return buildingManagerDAO.selectAll();
    }
    public boolean insert(BuildingManager buildingManager) {
        boolean check = BuildingManagerDAO.getInstance().insert(buildingManager) > 0;
        if (check) {
            this.listBuildingManagers.add(buildingManager);
        }
        return check;
    }
    public boolean delete(BuildingManager buildingManager) {
        boolean check = BuildingManagerDAO.getInstance().delete(buildingManager.getBuildingManagerId()) != 0;
        if (check) {
            this.listBuildingManagers.remove(buildingManager);
        }
        return check;
    }
    public boolean update(BuildingManager buildingManager) {
        int index = getIndexByBuildingManagerId(buildingManager.getBuildingManagerId());
        if (index >= 0 && index < listBuildingManagers.size()) {
            listBuildingManagers.set(index, buildingManager);
            return true;
        } else {
            System.err.println("Lỗi: Chỉ số không hợp lệ khi cập nhật.");
            return false;
        }
    }
    private int getIndexByBuildingManagerId(String buildingManagerId) {
        for (int i = 0; i < listBuildingManagers.size(); i++) {
            if (listBuildingManagers.get(i).getBuildingManagerId().equals(buildingManagerId)) {
                return i;
            }
        }
        return -1;
    }
    public void getGenderOfBDManager(BarChart<String, Number> barChart) {
        // Khởi tạo mảng hai chiều để lưu số lượng người quản lý theo từng độ tuổi và giới tính
        int[][] genderAgeCount = new int[100][2]; // Giả sử tuổi tối đa là 100

        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        ArrayList<BuildingManager> buildingManagers = buildingManagerBUS.getAll();

        LocalDate currentDate = LocalDate.now();

        // Duyệt qua danh sách người quản lý tòa nhà và tính độ tuổi của mỗi người quản lý
        for (BuildingManager buildingManager : buildingManagers) {
            LocalDate managersDOB = buildingManager.getDob();
            Period calculate = Period.between(managersDOB, currentDate);
            int managersAge = calculate.getYears();

            // Xác định giới tính của người quản lý (0 là nam, 1 là nữ)
            int genderIndex = buildingManager.getGender().equals("Nam") ? 0 : 1;

            // Cập nhật mảng hai chiều
            genderAgeCount[managersAge][genderIndex]++;
        }

        // Xóa các dữ liệu cũ trong biểu đồ
        barChart.getData().clear();

        // Thêm dữ liệu mới vào biểu đồ
        XYChart.Series<String, Number> maleSeries = new XYChart.Series<>();
        maleSeries.setName("Nam");
        XYChart.Series<String, Number> femaleSeries = new XYChart.Series<>();
        femaleSeries.setName("Nữ");

        for (int i = genderAgeCount.length - 1; i >= 0; i--) {
            if (genderAgeCount[i][0] > 0) {
                maleSeries.getData().add(new XYChart.Data<>(String.valueOf(currentDate.getYear() - i), i));
            }
            if (genderAgeCount[i][1] > 0) {
                femaleSeries.getData().add(new XYChart.Data<>(String.valueOf(currentDate.getYear() - i), i));
            }
        }

        barChart.getData().addAll(maleSeries, femaleSeries);
    }

}
