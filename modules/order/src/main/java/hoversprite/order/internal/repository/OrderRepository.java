package hoversprite.order.internal.repository;

import hoversprite.order.internal.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import hoversprite.common.external.enums.OrderSlot;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.desiredDate = :desiredDate AND o.timeSlot = :timeSlot AND o.status <> 'CANCELLED'")
    List<Order> getPendingOrdersByDesiredDateAndTimeSlot(@Param("desiredDate") LocalDate desiredDate, @Param("timeSlot") OrderSlot timeSlot);

    Page<Order> findAllByBookerId(Long bookerId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.desiredDate BETWEEN :startDate AND :endDate")
    List<Order> findAllWithinDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT o FROM Order o JOIN o.assignedSprayerIds s WHERE s = :sprayerId")
    List<Order> findAllOrdersByAssignedSprayerIds(@Param("sprayerId") Long sprayerId);

    List<Order> findAllByDesiredDate(LocalDate desiredDate);
}
