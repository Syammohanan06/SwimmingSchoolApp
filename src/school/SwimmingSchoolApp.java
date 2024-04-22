package school;

import java.util.Scanner;

public class SwimmingSchoolApp {
	private AppManager appManager;
	private Scanner inputValue;
	private SwimmerData swimmerData;
	private BookingData bookingData;
	private LessonSchedule lessonSchedule;
	private SchoolManager schoolManager;
	private ReportData reportData;
	private ReviewData reviewData;

	public SwimmingSchoolApp(AppManager appManager) {
		this.inputValue = new Scanner(System.in);
		this.appManager = appManager;
		this.swimmerData = appManager.getSwimmerData();
		swimmerData.manageObject(appManager);
		appManager.getCoachData();
		this.bookingData = appManager.getBookingData();
		bookingData.manageObject(appManager);
		this.lessonSchedule = appManager.getLessonSchedule();
		lessonSchedule.manageObject(appManager);
		this.schoolManager = appManager.getSchoolManager();
		schoolManager.manageObject(appManager);
		this.reportData = appManager.getReportData();
		reportData.manageObject(appManager);
		this.reviewData = appManager.getReviewData();
		reviewData.manageObject(appManager);

	}

	public void schoolName() {
		System.out.println("\nSwimming School App");
		System.out.println("________________________________");
	}

	public void openApp() {
		String action;
		String userId;
		do {
			System.out.println("\n1. Login as Swimmer");
			System.out.println("2. Login as Manager");
			System.out.println("3. Exit");
			System.out.print("Enter action : ");
			action = inputValue.nextLine().trim();

			switch (action) {
			case "1" -> {
				System.out.print("Enter your userId : ");
				userId = inputValue.nextLine().trim();
				// Validate swimmer user id
				String swimmerUserId = appManager.correctSwimmerUserId(userId);
				if (userId.isEmpty()) {
					System.out.println("Error: User ID cannot be empty. ");
				} else if (swimmerUserId == null) {
					System.out.println("Error: User ID not registered as a swimmer.");
				} else {
					appManager.setUserId(swimmerUserId);
					System.out.println("Swimmer logged in successfully.");
					swimmerData.swimmerPanel();
				}
				break;
			}
			case "2" -> {
				System.out.print("Enter your userId : ");
				userId = inputValue.nextLine().trim();
				String managerUserId = appManager.getManagerUserId();
				if (userId.isEmpty()) {
					System.out.println("Error: User ID cannot be empty ");
				} else if (!userId.equalsIgnoreCase(managerUserId)) {
					System.out.println("Error: Incorrect user ID .");
				} else {
					System.out.println("Manager logged in successfully.");
					schoolManager.managerPanel();

				}
				break;
			}
			case "3" -> {
				System.out.println("Exiting the application...");
				System.exit(0);
			}
			default -> {
				System.out.println("Error: Invalid action. Please enter a valid option .");
			}
			}

		} while (!action.equals("3"));

	}

	public static void main(String[] args) {
		// initialize app manager class
		AppManager appManager = new AppManager();
		SwimmingSchoolApp swimmingApp = new SwimmingSchoolApp(appManager);
		swimmingApp.schoolName();
		swimmingApp.openApp();

	}

}
