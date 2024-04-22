package school;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CoachData implements Iterable<CoachData> {
	private String coachUserId;
	private String coachName;
	private String coachDOB;
	private String coachEmail;
	private String coachGender;
	private String coachContact;
	private List<CoachData> allCoaches;

	// Constructor to initialize coach data
	public CoachData(String coachUserId, String coachName, String coachEmail, String coachContact,
			String coachGender, String coachDOB) {
		this.coachUserId = coachUserId;
		this.coachName = coachName;
		this.coachEmail = coachEmail;
		this.coachContact = coachContact;
		this.coachGender = coachGender;
		this.coachDOB = coachDOB;
	}

	// Default constructor to initialize list of all coachs
	public CoachData() {
		this.allCoaches = new ArrayList<>();
		storeDefaultCoachs();
	}

	// Method to initialize default coach data
	private void storeDefaultCoachs() {
		allCoaches.add(new CoachData("EW@123", "Emily White", "emily@gmail.com", "+447890123456", "Female", "27-Mar-1985"));
		allCoaches.add(new CoachData("DJ@123", "David Johnson", "david@gmail.com", "+447654321090", "Male", "15-Jun-1980"));
		allCoaches.add(new CoachData("SB@123", "Sophie Brown", "sophie@gmail.com", "+447123456789", "Female","24-Apr-1990"));
		allCoaches.add(new CoachData("RA@123", "Ryan Adams", "ryan@gmail.com", "+44654321876", "Male", "03-Mar-1978"));
		allCoaches.add(new CoachData("LT@123", "Lucy Taylor", "lucy@gmail.com", "+44823456789", "Female", "24-Apr-1987"));
	}

	// Iterator implementation for CoachData
	@Override
	public Iterator<CoachData> iterator() {
		return allCoaches.iterator();
	}

	// Getters for coach properties
	public String getCoachUserId() {
		return coachUserId;
	}

	public String getCoachName() {
		return coachName;
	}

	public String getCoachDOB() {
		return coachDOB;
	}

	public String getCoachEmail() {
		return coachEmail;
	}

	public String getCoachGender() {
		return coachGender;
	}

	public String getCoachContact() {
		return coachContact;
	}
	
	public List<CoachData> getAllCoachs() {
		return allCoaches;
	}

	// Method to display all coach details
	public void showCoachDetails() {
		System.out.println("\n________________________________");
		System.out.println("Coach details");
		System.out.println("________________________________");
		System.out
				.println("____________________________________________________________________________________________"
						+ "________________________");
		System.out.printf("| %-12s %-20s  %-15s  %-25s  %-15s  %-15s  |\n", "User Id", "Name", "Contact", "Email",
				"Gender", "Date Of Birth");
		System.out
				.println("____________________________________________________________________________________________"
						+ "________________________");
		Iterator<CoachData> iterator = iterator();
		while (iterator.hasNext()) {
			// Print coach details
			CoachData coach = iterator.next();
			System.out.printf("| %-12s %-20s  %-15s  %-25s  %-15s  %-15s  |\n", coach.getCoachUserId(),
					coach.getCoachName(), coach.getCoachContact(), coach.getCoachEmail(),
					coach.getCoachGender(), coach.getCoachDOB());
		}
		System.out
				.println("____________________________________________________________________________________________"
						+ "________________________");
	}

	// Method to find coach details by user ID
	public CoachData getCoachDetailsById(String userId) {
		for (CoachData coach : allCoaches) {
			if (coach.getCoachUserId().equalsIgnoreCase(userId)) {
				return coach;
			}
		}
		return null;
	}

	// Method to validate if a given user ID belongs to a coach
	public boolean isValidCoachUserId(String userId) {
		for (CoachData coach : allCoaches) {
			if (coach.getCoachUserId().equalsIgnoreCase(userId)) {
				return true;
			}
		}
		return false;
	}
}