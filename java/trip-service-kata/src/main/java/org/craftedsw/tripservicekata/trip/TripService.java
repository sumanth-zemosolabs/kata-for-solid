package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedUser = getLoggedUser();
		isUserLoggedIn(loggedUser);
		return getFriendList(user,  loggedUser);
	}

	private void isUserLoggedIn(User loggedUser) throws UserNotLoggedInException{
		if (loggedUser==null)
			throw new UserNotLoggedInException();
	}

	private List<Trip> getFriendList(User user, User loggedUser) {
		boolean isFriend = false;
		for (User friend : user.getFriends()) {
			if (friend.equals(loggedUser)) {
				isFriend = true;
				break;
			}
		}
		if (isFriend) {
			return getTripList(user);
		}
		return new ArrayList<>();
	}

	protected List<Trip> getTripList(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
