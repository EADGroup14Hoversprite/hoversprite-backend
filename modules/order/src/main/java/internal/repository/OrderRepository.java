package internal.repository;

import internal.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shared.enums.OrderSlot;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COUNT(o) FROM Order o WHERE o.desiredDate = :desiredDate AND o.timeSlot = :timeSlot")
    Long countByDesiredDateAndTimeSlot(@Param("desiredDate") LocalDate desiredDate, @Param("timeSlot") OrderSlot timeSlot);

    List<Order> findAllByFarmerId(Long farmerId);

    @Query("SELECT o FROM Order o WHERE o.desiredDate BETWEEN :startDate AND :endDate")
    List<Order> findAllWithinWeek(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
