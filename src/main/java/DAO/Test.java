/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Apartment;



/**
 *
 * @author NGOC
 */
public class Test {
    public static void main(String[] args) {
       Apartment apartment = new Apartment("11111", "3123123123", "12214342342",  2.0 , 0, 0, "urniture");
       ApartmentDAO.getInstance().insert(apartment);
       ApartmentDAO.getInstance().delete("11111");

    }
}
