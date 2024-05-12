package BUS;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import DAO.BuildingDAO;
import DAO.BuildingManagerDAO;
import DAO.TenantDAO;
import DTO.BuildingManager;
import DTO.Tenant;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class BuildingManagerBUS {

    private int MAX = 100;

   private final BuildingManagerDAO BlDAO = new BuildingManagerDAO();
    public ArrayList<BuildingManager> listBuildingManagerDAOs = new ArrayList<>();

    private static BuildingManagerBUS instance;

    public static BuildingManagerBUS getInstance() {
        if (instance == null) {
            instance = new BuildingManagerBUS();
        }
        return instance;
    }


    public BuildingManagerBUS() {
        listBuildingManagerDAOs = BlDAO.selectAll();
    }

    public ArrayList<BuildingManager> getAll() {
        return this.listBuildingManagerDAOs;
    }

    public BuildingManager getByIndex(int index) {
        return this.listBuildingManagerDAOs.get(index);
    }

    public boolean insert(BuildingManager buildingManager) {
        boolean check = BuildingManagerDAO.getInstance().insert(buildingManager) > 0;
        if (check) {
            this.listBuildingManagerDAOs.add(buildingManager);
        }
        return check;
    }

    public boolean delete(BuildingManager buildingManager) {
        boolean check = BuildingManagerDAO.getInstance().delete(buildingManager.getBuildingManagerId()) != 0;
        if (check) {
            this.listBuildingManagerDAOs.remove(buildingManager);
        }
        return check;
    }
    public boolean update(BuildingManager buildingManager) {
        boolean updated = BuildingManagerDAO.getInstance().update(buildingManager) != 0;
        if (updated) {
            int index = getIndexByBuildingId(buildingManager.getBuildingId());
            if (index != -1) {
                this.listBuildingManagerDAOs.set(index, buildingManager);
            }
        }
        return updated;
    }

    public BuildingManager getBuildingManagerById(String BMID) {
        return BuildingManagerDAO.getInstance().selectById(BMID);
    }
    private int getIndexByBuildingId(String buildingManegerId) {
        for (int i = 0; i < listBuildingManagerDAOs.size(); i++) {
            if (listBuildingManagerDAOs.get(i).getBuildingId().equals(buildingManegerId)) {
                return i;
            }
        }
        return -1;
    }

    public void getGenderOfBDManager(BarChart<String, Number> barChart) {
        // Khởi tạo mảng hai chiều để lưu số lượng người quản lý theo từng độ tuổi và
        // giới tính
        int[][] genderAgeCount = new int[100][2]; // Giả sử tuổi tối đa là 100

        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        ArrayList<BuildingManager> buildingManagers = buildingManagerBUS.getAll();

        LocalDate currentDate = LocalDate.now();

        // Duyệt qua danh sách người quản lý tòa nhà và tính độ tuổi của mỗi người quản
        // lý
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
    public void setInfor(Text id, Text fullName, Text phone, Text dob, Text gender, Text cccd, String ID){
        BuildingManager buildingManager = getBuildingManagerById(ID);
        System.out.println("___ "+ buildingManager);
        if(buildingManager != null){
            id.setText(buildingManager.getBuildingManagerId());
            fullName.setText(buildingManager.getLastName() + " " + buildingManager.getFirstName());
            phone.setText(buildingManager.getPhoneNumber());
            dob.setText(String.valueOf(buildingManager.getDob()));
            gender.setText(buildingManager.getGender());
            cccd.setText(buildingManager.getCitizenIdentityCard());
        }
    }


    public ArrayList<BuildingManager> search(String text, String type) {
        ArrayList<BuildingManager> result = new ArrayList<>();
        text = text.toLowerCase();
        if (text == null || type == null || type.isEmpty()) {
            return result;
        }

        switch (type) {
            case "Tìm Theo Ten" -> {
                for (BuildingManager i : this.listBuildingManagerDAOs) {
                    if (i.getLastName().toLowerCase().contains(text)) {
                        result.add(i);
                    }

                }
            }

        

          case "Giới Tính" -> {
                    for (BuildingManager i : this.listBuildingManagerDAOs) {
                       
                        if ((i.getGender()).toLowerCase().contains(text)) {
                            result.add(i);
                        }
                    }
                }
            }
            
        System.out.println("Kết quả tìm kiếm: " + result);
        return result;
    }

}
