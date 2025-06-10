//package org.example.bookshop;
//
//import org.example.bookshop.dto.PurchaseItemDTO;
//import org.example.bookshop.dto.PurchaseRequestDTO;
//import org.example.bookshop.service.PurchaseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class ConcurrencyPurchaseTest implements CommandLineRunner {
//
//    @Autowired
//    private PurchaseService purchaseService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Runnable customer1 = () -> {
//            try {
//                PurchaseRequestDTO request = new PurchaseRequestDTO();
//                request.setCustomerId(1L);
//                request.setItems(List.of(new PurchaseItemDTO(4L, 1)));
//
//                purchaseService.purchaseProducts(request);
//                System.out.println("Klient 1: Zakup udany");
//            } catch (Exception e) {
//                System.out.println("Klient 1: Błąd – " + e.getMessage());
//            }
//        };
//
//        Runnable customer2 = () -> {
//            try {
//                PurchaseRequestDTO request = new PurchaseRequestDTO();
//                request.setCustomerId(2L);
//                request.setItems(List.of(new PurchaseItemDTO(4L, 3)));
//
//                purchaseService.purchaseProducts(request);
//                System.out.println("Klient 2: Zakup udany");
//            } catch (Exception e) {
//                System.out.println("Klient 2: Błąd – " + e.getMessage());
//            }
//        };
//
//        Thread t1 = new Thread(customer1);
//        Thread t2 = new Thread(customer2);
//
//        t1.start();
//        t2.start();
//
//        t1.join();
//        t2.join();
//    }
//}