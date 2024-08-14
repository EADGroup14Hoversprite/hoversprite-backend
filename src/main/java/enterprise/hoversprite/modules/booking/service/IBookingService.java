package enterprise.hoversprite.modules.booking.service;

import java.util.Optional;

import enterprise.hoversprite.modules.booking.model.Booking;

public interface IBookingService {
    public Booking saveBooking(Booking booking);

    public Optional<Booking> getBookingById(Long id);

    public Booking[] getAllBookings();

    public Optional<Booking[]> getBookingByFarmerId(Long id);

    public void deleteBooking(Booking booking);
}
