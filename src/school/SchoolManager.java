package school;

import java.util.Scanner;

public class SchoolManager {
	private Scanner inputValue;
	private LessonSchedule lessonSchedule;
	private BookingData bookingData;
	private SwimmerData swimmerData;
	private ReportData reportData;
	private ReviewData reviewData;
	private CoachData coachData;

	public SchoolManager() {
		inputValue = new Scanner(System.in);
	}

	public void manageObject(AppManager appManager) {
		this.lessonSchedule = appManager.getLessonSchedule();
		this.bookingData = appManager.getBookingData();
		this.swimmerData = appManager.getSwimmerData();
		this.reportData = appManager.getReportData();
		this.reviewData = appManager.getReviewData();
		this.coachData = appManager.getCoachData();
	}

	// schoolManager panel
	public void managerPanel() {
		String action;
		do {
			System.out.println("\n________________________________");
			System.out.println("Manager Panel");
			System.out.println("________________________________");
			System.out.println("\n1. Manage swimmers");
			System.out.println("2. Timetable");
			System.out.println("3. Listing");
			System.out.println("4. Generate reports");
			System.out.println("5. Go to main menu");
			System.out.println();
			System.out.print("Enter your input : ");
			action = inputValue.nextLine().trim();
			switch (action) {
			case "1":
				manageSwimmers();
				break;
			case "2":
				lessonSchedule.lessonDetails(lessonSchedule.getAllLessons());
				// filter timetable
				lessonSchedule.lessonTimetable();
				break;
			case "3":
				handleListingMenu();
				break;
			case "4":
				reportData.generateReports();
				break;
			case "5":
				return;
			default:
				System.out.println("Invalid selection. Please try again.");
				break;
			}
		} while (!action.equals("5"));

		inputValue.close();
	}

	private void handleListingMenu() {
		String action;
		do {
			System.out.println("\n________________________________");
			System.out.println("Listing menu");
			System.out.println("________________________________");
			System.out.println("1. Booking listing");
			System.out.println("2. Coach listing");
			System.out.println("3. Review listing");
			System.out.println("4. Go back to previous menu");
			System.out.println();
			System.out.print("Enter your input : ");
			action = inputValue.nextLine().trim();

			switch (action) {
			case "1":
				// show all bookings
				bookingData.swimmerBookings(bookingData.getAllBookings());
				break;
			case "2":
				// show coach details
				coachData.showCoachDetails();
				break;
			case "3":
				// show all review
				reviewData.printReviewData(reviewData.getAllReviews());
				break;
			case "4":
				return;
			default:
				System.out.println("Invalid selection. Please try again.");
				break;
			}
		} while (!action.equals("4"));

		inputValue.close();
	}

	private void manageSwimmers() {
		String action = null;
		do {
			System.out.println("\n1. Add new swimmer");
			System.out.println("2. Swimmer listing");
			System.out.println("3. Go to previous menu");
			System.out.print("Enter your input : ");
			action = inputValue.nextLine();
			switch (action) {
			case "1":
				if(addNewSwimmer()) {
					System.out.println("Success ! new swimmer added successfully");
				}
				break;
			case "2":
				swimmerData.swimmerData();
				break;
			case "3":
			default:
				System.out.println("Invalid selection. Please try again.");
				break;
			}

		} while (!action.equals("3"));
	}

