package school;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ReviewData {
	private AppManager appManager;
	private SwimmerData swimmerData;
	private BookingData bookingData;
	private Scanner inputValue;
	private String uniqueId;
	private String coachUserid;
	private String userId;
	private String lessonNO;
	private int starRating;
	private String swimmerReview;
	private List<ReviewData> allReviews;

	public String getUniqueId() {
		return uniqueId;
	}

	public String getCoachUserid() {
		return coachUserid;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setInputValue(Scanner inputValue) {
		this.inputValue=inputValue;
	}

	public String getLessonNO() {
		return lessonNO;
	}

	public int getStarRating() {
		return starRating;
	}

	public String getSwimmerReview() {
		return swimmerReview;
	}

	public List<ReviewData> getAllReviews() {
		return allReviews;
	}

	// create constructor to store swimmer review data
	public ReviewData(String uniqueId, String coachUserid, String userId, String lessonNO, int starRating,
			String swimmerReview) {
		this.uniqueId = uniqueId;
		this.coachUserid = coachUserid;
		this.userId = userId;
		this.lessonNO = lessonNO;
		this.starRating = starRating;
		this.swimmerReview = swimmerReview;
	}

	// create default constructor
	public ReviewData() {
		inputValue = new Scanner(System.in);
		allReviews = new ArrayList<>();
	}

	// initialized object
	public void manageObject(AppManager appManager) {
		this.appManager = appManager;
		this.bookingData = appManager.getBookingData();
		this.swimmerData = appManager.getSwimmerData();
	}

	// get swimmer review after attend lesson
	public boolean swimmerAttendLessonReview(String bookingId) {
		String rating = "";
		boolean storeFeedback = false;
		String swimmerReview = "";
		System.out.println("\n________________________________");
		System.out.println("Swimmer review of attended lesson");
		System.out.println("________________________________");
		System.out.println("\n1: Very dissatisfied\n2: Dissatisfied\n3: Ok\n4: Satisfied\n5: Very Satisfied\n");
		do {
			if (swimmerReview.isEmpty()) {
				System.out.print("Give review about attended lesson : ");
				swimmerReview = inputValue.nextLine().trim();
				if (swimmerReview.isEmpty()) {
					System.out.println("=> Please give correct review for this lesson ");
				}
			} else if (rating.isEmpty()) {
				System.out.print("Enter rating (1 to 5) about attended lesson : ");
				rating = inputValue.nextLine().trim();
				if (rating.isEmpty() || !rating.matches("[1-5]")) {
					System.out.println("Error : Please give correct rating (1 to 5) about attended lesson");
					rating = "";
				}
			} else {
				// create object
				uniqueId = "RID_" + (allReviews.size() + 1);
				String userId = appManager.getUserId();
				starRating = Integer.parseInt(rating);
				String lessonNo = bookingData.getBookingDataById(bookingId).getLessonNo();
				String coachUserId = new LessonSchedule().getLessonDataByLessonNo(lessonNo).getCoachUserId();
				allReviews.add(new ReviewData(uniqueId, coachUserId, userId, lessonNo, starRating, swimmerReview));
				storeFeedback = true;
			}

		} while (!storeFeedback);
		return storeFeedback;
	}

	// show review data
	public void printReviewData(List<ReviewData> reviews) {
		if (!reviews.isEmpty()) {
			System.out.println(
					"______________________________________________________________________________________________________________________________________________________________________");
			System.out.printf("|  %-10s  %-15s  %-17s  %-17s  %-15s  %-12s   %-12s  %-30s  %-15s  |\n", "Unique ID",
					"Lesson No", "Lesson", "Swimmer", "Coach", "Lesson Date", "Lesson Day", "Swimmer Review",
					"Star Rating");
			System.out.println(
					"______________________________________________________________________________________________________________________________________________________________________");

			Iterator<ReviewData> iterator = allReviews.iterator();
			while (iterator.hasNext()) {
				ReviewData review = iterator.next();
				// get lesson object to print lesson details
				LessonSchedule lessonObj = new LessonSchedule().getLessonDataByLessonNo(review.getLessonNO());
				System.out.printf("|  %-10s  %-15s  %-17s  %-17s  %-15s  %-12s   %-12s  %-30s  %-15s  |\n",
						review.getUniqueId(), review.getLessonNO(), lessonObj.getLessonTitle(),
						swimmerData.getSwimmerDetailsById(review.getUserId()).getSwimmerName(),
						new CoachData().getCoachDetailsById(review.getCoachUserid()).getCoachName(),
						lessonObj.getLessonDate(), lessonObj.getLessonDay(), review.getSwimmerReview(),
						review.getStarRating());
			}

			System.out.println(
					"______________________________________________________________________________________________________________________________________________________________________");
		} else {
			System.out.println("Sorry ! No reviews data available in the record");
		}
	}

	// get review data by userId
	public List<ReviewData> getCoachRatingData(String month, String coachUserId) {
		// create list to store swimmer review data
		List<ReviewData> coachReview = new ArrayList<>();
		Iterator<ReviewData> iterator = allReviews.iterator();
		while (iterator.hasNext()) {
			ReviewData review = iterator.next();
			if (review.getCoachUserid().equalsIgnoreCase(coachUserId)) {
				// get lesson number from review
				String lessonNo = review.getLessonNO();
				// get lessonMonth
				String lessonDate = new LessonSchedule().getLessonDataByLessonNo(lessonNo).getLessonDate();
				// get lesson month
				String lessonMonth = lessonDate.substring(3, 6);
				if (lessonMonth.equalsIgnoreCase(month) || month.isEmpty()) {
					coachReview.add(review);
				}
			}
		}
		return coachReview;
	}

	// get swimmer rating data
	public List<ReviewData> getSwimmerRatingData(String userId) {
		// create list to store swimmer review data
		List<ReviewData> swimmerReview = new ArrayList<>();
		Iterator<ReviewData> iterator = allReviews.iterator();
		while (iterator.hasNext()) {
			ReviewData review = iterator.next();
			if (review.getUserId().equalsIgnoreCase(userId)) {
				swimmerReview.add(review);
			}
		}
		return swimmerReview;
	}

}
