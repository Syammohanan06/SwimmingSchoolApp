package school;

import java.util.ArrayList;
import java.util.List;

public class AppManager {
	private String userId;
	private final String managerUserId = "MNG@123";
	private SwimmerData swimmerData;
	private CoachData coachData;
	private BookingData bookingData;
	private LessonSchedule lessonSchedule;
	private SchoolManager schoolManager;
	private ReportData reportData;
	private ReviewData reviewData;

	public String getUserId() {
		return userId;
	}

	public SwimmerData getSwimmerData() {
		return swimmerData;
	}

	public String getManagerUserId() {
		return managerUserId;
	}

	public CoachData getCoachData() {
		return coachData;
	}

	public BookingData getBookingData() {
		return bookingData;
	}

	public LessonSchedule getLessonSchedule() {
		return lessonSchedule;
	}

	public SchoolManager getSchoolManager() {
		return schoolManager;
	}

	public ReportData getReportData() {
		return reportData;
	}

	public ReviewData getReviewData() {
		return reviewData;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// app manager constructor
	public AppManager() {
		this.swimmerData = new SwimmerData();
		this.coachData = new CoachData();
		this.bookingData = new BookingData();
		this.lessonSchedule = new LessonSchedule();
		this.schoolManager = new SchoolManager();
		this.reportData = new ReportData();
		this.reviewData = new ReviewData();
	}

	// validation of swimmer userid
	public String correctSwimmerUserId(String swimmerUserId) {
		List<SwimmerData> swimmersData = swimmerData.getAllSwimmers();
		for (int id = 0; id < swimmersData.size(); id++) {
			if (swimmersData.get(id).getSwimmerUserId().equalsIgnoreCase(swimmerUserId)) {
				return swimmersData.get(id).getSwimmerUserId();
			}
		}
		return null;

	}

	// get lesson according to day
	public List<LessonSchedule> dayWiseTimetable(String day) {
		List<LessonSchedule> allLessons = lessonSchedule.getAllLessons();
		// create List to store day wise lesson
		List<LessonSchedule> daywiseLesson = new ArrayList<>();
		for (LessonSchedule data : allLessons) {
			if (data.getLessonDay().equalsIgnoreCase(day)) {
				daywiseLesson.add(data);
			}
		}
		return daywiseLesson;
	}

	// level wise timetable
	public List<LessonSchedule> levelWiseTimetable(int level) {
		List<LessonSchedule> allLessons = lessonSchedule.getAllLessons();
		// create List to store level wise lesson
		List<LessonSchedule> levelwiseLesson = new ArrayList<>();
		for (LessonSchedule data : allLessons) {
			if (data.getLessonLevel() == level) {
				levelwiseLesson.add(data);
			}
		}
		return levelwiseLesson;
	}

	// coach wise timetable
	public List<LessonSchedule> coachWiseTimetable(String coachUserId) {
		List<LessonSchedule> allLessons = lessonSchedule.getAllLessons();
		// create List to store coach wise lesson
		List<LessonSchedule> coachwiseLesson = new ArrayList<>();
		for (LessonSchedule data : allLessons) {
			if (data.getCoachUserId().equalsIgnoreCase(coachUserId)) {
				coachwiseLesson.add(data);
			}
		}
		return coachwiseLesson;
	}

}
