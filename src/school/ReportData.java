package school;

import java.text.DecimalFormat;
import java.time.Month;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReportData {
	private Scanner inputValue;
	private CoachData coachData;
	private ReviewData reviewData;
	private SwimmerData swimmerData;
	private BookingData bookingData;
	private String reportMonth;

	// initialized object
	public void manageObject(AppManager appManager) {
		inputValue = new Scanner(System.in);
		this.reviewData = appManager.getReviewData();
		this.swimmerData = appManager.getSwimmerData();
		this.bookingData = appManager.getBookingData();
		this.coachData = new CoachData();
	}

	public void generateReports() {
		String reportType;
		do {
			System.out.println("\n________________________________");
			System.out.println("Generate Report");
			System.out.println("________________________________");
			System.out.println("\n1. Swimmer report");
			System.out.println("2. Coach report");
			System.out.println("3. Go to previous menu");
			System.out.print("Enter your input to generate report : ");
			reportType = inputValue.nextLine().trim();
			// get report month
			if (reportType.matches("[1-2]")) {
				monthReport();
			}
			switch (reportType) {
			case "1":
				if (reportMonth != null) {
					printSwimmerReport(reportMonth.substring(0, 3));
				}
				break;
			case "2":
				if (reportMonth != null) {
					printCoachReport(reportMonth.substring(0, 3));
				}
				break;
			case "3":
				return;
			default:
				System.out.println("Invalid selection. Please try again.");
				break;
			}
		} while (!reportType.equals("3"));
	}

	// get month from user to generate report of this month
	public void monthReport() {
		String month;
		System.out.print("Enter month name (Ex : January or Jan) to view report : ");
		month = inputValue.nextLine().trim();
		if (month.isEmpty() || validateMonthName(month) == null) {

		} else {
			this.reportMonth = validateMonthName(month);
		}
	}

	public void printCoachReport(String month) {
		boolean generateReport = false;
		System.out.println("\nCoach performance report for month " + reportMonth);
		System.out.println("___________________________________________");
		Iterator<CoachData> iterator = coachData.getAllCoachs().iterator();
		while (iterator.hasNext()) {
			CoachData coach = iterator.next();
			String coachUserId = coach.getCoachUserId();
			double averageRating = calculateCoachPerformance(coachUserId, month);
			if (averageRating != 0) {
				System.out.println("Coach details");
				System.out.println(
						"____________________________________________________________________________________________"
								+ "_______________________________________________");
				System.out.printf("| %-12s %-20s  %-15s  %-25s  %-15s  %-15s  %-15s |\n", "User Id", "Name", "Contact",
						"Email", "Gender", "Date Of Birth", "Average Rating");
				System.out.println(
						"____________________________________________________________________________________________"
								+ "_______________________________________________");
				System.out.printf("| %-12s %-20s  %-15s  %-25s  %-15s  %-15s  %-15s  |\n", coach.getCoachUserId(),
						coach.getCoachName(), coach.getCoachContact(), coach.getCoachEmail(),
						coach.getCoachGender(), coach.getCoachDOB(),  new DecimalFormat("0.##").format(averageRating));
				System.out.println(
						"____________________________________________________________________________________________"
								+ "_______________________________________________");
				
				generateReport=true;
			}
		}
		if (!generateReport) {
			System.out.println("Sorry ! No coach performance data available in record");
		}
	}

	public void printSwimmerReport(String month) {
		boolean generateReport = false;
		System.out.println("\nSwimmer Report of Month " + reportMonth);
		System.out.println("___________________________________________");
		Iterator<SwimmerData> iterator = swimmerData.getAllSwimmers().iterator();
		while (iterator.hasNext()) {
			SwimmerData swimmer = iterator.next();
			String userId = swimmer.getSwimmerUserId();
			int swimmerTotalBooking = countSwimmerBookingData(userId, month).get("totalBooking");
			int swimmerTotalAttend = countSwimmerBookingData(userId, month).get("totalAttend");
			int swimmerTotalCancel = countSwimmerBookingData(userId, month).get("totalCancel");
			if (!(swimmerTotalBooking == 0 && swimmerTotalAttend == 0 && swimmerTotalCancel == 0)) {
				System.out.println("\nSwimmer details");
				System.out.println(
						"____________________________________________________________________________________________"
								+ "_______________________________________________________________________________");
				System.out.printf("| %-12s %-20s  %-15s  %-25s  %-10s  %-10s %-15s %-15s %-15s %-15s |\n", "User Id",
						"Name", "Contact", "Email", "Level", "Age", "Gender", "Total Book ", "Total Attend",
						"Total Cancel");
				System.out.println(
						"_____________________________________________________________________________________________"
								+ "______________________________________________________________________________");
				System.out.printf("| %-12s %-20s  %-15s  %-25s  %-10s  %-10s %-15s  %-15s %-15s %-15s |\n",
						swimmer.getSwimmerUserId(), swimmer.getSwimmerName(), swimmer.getSwimmerContact(),
						swimmer.getSwimmerEmail(), swimmer.getSwimmerLevel(), swimmer.getSwimmerAge(),
						swimmer.getSwimmerGender(), swimmerTotalBooking, swimmerTotalAttend, swimmerTotalCancel);
				System.out.println(
						"_____________________________________________________________________________________________"
								+ "______________________________________________________________________________");
				System.out.println("\nBooking details");
				// show swimmer booking details
				bookingData.swimmerBookings(bookingData.swimmerBookingData(userId, month));
				generateReport = true;
			}

		}
		if (!generateReport) {
			System.out.println("Sorry ! No swimmer booking data available in record");
		}
	}

	// calculate coach average rating
	public double calculateCoachPerformance(String coachUserId, String month) {
		double totalRatings = 0;
		int totalTeachLesson = 0;
		double averageRating = 0;
		// get coach performance details
		Iterator<ReviewData> iterator = reviewData.getCoachRatingData(month, coachUserId).iterator();
		while (iterator.hasNext()) {
			ReviewData review = iterator.next();
			totalRatings += review.getStarRating();
			totalTeachLesson++;
		}
		// calculate average rating
		if (!(totalRatings == 0 && totalTeachLesson == 0)) {
			averageRating = totalRatings / totalTeachLesson;
		}
		return averageRating;
	}

	// count swimmer booking
	public Map<String, Integer> countSwimmerBookingData(String userId, String month) {
		int swimmerTotalBooking = 0;
		int swimmerTotalAttend = 0;
		int swimmerTotalCancel = 0;
		// create list to store swimmer booking
		Map<String, Integer> swimmerBookingData = new HashMap<>();
		// get swimmer booking details
		List<BookingData> swimmerBookings = bookingData.swimmerBookingData(userId, month);
		Iterator<BookingData> iterator = swimmerBookings.iterator();
		while (iterator.hasNext()) {
			BookingData booking = iterator.next();
			if (booking.getStatus().equalsIgnoreCase("Booked")) {
				swimmerTotalBooking++;
			} else if (booking.getStatus().equalsIgnoreCase("Attended")) {
				swimmerTotalAttend++;
			} else if (booking.getStatus().equalsIgnoreCase("Cancelled")) {
				swimmerTotalCancel++;
			}
		}
		// store swimmer booking data in map list
		swimmerBookingData.put("totalBooking", swimmerTotalBooking);
		swimmerBookingData.put("totalAttend", swimmerTotalAttend);
		swimmerBookingData.put("totalCancel", swimmerTotalCancel);
		return swimmerBookingData;
	}

	public String validateMonthName(String inputMonthName) {
		String inputLowerCase = inputMonthName.trim().toLowerCase();
		// Iterate through Month
		for (Month month : Month.values()) {
			if (month.toString().equalsIgnoreCase(inputLowerCase)
					|| month.name().substring(0, 3).equalsIgnoreCase(inputLowerCase)) {
				return month.toString();
			}
		}
		return null;
	}

}
