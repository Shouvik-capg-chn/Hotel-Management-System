package com.example.hotelManagement.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotelManagement.model.Payment;
import com.example.hotelManagement.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Get all payments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved payments"),
        @ApiResponse(responseCode = "404", description = "Payment list is empty")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        if (payments.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\":\"GETALLFAILS\",\"message\":\"Payment list is empty\"}");
        }
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payment by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved payment"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable Integer paymentId) {
        Optional<Payment> payment = paymentService.getPaymentById(paymentId);
        if (payment.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\":\"GETALLFAILS\",\"message\":\"Payment list is empty\"}");
        }
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Get payments by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved payments"),
        @ApiResponse(responseCode = "404", description = "No payments found with given status")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getPaymentsByStatus(@PathVariable String status) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        if (payments.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\":\"GETALLFAILS\",\"message\":\"Payment list is empty\"}");
        }
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get total revenue from payments")
    @ApiResponse(responseCode = "200", description = "Successfully calculated total revenue")
    @GetMapping("/total-revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        return ResponseEntity.ok(paymentService.getTotalRevenue());
    }

    @Operation(summary = "Add a new payment")
    @ApiResponse(responseCode = "200", description = "Payment added successfully")
    @PostMapping("/post")
    public ResponseEntity<?> addPayment(@RequestBody Payment payment) {
        Payment saved = paymentService.addPayment(payment);
        return ResponseEntity.ok("{\"code\":\"POSTSUCCESS\",\"message\":\"Payment added successfully\"}");
    }

    @Operation(summary = "Delete a payment by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer paymentId) {
        boolean deleted = paymentService.deletePayment(paymentId);
        if (deleted) {
            return ResponseEntity.ok("{\"code\":\"DELETESUCCESS\",\"message\":\"Payment deleted successfully\"}");
        } else {
            return ResponseEntity.status(404).body("{\"code\":\"DLTFAILS\",\"message\":\"Payment doesn't exist\"}");
        }
    }
}