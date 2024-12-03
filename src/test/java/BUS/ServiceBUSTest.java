package BUS;

import DTO.Service;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceBUSTest {

    @Test
    @org.junit.jupiter.api.Order(1)
    void add() {
        ServiceBUS serviceBUS = ServiceBUS.getInstance();
        int size = serviceBUS.getAll().size();
        Service service = new Service("1", "name", 1.0, "unit", "type");
        assertTrue(serviceBUS.add(service));
        System.out.println("Dự kiến:"+(size+1) +"|Kết quả:"+serviceBUS.getAll().size());
        assertEquals(size + 1, serviceBUS.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void addButFail() {
        ServiceBUS serviceBUS = ServiceBUS.getInstance();
        int size = serviceBUS.getAll().size();
        Service service = new Service("1", "name", 1.0, "unit", "type");
        boolean result = serviceBUS.add(service);
        assertFalse(result);
        System.out.println("Expected: False"+"|Result:"+result);
        System.out.println("Expected:"+size+"|Result:"+serviceBUS.getAll().size());
        assertEquals(size, serviceBUS.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    //Kiểm tra đối tượng Service null và đảm bảo số lượng không thay đổi
    void addNull() {
        ServiceBUS serviceBUS = ServiceBUS.getInstance();
        int size = serviceBUS.getAll().size();
        boolean result = serviceBUS.add(null);
        System.out.println("Expected: false | Result: " + result);
        System.out.println("Expected size: " + size + " | Result size: " + serviceBUS.getAll().size());
        assertFalse(result);
        assertEquals(size, serviceBUS.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void addInvalidData() {
        ServiceBUS serviceBUS = ServiceBUS.getInstance();
        int size = serviceBUS.getAll().size();
        Service service = new Service("", "", -1.0, "", "");
        assertFalse(serviceBUS.add(service));
        assertEquals(size, serviceBUS.getAll().size());
    }

    @Test
    void getAll() {
    }
}