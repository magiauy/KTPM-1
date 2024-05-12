/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BUS.MonthlyRentBillBUS;
import DTO.Apartment;



/**
 *
 * @author NGOC
 */
public class Test {
    public static void main(String[] args) {
        MonthlyRentBillBUS mRB = new MonthlyRentBillBUS();
        mRB.XuatExcelPhieuThang("MRB1", "D:/OneDrive/Desktop");
    }
}
