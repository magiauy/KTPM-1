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
