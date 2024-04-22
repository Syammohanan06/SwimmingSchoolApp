package school;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BookingData {
	private CoachData coachData;
	private SwimmerData swimmerData;
	private AppManager appManager;
	private ReviewData reviewData;
	private Scanner inputValue;
	private LessonSchedule lessonSchedule;
	private String bookingId;
	private String swimmerUserId;
	private String coachUserId;
	private String lessonNo;
	private String status;
	private List<BookingData> allBookings;

	public String getBookingId() {
		return bookingId;
	}

	public String getSwimmerUserId() {
		return swimmerUserId;
	}

	public String getCoachUserId() {
		return coachUserId;
	}
	
	public void setInputValue(Scanner inputValue) {
		this.inputValue=inputValue;
	}

	public void setCoachUserId(String coachUserId) {
		this.coachUserId = coachUserId;
	}

	public String getLessonNo() {
		return lessonNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setLessonNo(String lessonNo) {
		this.lessonNo = lessonNo;
	}

	public List<BookingData> getAllBookings() {
		return allBookings;
	}

	public BookingData(String lessonNo, String userId, String bookingId, String swimmerUserId, String coachUserId) {
		this.bookingId = bookingId;
		this.swimmerUserId = userId;
		this.coachUserId = coachUserId;
		this.lessonNo = lessonNo;
		this.status = "Booked";
	}

	public BookingData() {
		this.coachData = new CoachData();
		this.allBookings = new ArrayList<>();
		inputValue = new Scanner(System.in);
	}

	// initialized object
	public void manageObject(AppManager appManager) {
		this.appManager = appManager;
		appManager.getReportData();
		this.reviewData = appManager.getReviewData();
		this.lessonSchedule = appManager.getLessonSchedule();
		this.swimmerData = appManager.getSwimmerData();
	}

	public boolean swimmerBookedLesson() {
		String lessonNo;
		String option = lessonSchedule.selectLesson("Book");
		if (option == null) {
			System.out.println("\n________________________________");
			System.out.println("Booking lesson");
			System.out.println("________________________________");
			System.out.print("Enter lesson number to booked : ");
			lessonNo = inputValue.nextLine().trim();
			if (lessonNo.isEmpty() || !lessonSchedule.validLessonNo(lessonNo)) {
				System.out.println("Sorry : lesson number does not exist in lessonData");
				return false;
			} else {
				// validation for duplicate booking
				if (hasDuplicateBooking(lessonNo)) {
					System.out.println("Error: You have already booked this lesson.");
				} else {
					// validate lesson level is suitable for swimmer
					if (!hasLessonSwimmerLevel(lessonNo)) {
						System.out.println("Error : This lesson is not for your level");
					} else {
						// validate lesson has available capacity to book lesson
						if (!hasAvailableSeat(lessonNo)) {
							System.out.println("Wrong : This lesson does not have available capacity");
						} else {
							String bookingId = "BID_" + (this.allBookings.size() + 1);
							String coachUserId = lessonSchedule.getLessonDataByLessonNo(lessonNo).getCoachUserId();
							String userId = appManager.getUserId();
							allBookings.add(new BookingData(lessonNo, userId, bookingId, swimmerUserId, coachUserId));
							// update lesson seat availability after book lesson
							int currentSeatAvailability = lessonSchedule.getLessonDataByLessonNo(lessonNo)
									.getSeatAvailability();
							// update seat
							lessonSchedule.getLessonDataByLessonNo(lessonNo)
									.setSeatAvailability(currentSeatAvailability - 1);
							return true;
						}
					}
				}

			}
		}
		return true;
	}

	// change lesson by swimmer
	public boolean changeLessonBySwimmer() {
		String bookingId;
		String lessonNo;
		String status = "Changed";
		// get userId
		String userId = appManager.getUserId();
		// show booking details to select
		swimmerBookings(swimmerBookingData(userId, ""));
		// cancel lesson swimmer booking data not empty
		if (!swimmerBookingData(userId, "").isEmpty()) {
			System.out.print("Enter booking id to change lesson : ");
			bookingId = inputValue.nextLine().trim();
			if (bookingId.isEmpty() || bookingId.matches("\\d+")) {
				System.out.println("Error : This booking id does not exist");
			} else {
				// Check swimmer enter correct booking number
				if (!isSwimmerBookedLesson(bookingId)) {
					System.out.println("Error : This booking id does not exist");
				} else {
					// check selected booking number not attended or cancel
					String booking_status = getBookingDataById(bookingId).getStatus();
					if (booking_status.equalsIgnoreCase("Attended")) {
						System.out.println("Error : This booking id  has been already attended by you ");
					} else if (booking_status.equalsIgnoreCase("Cancelled")) {
						System.out.println("Error : This booking id  has been already cancelled by you ");
					} else {
						// show selected booking details
						System.out.println("\nSelect booking details to change");
						List<BookingData> selectBooking = new ArrayList<>();
						selectBooking.add(getBookingDataById(bookingId));
						swimmerBookings(selectBooking);
						// changed process
						String option = lessonSchedule.selectLesson("Changed");
						if (option == null) {
							System.out.println("\n________________________________");
							System.out.println("Change booked lesson");
							System.out.println("________________________________");
							System.out.print("Enter lesson number to changed : ");
							lessonNo = inputValue.nextLine().trim();
							if (lessonNo.isEmpty() || !lessonSchedule.validLessonNo(lessonNo)) {
								System.out.println("Sorry : lesson number does not exist in lessonData");
								return false;
							} else {
								// validation for duplicate booking
								if (hasDuplicateBooking(lessonNo)) {
									System.out.println("Error: You have already booked this lesson.");
								} else {
									// validate lesson level is suitable for swimmer
									if (!hasLessonSwimmerLevel(lessonNo)) {
										System.out.println("Error : This lesson is not for your level");
									} else {
										// validate lesson has available capacity to book lesson
										if (!hasAvailableSeat(lessonNo)) {
											System.out.println("Wrong : This lesson does not have available capacity");
										} else {
											// update lesson
											lessonNo.toUpperCase();
											// get booking object to update
											BookingData bookingObj = getBookingDataById(bookingId);
											// update status
											bookingObj.setStatus(status);
											// get last lesson number to update seat availability
											String lastLessonNo = bookingObj.getLessonNo();
											// update latest selected lesson in booking
											bookingObj.setLessonNo(lessonNo);
											// get coach userId from latest lesson
											String coachUserId = lessonSchedule.getLessonDataByLessonNo(lessonNo)
													.getCoachUserId();
											// update coach userid
											bookingObj.setCoachUserId(coachUserId);
											// update last lesson seat availability
											int currentSeatAvailability = lessonSchedule
													.getLessonDataByLessonNo(lastLessonNo).getSeatAvailability();
											// update seat
											lessonSchedule.getLessonDataByLessonNo(lastLessonNo)
													.setSeatAvailability(currentSeatAvailability + 1);
											// update latest lesson seat availability
											int latestLessonSeatAvailability = lessonSchedule
													.getLessonDataByLessonNo(lessonNo).getSeatAvailability();
											lessonSchedule.getLessonDataByLessonNo(lessonNo)
													.setSeatAvailability(latestLessonSeatAvailability - 1);
											return true;
										}
									}
								}

							}
						}
					}
				}
			}
		}
		return false;

	}

	// get booking data booking data by booking id
	public BookingData getBookingDataById(String bookingId) {
		Iterator<BookingData> iterator = allBookings.iterator();
		while (iterator.hasNext()) {
			BookingData booking = iterator.next();
			if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
				return booking;
			}
		}
		return null;
	}

	// cancelled lesson by swimmer
	public boolean cancelledLessonBySwimmer() {
		String bookingId;
		String status = "Cancelled";
		// get userId
		String userId = appManager.getUserId();
		// show booking details to select
		swimmerBookings(swimmerBookingData(userId, ""));
		// cancel lesson swimmer booking data not empty
		if (!swimmerBookingData(userId, "").isEmpty()) {
			System.out.print("Enter booking id to cancel lesson : ");
			bookingId = inputValue.nextLine().trim();
			if (bookingId.isEmpty() || bookingId.matches("\\d+")) {
				System.out.println("Error : This booking id does not exist");
			} else {
				// Check swimmer enter correct booking number
				if (!isSwimmerBookedLesson(bookingId)) {
					System.out.println("Error : This booking id does not exist");
				} else {
					// check selected booking number not attended or cancel
					String booking_status = getBookingDataById(bookingId).getStatus();
					if (booking_status.equalsIgnoreCase("Attended")) {
						System.out.println("Error : This booking id  has been already attended by you ");
					} else if (booking_status.equalsIgnoreCase("Cancelled")) {
						System.out.println("Error : This booking id  has been already cancelled by you ");
					} else {
						// show selected booking details
						System.out.println("Select booking details to cancel");
						List<BookingData> selectBooking = new ArrayList<>();
						selectBooking.add(getBookingDataById(bookingId));
						swimmerBookings(selectBooking);
						// cancel process
						// Update booking status
						getBookingDataById(bookingId).setStatus(status);
						// update lesson seat availability
						String lessonNo = getBookingDataById(bookingId).getLessonNo();
						int currentSeatAvailability = lessonSchedule.getLessonDataByLessonNo(lessonNo)
								.getSeatAvailability();
						// update seat
						lessonSchedule.getLessonDataByLessonNo(lessonNo).setSeatAvailability(currentSeatAvailability + 1);
						return true;
					}
				}
			}

		}
		return false;
	}

	// attend lesson by swimmer
	public boolean attendLessonBySwimmer() {
		String bookingId;
		String status = "Attended";
		// get userId
		String userId = appManager.getUserId();
		// show booking details to select
		swimmerBookings(swimmerBookingData(userId, ""));
		// cancel lesson swimmer booking data not empty
		if (!swimmerBookingData(userId, "").isEmpty()) {
			System.out.print("Enter booking id to attend lesson : ");
			bookingId = inputValue.nextLine().trim();
			if (bookingId.isEmpty() || bookingId.matches("\\d+")) {
				System.out.println("Error : This booking id does not exist");
			} else {
				// Check swimmer enter correct booking number
				if (!isSwimmerBookedLesson(bookingId)) {
					System.out.println("Error : This booking id does not exist");
				} else {
					// check selected booking number not attended or cancel
					String booking_status = getBookingDataById(bookingId).getStatus();
					if (booking_status.equalsIgnoreCase("Attended")) {
						System.out.println("Error : This booking id  has been already attended by you ");
					} else if (booking_status.equalsIgnoreCase("Cancelled")) {
						System.out.println("Error : This booking id  has been already cancelled by you ");
					} else {
						// show selected booking details
						System.out.println("\nSelect booking details to attend");
						List<BookingData> selectBooking = new ArrayList<>();
						selectBooking.add(getBookingDataById(bookingId));
						swimmerBookings(selectBooking);
						// attend process
						// get feedback from swimmer after attended lesson
						boolean storeFeedback = reviewData.swimmerAttendLessonReview(bookingId);
						if (storeFeedback) {
							// Update booking status
							getBookingDataById(bookingId).setStatus(status);
							// update lesson seat availability
							String lessonNo = getBookingDataById(bookingId).getLessonNo();
							int currentSeatAvailability = lessonSchedule.getLessonDataByLessonNo(lessonNo)
									.getSeatAvailability();
							// update seat
							lessonSchedule.getLessonDataByLessonNo(lessonNo)
									.setSeatAvailability(currentSeatAvailability + 1);
							// update swimmer level if he attend higher level lesson
							// get swimmer level
							int swimmerLevel = swimmerData.getSwimmerDetailsById(userId).getSwimmerLevel();
							// get lesson level
							int lessonLevel = lessonSchedule.getLessonDataByLessonNo(lessonNo).getLessonLevel();
							// update lesson level
							if (lessonLevel > swimmerLevel) {
								swimmerData.getSwimmerDetailsById(userId).setSwimmerLevel(lessonLevel);
							}
							// for testing
							System.out.println("Swimmer current Level : "
									+ swimmerData.getSwimmerDetailsById(userId).getSwimmerLevel());
							return true;
						}
					}
				}
			}

		}
		return false;
	}

	// show swimmer booking details
	public void swimmerBookings(List<BookingData> swimmerBookings) {
		if (!swimmerBookings.isEmpty()) {
			System.out.println("\n________________________________________________________________________________"
					+ "____________________________________________________________________________________________________________________");
			System.out.printf("| %-12s %-15s  %-17s  %-12s  %-15s  %-12s %-12s  %-16s  %-15s  %-15s  %-15s  %-15s |\n",
					"Booking Id", "Lesson No", "Lesson Title", "Lesson Level", "Lesson Day", "Start Time", "End Time",
					"Lesson Date", "Status", "Swimmer Name", "Swimmer Level", "Coach");

			System.out.println("________________________________________________________________________________"
					+ "____________________________________________________________________________________________________________________");
			// Print booking details
			for (BookingData data : swimmerBookings) {
				String lessonNo = data.getLessonNo();
				// Get LessonTimetable object by lesson id
				LessonSchedule lessonData = lessonSchedule.getLessonDataByLessonNo(lessonNo);
				System.out.printf(
						"| %-12s %-15s  %-17s  %-12s  %-15s  %-12s %-12s  %-16s  %-15s  %-15s  %-15s  %-15s |\n",
						data.getBookingId(), lessonNo, lessonData.getLessonTitle(),
						"Level " + lessonData.getLessonLevel(), lessonData.getLessonDay(),
						lessonData.getLessonStartTime(), lessonData.getLessonEndTime(), lessonData.getLessonDate(),
						data.getStatus(), swimmerData.getSwimmerDetailsById(data.getSwimmerUserId()).getSwimmerName(),
						"Level " + swimmerData.getSwimmerDetailsById(data.getSwimmerUserId()).getSwimmerLevel(),
						coachData.getCoachDetailsById(data.getCoachUserId()).getCoachName());
			}
			System.out.println("________________________________________________________________________________"
					+ "____________________________________________________________________________________________________________________");
		}
	}

	// get swimmer booking data by userId
	public List<BookingData> swimmerBookingData(String userId, String month) {
		// create list to store swimmer booking data
		List<BookingData> swimmerBookings = new ArrayList<>();
		Iterator<BookingData> iterator = allBookings.iterator();
		while (iterator.hasNext()) {
			BookingData bData = iterator.next();
			// get lesson number from bookings
			String lessonNo = bData.getLessonNo();
			// get lessonObject
			LessonSchedule lessonObj = lessonSchedule.getLessonDataByLessonNo(lessonNo);
			// get lesson Month
			String lessonMonth = lessonObj.getLessonDate().substring(3, 6);
			// validation of swimmer userId and month
			if ((bData.getSwimmerUserId().equalsIgnoreCase(userId) && lessonMonth.equalsIgnoreCase(month))
					|| (bData.getSwimmerUserId().equalsIgnoreCase(userId) && month.isEmpty())) {
				swimmerBookings.add(bData);
			}
		}
		return swimmerBookings;

	}

	// validation of duplicate booking
	public boolean hasDuplicateBooking(String lessonNo) {
		String userId = appManager.getUserId();
		for (BookingData bData : allBookings) {
			if (bData.getLessonNo().equalsIgnoreCase(lessonNo) && bData.getSwimmerUserId().equalsIgnoreCase(userId)) {
				if (bData.getStatus().equalsIgnoreCase("Booked") || bData.getStatus().equalsIgnoreCase("Changed")) {
					return true;
				}
			}
		}
		return false;

	}

	// validation of has available capacity
	public boolean hasAvailableSeat(String lessonNo) {
		List<LessonSchedule> allLessons = lessonSchedule.getAllLessons();
		int lessonSeat;
		for (LessonSchedule lData : allLessons) {
			if (lData.getLessonNo().equalsIgnoreCase(lessonNo)) {
				lessonSeat = lData.getSeatAvailability();
				if (lessonSeat >= 1 && lessonSeat <= 4) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasLessonSwimmerLevel(String lessonNo) {
		// get userId from app manager
		String userId = appManager.getUserId();
		// get lesson level
		int lessonLevel = lessonSchedule.getLessonDataByLessonNo(lessonNo).getLessonLevel();
		// get swimmer Grade
		int swimmerLevel = swimmerData.getSwimmerDetailsById(userId).getSwimmerLevel();
		if (lessonLevel == swimmerLevel || lessonLevel == (swimmerLevel + 1)) {
			return true;
		}
		return false;
	}

	public boolean isSwimmerBookedLesson(String bookingId) {
		String userId = appManager.getUserId();
		for (BookingData bData : allBookings) {
			if (bData.getSwimmerUserId().equalsIgnoreCase(userId) && bData.getBookingId().equalsIgnoreCase(bookingId)) {
				return true;
			}
		}

		return false;
	}

}
