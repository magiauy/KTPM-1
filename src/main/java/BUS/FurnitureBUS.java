package BUS;

import DAO.ApartmentDAO;
import DAO.FurnitureDAO;
import DTO.Apartment;
import DTO.Furniture;
import DTO.MonthlyRentBill;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Objects;

public class FurnitureBUS {
    private ArrayList<Furniture> listFurniture = new ArrayList<>();
    public FurnitureBUS() {
        this.listFurniture = FurnitureDAO.getInstance().selectAll();
    }

    public ArrayList<Furniture> getAll(){
        return this.listFurniture;
    }

    public boolean add(Furniture furniture){
        boolean check = FurnitureDAO.getInstance().insert(furniture)!=0;
        if (check){
            this.listFurniture.add(furniture);
        }
        return check;
    }

    public boolean delete(Furniture furniture){
        boolean check = FurnitureDAO.getInstance().delete(furniture.getFurnitureID())!=0;
        if (check){
            this.listFurniture.remove(furniture);
        }
        return check;
    }

    public boolean update(Furniture furniture){
        boolean check = FurnitureDAO.getInstance().update(furniture)!=0;
        if (check){
            this.listFurniture.set(getIndexByFurnitureID(furniture.getFurnitureID()), furniture);
        }
        return check;
    }

    public int getIndexByFurnitureID(String furnitureID){
        int i=0;
        int vitri=-1;
        while (i < this.listFurniture.size() && vitri == -1){
            if (listFurniture.get(i).getFurnitureID().equals(furnitureID)){
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public ArrayList<Furniture> getFurnitureByApartmentID(String ApartmentID){
        ArrayList<Furniture> furnitureByApartmentID = new ArrayList<>();
        for (Furniture furniture : listFurniture) {
            if (Objects.equals(furniture.getApartmentID(), ApartmentID)) {
                furnitureByApartmentID.add(furniture);
            }
        }
        return  furnitureByApartmentID;
    }

    public int importExcel(String url){
        try {
            FileInputStream fis = new FileInputStream(url);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String furnitureID = row.getCell(0).getStringCellValue();
                    String apartmentID = row.getCell(1).getStringCellValue();
                    String nameFurniture = row.getCell(2).getStringCellValue();
                    String conditionFurniture = row.getCell(3).getStringCellValue();
                    double price = row.getCell(4).getNumericCellValue();
                    Furniture furniture = new Furniture(furnitureID, apartmentID, nameFurniture, conditionFurniture, price);
                    listFurniture.add(furniture);
                    add(furniture);
                }
            }

            fis.close();
            workbook.close();
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    public ArrayList<Furniture> searchApartments(String keyword, String buildingManagerID) {
        return FurnitureDAO.getInstance().search(keyword, buildingManagerID);
    }
}
