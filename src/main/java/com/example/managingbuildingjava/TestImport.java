package com.example.managingbuildingjava;

import BUS.FurnitureBUS;
import DAO.FurnitureDAO;
import DTO.Furniture;

import java.util.ArrayList;

public class TestImport {
    public static void main(String[] args) {
        FurnitureBUS bus = new FurnitureBUS();
        bus.importExcel("D:\\OneDrive\\Desktop\\Data\\code\\Java\\BuildingOperationsApplication\\src\\NhapNoiThat.xlsx");
        ArrayList<Furniture> furnitures = FurnitureDAO.getInstance().selectAll();
        for (Furniture f : furnitures) {
            System.out.println(f.toString());
        }
    }
}
