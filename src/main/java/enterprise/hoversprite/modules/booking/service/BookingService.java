package enterprise.hoversprite.modules.booking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enterprise.hoversprite.modules.booking.model.Booking;
import enterprise.hoversprite.modules.booking.repository.BookingRepository;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking saveBooking(Booking booking) {
        return null;
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return null;
    }

    @Override
    public Booking[] getAllBookings() {
        return null;
    }

    @Override
    public Optional<Booking[]> getBookingByFarmerId(Long id) {
        return null;
    }

    @Override
    public void deleteBooking(Booking booking) {
    }
}
