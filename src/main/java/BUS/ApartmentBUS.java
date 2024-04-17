/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ApartmentDAO;
import DTO.Apartment;

import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class ApartmentBUS {
    private ArrayList<Apartment> listApartment = new ArrayList<>();

    public ApartmentBUS() {
        this.listApartment = ApartmentDAO.getInstance().selectAll();
    }

    public ArrayList<Apartment> getAll(){
        return this.listApartment;
    }

    public boolean add(Apartment apartment){
        boolean check = ApartmentDAO.getInstance().insert(apartment)!=0;
        if (check){
            this.listApartment.add(apartment);
        }
        return check;
    }

    public boolean delete(Apartment apartment){
        boolean check = ApartmentDAO.getInstance().delete(apartment.getApartmentID())!=0;
        if (check){
            this.listApartment.remove(apartment);
        }
        return check;
    }

    public boolean update(Apartment apartment){
        boolean check = ApartmentDAO.getInstance().update(apartment)!=0;
        if (check){
            this.listApartment.set(getIndexByApartmentID(apartment.getApartmentID()), apartment);
        }
        return check;
    }

    public int getIndexByApartmentID(String apartmentID){
        int i=0;
        int vitri=-1;
        while (i < this.listApartment.size() && vitri == -1){
            if (listApartment.get(i).getApartmentID().equals(apartmentID)){
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }
}
