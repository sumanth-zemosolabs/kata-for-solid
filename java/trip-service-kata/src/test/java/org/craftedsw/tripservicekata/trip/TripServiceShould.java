package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.List;

public class TripServiceShould {
    private User loggedUser = new User();
    private User notLoggedUser = new User();
    private User anotherUser = new User();
    private TripService tripService = new TripService(){

        @Override
        protected List<Trip> getTripList(User user) {
            return user.trips();
        }

        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }
    };

    @Before
    public void beforeClass(){
//        tripService =
    }
	@Test(expected = UserNotLoggedInException.class)
    public void testGetTripsByUserForNoLoggedInUser(){
        loggedUser = null;
        tripService.getTripsByUser(anotherUser);
    }

    @Test
    public void testGetTripByUserForLoggedInUser(){
        tripService.getTripsByUser(notLoggedUser);
    }

    @Test
    public void testGetTripByUserLoggedInAndFriend(){
        anotherUser.addFriend(loggedUser);
        anotherUser.addTrip(new Trip());
        anotherUser.addTrip(new Trip());
        assertEquals(2, tripService.getTripsByUser(anotherUser).size());
    }

    @Test
    public void testGetTripByUserLoggedInAndNotFriend(){
        anotherUser.addFriend(new User());
        assertEquals(0, tripService.getTripsByUser(anotherUser).size());
    }
}
