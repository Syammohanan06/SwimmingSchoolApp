package school;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SwimmerData {
	private BookingData bookingData;
	private ReviewData reviewData;
	private AppManager appManager;
	private Scanner inputValue;
	private String swimmerUserId;
	private String swimmerName;
	private String swimmerContact;
	private String swimmerEmail;
	private String swimmerGender;
	private int swimmerAge;
	private int swimmerLevel;
	public List<SwimmerData> allSwimmers;

	public String getSwimmerUserId() {
		return swimmerUserId;
	}

	public String getSwimmerName() {
		return swimmerName;
	}

	public String getSwimmerContact() {
		return swimmerContact;
	}

	public String getSwimmerEmail() {
		return swimmerEmail;
	}

	public String getSwimmerGender() {
		return swimmerGender;
	}

	public int getSwimmerAge() {
		return swimmerAge;
	}

	public int getSwimmerLevel() {
		return swimmerLevel;
	}
	public void setSwimmerLevel(int swimmerLevel) {
		this.swimmerLevel=swimmerLevel;
	}

	public List<SwimmerData> getAllSwimmers() {
		return allSwimmers;
	}

	// create constructor to store swimmer data
	public SwimmerData(String swimmerUserId, String swimmerName, String swimmerEmail, String swimmerContact,
			String swimmerGender, int swimmerAge, int swimmerLevel) {
		this.swimmerUserId = swimmerUserId;
		this.swimmerName = swimmerName;
		this.swimmerEmail = swimmerEmail;
		this.swimmerContact = swimmerContact;
		this.swimmerGender = swimmerGender;
		this.swimmerAge = swimmerAge;
		this.swimmerLevel = swimmerLevel;
	}

	// default constructor
	public SwimmerData() {
		this.allSwimmers = new ArrayList<>();
		this.inputValue = new Scanner(System.in);
		defaultSwimmerData();

	}

	// initialized object
	public void manageObject(AppManager appManager) {
		this.appManager = appManager;
		this.bookingData = appManager.getBookingData();
		this.reviewData=appManager.getReviewData();
	}

	// store default swimmer data
	public void defaultSwimmerData() {
		allSwimmers.add(new SwimmerData("JS@123", "Matthew Green", "matthew@gmail.com", "+4402345678901", "Male", 11, 5));
		allSwimmers.add(new SwimmerData("EJ@123", "Olivia Roberts", "olivia@gmail.com", "+4409876543210", "Female", 9, 4));
		allSwimmers.add(new SwimmerData("MB@123", "Benjamin Clarke", "benjamin@gmail.com", "+4404567890123", "Male", 6, 3));
		allSwimmers.add(new SwimmerData("SW@123", "Sophia Thompson", "sophia@gmail.com", "+4406543219870", "Female", 4, 2));
		allSwimmers.add(new SwimmerData("DT@123", "Daniel Murphy", "daniel@gmail.com", "+4403219876543", "Male", 5, 1));
		allSwimmers.add(new SwimmerData("OD@123", "Ava Carter", "ava@gmail.com", "+4407890123456", "Female", 9, 5));
		allSwimmers.add(new SwimmerData("JE@123", "Jacob Evans", "jacob@gmail.com", "+4404561237890", "Male", 8, 4));
		allSwimmers.add(new SwimmerData("ET@123", "Ella Harrison", "ella@gmail.com", "+4409870123456", "Female", 4, 3));
		allSwimmers.add(new SwimmerData("TH@123", "Nathan White", "nathan@gmail.com", "+4403216549870", "Male", 7, 2));
		allSwimmers.add(new SwimmerData("CC@123", "Isabella Scott", "isabella@gmail.com", "+4406547890123", "Female", 6, 1));
		allSwimmers.add(new SwimmerData("AB@123", "Charlotte Baker", "charlotte@gmail.com", "+4407896543210", "Female", 9, 5));

	}

	// swimmer panel
	public void swimmerPanel() {
		String action;
		String listing;
		String userId = appManager.getUserId();
		String name = getSwimmerDetailsById(userId).getSwimmerName();
		int level = getSwimmerDetailsById(userId).getSwimmerLevel();
		System.out.println("\nSwimmer Panel");
		System.out.println("________________________________");
		System.out.println("Swimmer User id  : " + userId);
		System.out.println("Swimmer Name     : " + name);
		System.out.println("Swimmer Level    : " + level);
		System.out.println("________________________________");
		do {
			System.out.println("\n1. Book lesson");
			System.out.println("2. Attend lesson");
			System.out.println("3. Change lesson");
			System.out.println("4. Cancel lesson");
			System.out.println("5. Listing ");
			System.out.println("6. Go to main menu");
			System.out.println();
			System.out.print("Enter your input : ");
			action = inputValue.nextLine().trim();
			// call method by selectMenu by
			switch (action) {
			case "1" -> {
				boolean bookedLesson=bookingData.swimmerBookedLesson();
				if(bookedLesson) {
					System.out.println("Success ! Lesson booked successfully");
				}
			}
			case "2" -> {
				boolean attendLesson=bookingData.attendLessonBySwimmer();
				if(attendLesson) {
					System.out.println("Success ! Lesson attended successfully");
				}
			}
			case "3" -> {
				boolean changedLesson=bookingData.changeLessonBySwimmer();
				if(changedLesson) {
					System.out.println("Success ! Lesson changed successfully");
				}
			}
			case "4" -> {
				boolean cancelLesson=bookingData.cancelledLessonBySwimmer();
				if(cancelLesson) {
					System.out.println("Success ! Lesson cancelled successfully");
				}
			}
			case "5" -> {
				System.out.println("\n________________________________");
				System.out.println("Listing menu");
				System.out.println("________________________________");
				System.out.println("\n1. Booking listing");
				System.out.println("2. Feedback listing");
				System.out.print("Select listing type : ");
				listing = inputValue.nextLine().trim();
				if (listing.equals("1")) {
					System.out.println("\n________________________________");
					System.out.println("My booking details");
					System.out.println("________________________________");
					bookingData.swimmerBookings(bookingData.swimmerBookingData(userId, ""));
				} else if (listing.equals("2")) {
					System.out.println("\n________________________________");
					System.out.println("My attended lesson review");
					System.out.println("________________________________");
					reviewData.printReviewData(reviewData.getSwimmerRatingData(userId));
				} else {
					System.out.println("Incorrect input");
				}
			}
			case "6" -> {
				return;
			}
			default -> System.out.println("Invalid selection. Please try again.");
			}
		} while (!action.equals("e"));
	}
	
	//show swimmer data
	public void swimmerData() {
		System.out.println("\n________________________________");
		System.out.println("Swimmer details");
		System.out.println("________________________________");
		System.out
				.println("__________________________________________________________________________________________________"
						+ "________________________");
		System.out.printf("| %-12s %-20s  %-15s  %-25s  %-10s  %-10s %-15s |\n", "User Id", "Name", "Contact", "Email",
				"Level", "Age","Gender");
		System.out
				.println("__________________________________________________________________________________________________"
						+ "________________________");
		Iterator<SwimmerData> iterator = allSwimmers.iterator();
		while (iterator.hasNext()) {
			// Print coach details
			SwimmerData swimmer = iterator.next();
			System.out.printf("| %-12s %-20s  %-15s  %-25s  %-10s  %-10s %-15s |\n", swimmer.getSwimmerUserId(),
					swimmer.getSwimmerName(), swimmer.getSwimmerContact(), swimmer.getSwimmerEmail(),
					swimmer.getSwimmerLevel(),swimmer.getSwimmerAge(),swimmer.getSwimmerGender());
		}
		System.out
				.println("__________________________________________________________________________________________________"
						+ "________________________");
	}

	// get swimmer detailsl by id
	public SwimmerData getSwimmerDetailsById(String userId) {
		for (int id = 0; id < allSwimmers.size(); id++) {
			if (allSwimmers.get(id).getSwimmerUserId().equalsIgnoreCase(userId)) {
				return allSwimmers.get(id);
			}
		}
		return null;
	}

}
