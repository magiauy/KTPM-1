package BUS;

import DAO.FurnitureDAO;
import DTO.Furniture;
import DTO.MonthlyRentBill;

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
}
