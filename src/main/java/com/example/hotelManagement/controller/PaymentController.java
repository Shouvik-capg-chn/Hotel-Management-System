//package com.example.hotelManagement.controller;
//
//import com.example.hotelManagement.model.ApiResponse;
//import com.example.hotelManagement.model.Payment;
//import com.example.hotelManagement.service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api") // enables both /api/payment/... and /api/payments/...
//public class PaymentController
//{
//
//    @Autowired
//    private PaymentService paymentService;
//
//    // POST /api/payment/post : Create a new payment
//    @PostMapping("/payment/post")
//    public ResponseEntity<ApiResponse> createPayment(@RequestBody Payment payment) {
//        return ResponseEntity.ok(paymentService.addPayment(payment));
//    }
//
//    // GET /api/payment/all : Get all payments
//    @GetMapping("/payment/all")
//    public ResponseEntity<?> getAllPayments() {
//        List<Payment> payments = paymentService.getAllPayments();
//        if (payments.isEmpty()) {
//            return ResponseEntity.ok(new ApiResponse("GETALLFAILS", "Payment list is empty"));
//        }
//        return ResponseEntity.ok(payments);
//    }
//
//    // GET /api/payment/{payment_id} : Get payment by id
//    @GetMapping("/payment/{payment_id}")
//    public ResponseEntity<?> getPaymentById(@PathVariable("payment_id") Integer paymentId) {
//        Payment payment = paymentService.getPaymentById(paymentId);
//        if (payment == null) {
//            return ResponseEntity.ok(new ApiResponse("GETFAILS", "Payment doesn't exist"));
//        }
//        return ResponseEntity.ok(payment);
//    }
//
//    // DELETE /api/payment/{payment_id} : Delete a payment
//    @DeleteMapping("/payment/{payment_id}")
//    public ResponseEntity<ApiResponse> deletePayment(@PathVariable("payment_id") Integer paymentId) {
//        return ResponseEntity.ok(paymentService.deletePaymentById(paymentId));
//    }
//
//    // GET /api/payments/status/{status} : Retrieve payments by status
//    @GetMapping("/payments/status/{status}")
//    public ResponseEntity<?> getPaymentsByStatus(@PathVariable("status") String status) {
//        List<Payment> payments = paymentService.getPaymentsByStatus(status);
//        if (payments.isEmpty()) {
//            return ResponseEntity.ok(new ApiResponse("GETALLFAILS", "Payment list is empty"));
//        }
//        return ResponseEntity.ok(payments);
//    }
//
//    // GET /api/payments/total-revenue : Retrieve the total revenue (Paid only)
//    @GetMapping("/payments/total-revenue")
//    public ResponseEntity<?> getTotalRevenue() {
//        BigDecimal totalRevenue = paymentService.getTotalRevenue();
//        Map<String, Object> result = new HashMap<>();
//        result.put("totalRevenue", totalRevenue);
//        return ResponseEntity.ok(result);
//    }
//}