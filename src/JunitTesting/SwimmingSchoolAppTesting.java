
package JunitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import school.AppManager;
import school.BookingData;
import school.LessonSchedule;
import school.ReviewData;

class SwimmingSchoolAppTesting {
	private static AppManager appManager;
	private static BookingData bookingData;
	private static LessonSchedule lessonSchedule;
	private static String bookedLessonNo;
	private static ReviewData reviewData;

	@BeforeAll
	public static void setUp() {
		appManager = new AppManager();
		bookingData = appManager.getBookingData();
		bookingData.manageObject(appManager);
		lessonSchedule = appManager.getLessonSchedule();
		reviewData = appManager.getReviewData();
		reviewData.manageObject(appManager);
	}

	// test get day wise lesson timetable
	@Test
	public void testDayWiseTimetable() {
		String day = "Friday";
		System.out.println("\n\nTesting 1 : get timetable of friday");
		List<LessonSchedule> fridayTimetable = appManager.dayWiseTimetable(day);
		assertNotNull(fridayTimetable);
		// print timetable
		lessonSchedule.lessonDetails(fridayTimetable);
	}

	// test swimmer booked lesson
	@Test
	public void testSwimmerBookedLesson() {
		System.out.println("\n\nTesting 2: Swimmer books a lesson");
		String userId = "JS@123";
		bookedLessonNo = "LN39";
		String bookingId = "BID_1";
		int expectedSeat = 3;
		String expectedStatus = "Booked";
		appManager.setUserId(userId);
		lessonSchedule.setInputValue(new Scanner("2"));
		bookingData.setInputValue(new Scanner(bookedLessonNo));
		boolean bookingResult = bookingData.swimmerBookedLesson();
		int lessonSeatAvailability = lessonSchedule.getLessonDataByLessonNo(bookedLessonNo).getSeatAvailability();
		String resultStatus = bookingData.getBookingDataById(bookingId).getStatus();
		assertTrue(bookingResult, "Booking should be successful");
		assertEquals(expectedSeat, lessonSeatAvailability, "Lesson seat availability should be updated");
		assertEquals(expectedStatus, resultStatus);
	}

	// test change lesson by swimmer
	@Test
	public void testChangeLessonBySwimmer() {
		System.out.println("\n\nTesting 3: Swimmer changes lesson");
		String latestLessonNo = "LN31";
		String bookingId = "BID_1";
		lessonSchedule.setInputValue(new Scanner("2"));
		bookingData.setInputValue(new Scanner(bookingId + "\n" + latestLessonNo));
		boolean changeResult = bookingData.changeLessonBySwimmer();
		String updatedLessonNo = bookingData.getBookingDataById(bookingId).getLessonNo();
		assertTrue(changeResult, "Swimmer should successfully change the lesson");
		assertEquals(latestLessonNo, updatedLessonNo);
	}

	// test swimmer attend lesson review
	@Test
	public void testSwimmerAttendLessonReview() {
		System.out.println("\n\nTesting 4 : Swimmer review after lesson");
		String bookingId = "BID_1";
		String starRating = "5";
		String swimmerReview = "Excellent Coaching Experience";
		reviewData.setInputValue(new Scanner(swimmerReview + "\n" + starRating));
		boolean saveRating = reviewData.swimmerAttendLessonReview(bookingId);
		assertTrue(saveRating);
	}

	// test cancel lesson by swimmer
	@Test
	public void testCancelledLessonBySwimmer() {
		System.out.println("\n\nTesting 5 : Swimmer cancel lesson");
		String bookingId = "BID_1";
		String expectedStatus = "Cancelled";
		int expectedSeat = 4;
		bookingData.setInputValue(new Scanner(bookingId));
		boolean cancelResult = bookingData.cancelledLessonBySwimmer();
		int lessonSeatAvailability = lessonSchedule.getLessonDataByLessonNo(bookedLessonNo).getSeatAvailability();
		String resultStatus = bookingData.getBookingDataById(bookingId).getStatus();
		assertTrue(cancelResult, "Swimmer should successfully cancel the lesson");
		assertEquals(expectedSeat, lessonSeatAvailability, "Lesson seat availability should be updated");
		assertEquals(expectedStatus, resultStatus);
	}

	// test attend lesson by swimmer
	@Test
	public void testAttendLessonBySwimmer() {
		System.out.println("\n\nTesting 6 : Swimmer attend lesson");
		String bookingId = "BID_1";
		String expectedStatus = "Cancelled";
		int expectedSeat = 4;
		bookingData.setInputValue(new Scanner(bookingId));
		boolean attendResult = bookingData.attendLessonBySwimmer();
		int lessonSeatAvailability = lessonSchedule.getLessonDataByLessonNo(bookedLessonNo).getSeatAvailability();
		String resultStatus = bookingData.getBookingDataById(bookingId).getStatus();
		assertFalse(attendResult, "Swimmer should successfully attend the lesson");
		assertEquals(expectedSeat, lessonSeatAvailability, "Lesson seat availability should be updated");
		assertEquals(expectedStatus, resultStatus);
	}

}
