//package com.example.hotelManagement.repository;
//
//
//import com.example.hotelManagement.model.Payment;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.data.repository.query.Param;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface PaymentRepository extends JpaRepository<Payment, Integer>
//{
//
//    //Optional<Payment> findByReservationId(Integer reservationId);
//
//    List<Payment> findByPaymentStatusIgnoreCase(String paymentStatus);
//
//    // Sum amounts for a given status (used for total revenue with status = 'Paid')
//    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE LOWER(p.paymentStatus) = LOWER(:status)")
//    BigDecimal sumAmountByPaymentStatus(@Param("status") String status);
//}
