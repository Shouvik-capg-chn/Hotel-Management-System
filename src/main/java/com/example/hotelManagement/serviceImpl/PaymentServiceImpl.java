//package com.example.hotelManagement.serviceImpl;
//
//import com.example.hotelManagement.model.ApiResponse;
//import com.example.hotelManagement.model.Payment;
//import com.example.hotelManagement.repository.PaymentRepository;
//import com.example.hotelManagement.service.PaymentService;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//
//    private final PaymentRepository paymentRepository;
//
//    public PaymentServiceImpl(PaymentRepository paymentRepository) {
//        this.paymentRepository = paymentRepository;
//    }
//
//    @Override
//    public ApiResponse addPayment(Payment payment) {
//        // Simple duplicate guard: if client sends existing payment_id
//        if (payment.getPaymentId() != null && paymentRepository.existsById(payment.getPaymentId())) {
//            return new ApiResponse("ADDFAILS", "Payment already exist");
//        }
//        paymentRepository.save(payment);
//        return new ApiResponse("POSTSUCCESS", "Payment added successfully");
//    }
//
//    @Override
//    public List<Payment> getAllPayments() {
//        return paymentRepository.findAll();
//    }
//
//    @Override
//    public Payment getPaymentById(Integer paymentId) {
//        return paymentRepository.findById(paymentId).orElse(null);
//    }
//
//    @Override
//    public ApiResponse deletePaymentById(Integer paymentId) {
//        if (!paymentRepository.existsById(paymentId)) {
//            return new ApiResponse("DLTFAILS", "Payment doesn't exist");
//        }
//        paymentRepository.deleteById(paymentId);
//        return new ApiResponse("DELETESUCCESS", "Payment deleted successfully");
//    }
//
//    @Override
//    public List<Payment> getPaymentsByStatus(String status) {
//        return paymentRepository.findByPaymentStatusIgnoreCase(status);
//    }
//
//    @Override
//    public BigDecimal getTotalRevenue() {
//        // Business rule: revenue = sum(amount) where status = 'Paid'
//        BigDecimal total = paymentRepository.sumAmountByPaymentStatus("Paid");
//        return total != null ? total : BigDecimal.ZERO;
//    }
//}