	// add new Swimmer
	public boolean addNewSwimmer() {
		String swimmerUserId;
		String swimmerName = "";
		String swimmerEmail = "";
		String swimmerContact = "";
		String age = "";
		int swimmerAge = 0;
		String level = "";
		int swimmerLevel = 0;
		boolean newSwimmerAdded = false;
		String swimmerGender = "";
		do {
			// Get swimmer's name
			if (swimmerName.isEmpty()) {
				System.out.print("Swimmer's Name: ");
				swimmerName = inputValue.nextLine().trim();
				if (swimmerName.isBlank() || swimmerName.length() < 5) {
					System.out.println(
							"Error: Swimmer's name cannot be empty.enter valid name at least alphabatic 5 alphabatical character");
					swimmerName = "";
				} else {
					if (swimmerName.matches(".*\\d.*")) {
						System.out.println("Error: Swimmer's name cannot contain numbers.");
						swimmerName = "";
					} else {
						if (isUniqueName(swimmerName)) {
							System.out.println("Sorry : This swimmer name already added");
							swimmerName = "";
						}
					}
				}
			} else if (swimmerGender.isEmpty()) {
				// Get swimmer's gender
				System.out.print("Swimmer's Gender (Male/Female): ");
				swimmerGender = inputValue.nextLine().trim();
				if (swimmerGender.isEmpty()) {
					System.out.println("Error: Gender cannot be empty.");
				} else {
					if (!swimmerGender.equalsIgnoreCase("male") && !swimmerGender.equalsIgnoreCase("m")
							&& !swimmerGender.equalsIgnoreCase("female") && !swimmerGender.equalsIgnoreCase("f")) {
						System.out.println("Error: Gender should be either Male or Female.");
						swimmerGender = "";
					} else {
						if (swimmerGender.equalsIgnoreCase("m")) {
							swimmerGender = "Male";
						} else if (swimmerGender.equalsIgnoreCase("f")) {
							swimmerGender = "Female";
						}
					}
				}
			} else if (swimmerEmail.isEmpty()) {
				// Get swimmer's email
				System.out.print("Swimmer's Email: ");
				swimmerEmail = inputValue.nextLine().trim();
				if (swimmerEmail.isEmpty() || !swimmerEmail.matches("^(.+)@(\\S+)$")) {
					System.out.println("Error: Invalid swimmer email format.");
					swimmerEmail = "";
				} else {
					if (isUniqueEmail(swimmerEmail)) {
						System.out.println("Sorry : This swimmer email id already added");
						swimmerEmail = "";
					}
				}
			} else if (swimmerContact.isEmpty()) {
				// Get swimmer's contact number
				System.out.print("Swimmer's Contact Number: ");
				swimmerContact = inputValue.nextLine().trim();
				if (swimmerContact.isEmpty() || !swimmerContact.matches("[0-9]+")) {
					System.out.println("Error: Invalid contact number.");
					swimmerContact = "";
				}
			} else if (age.isEmpty()) {
				// Get swimmer's age
				System.out.print("Swimmer's Age (4 to 11 year): ");
				age = inputValue.nextLine().trim();
				if (age.isEmpty() || !age.matches("[0-9]+")) {
					System.out.println("Error: Age should be a number between 4 and 11.");
					age = "";
				} else {
					swimmerAge = Integer.parseInt(age);
					if (swimmerAge < 4 || swimmerAge > 11) {
						System.out.println("Error: Swimmer's age should be between 4 and 11 years.");
						age = "";
					}
				}

			} else if (level.isEmpty()) {
				// Get swimmer's level
				System.out.print("Swimmer's Level (1-5): ");
				level = inputValue.nextLine().trim();
				if (!level.matches("\\d")) {
					System.out.println("Error: Level should be a number between 1 and 5.");
					level = "";
				} else {
					swimmerLevel = Integer.parseInt(level);
					if (!isValidSwimmerLevel(swimmerLevel)) {
						System.out.println("Error: Swimmer's level should be between 1 and 5.");
						level = "";
					}
				}
			} else {
				// generate swimmer userId
				swimmerUserId = (String) swimmerName.substring(0, 2).toUpperCase().concat("@123");
				// save new swimmer data
				swimmerData.allSwimmers.add(new SwimmerData(swimmerUserId, swimmerName, swimmerEmail, swimmerContact,
						swimmerGender, swimmerAge, swimmerLevel));
				newSwimmerAdded = true;
			}

		} while (!newSwimmerAdded);

		return newSwimmerAdded;

	}

	// validation of swimmer level
	public boolean isValidSwimmerLevel(int level) {
		if (level >= 1 && level <= 5) {
			return true;
		}
		return false;
	}

	// validation of unique swimmer name
	public boolean isUniqueName(String name) {
		for (SwimmerData swimmer : swimmerData.allSwimmers) {
			if (swimmer.getSwimmerName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	// validation of unique swimmer email
	public boolean isUniqueEmail(String email) {
		for (SwimmerData swimmer : swimmerData.allSwimmers) {
			if (swimmer.getSwimmerEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		return false;
	}

}
