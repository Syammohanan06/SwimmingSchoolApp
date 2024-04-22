package school;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LessonSchedule {
	private AppManager appManager;
	private CoachData coachData;
	private Scanner inputValue;
	private String lessonNo;
	private String lessonTitle;
	private String lessonStartTime;
	private String lessonEndTime;
	private String lessonDate;
	private String lessonDay;
	private String coachUserId;
	private int lessonLevel;
	private int seatAvailability;

	public String getLessonNo() {
		return lessonNo;
	}

	public String getLessonTitle() {
		return lessonTitle;
	}

	public String getLessonStartTime() {
		return lessonStartTime;
	}
	
	public void setInputValue(Scanner inputValue) {
		this.inputValue=inputValue;
	}

	public String getLessonEndTime() {
		return lessonEndTime;
	}

	public String getLessonDate() {
		return lessonDate;
	}

	public String getLessonDay() {
		return lessonDay;
	}

	public String getCoachUserId() {
		return coachUserId;
	}

	public int getLessonLevel() {
		return lessonLevel;
	}

	public int getSeatAvailability() {
		return seatAvailability;
	}
	
	public void setSeatAvailability(int seatAvailability) {
		this.seatAvailability=seatAvailability;
	}

	public List<LessonSchedule> getAllLessons() {
		return allLessons;
	}

	private List<LessonSchedule> allLessons;

	public LessonSchedule(String lessonNo, String lessonDay, String lessonStartTime, String lessonEndTime,
			String lessonDate, String lessonTitle, String coachUserId, int lessonLevel, int seatAvailability) {
		this.lessonNo = lessonNo;
		this.lessonDay = lessonDay;
		this.lessonStartTime = lessonStartTime;
		this.lessonEndTime = lessonEndTime;
		this.lessonDate = lessonDate;
		this.lessonTitle = lessonTitle;
		this.coachUserId = coachUserId;
		this.lessonLevel = lessonLevel;
		this.seatAvailability = seatAvailability;
	}

	// manage object
	public void manageObject(AppManager appManager) {
		this.appManager = appManager;

	}

	// select lesson for book or change
	public String selectLesson(String action) {
		String userAction = null;
		System.out.println("\n________________________________");
		System.out.println("Swimming Timetable");
		System.out.println("________________________________");
		// show swimming timtable
		lessonDetails(allLessons);
		do {
			System.out.println("\n1. View timetable to another way");
			System.out.println("2. Select lesson to " + action);
			System.out.println("3. Go to previous menu");
			System.out.print("Please select an action : ");
			userAction = inputValue.nextLine().trim();
			// call method by selectMenu by
			switch (userAction) {
			case "1" -> {
				lessonTimetable();
			}
			case "2" -> {
				return null;
			}
			case "3" -> {
				return "Back";
			}
			default -> System.out.println("Invalid selection. Please try again.");
			}

		} while (!userAction.equals("3"));
		return null;

	}

	// lesson timetable
	public void lessonTimetable() {
		String viewType;
		System.out.println("\n1. Day wise timetable");
		System.out.println("2. Level wise timetable");
		System.out.println("3. Coach wise timetable");
		System.out.println("4. All timetable");
		System.out.println("5. Go back to previous menu");
		System.out.print("Select an option to view timetable: ");
		viewType = inputValue.nextLine().trim();
		switch (viewType) {
		case "1" -> {
			System.out.print("Enter day (Monday,Wednesday,Friday,Saturday) to view timetable : ");
			String day = inputValue.nextLine().trim();
			if (day.equalsIgnoreCase("Monday") || day.equalsIgnoreCase("Mon")) {
				// show timetable of monday
				lessonDetails(appManager.dayWiseTimetable("Monday"));
			} else if (day.equalsIgnoreCase("Wednesday") || day.equalsIgnoreCase("Wed")) {
				// show timetable of wednesday
				lessonDetails(appManager.dayWiseTimetable("Wednesday"));
			} else if (day.equalsIgnoreCase("Friday") || day.equalsIgnoreCase("Fri")) {
				// show timetable of friday
				lessonDetails(appManager.dayWiseTimetable("Friday"));
			} else if (day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Sat")) {
				// show timetable of saturday
				lessonDetails(appManager.dayWiseTimetable("Saturday"));
			} else {
				System.out.println("Error : Invlalid day.Please enter valid day to view timetable");
			}
		}

		case "2" -> {
			System.out.print("Enter level (1 to 5) to view timetable : ");
			String level = inputValue.nextLine().trim();
			if (level.isEmpty() || !level.matches("[1-5]")) {
				System.out.println("Error : Invalid level");
			} else {
				// show timetable according to level
				lessonDetails(appManager.levelWiseTimetable(Integer.parseInt(level)));
			}

		}

		case "3" -> {
			// show coach details
			coachData.showCoachDetails();
			System.out.print("Enter coach user id to view timeable : ");
			String userId = inputValue.nextLine().trim();
			if (userId.isEmpty() || !coachData.isValidCoachUserId(userId)) {
				System.out.println("Sorry : userId does not exist");
			} else {
				// show timetable according to coach
				lessonDetails(appManager.coachWiseTimetable(userId));
			}

		}

		case "4" -> {
			// show all timetable
			lessonDetails(allLessons);
		}

		case "5" -> {
			System.out.println("Going back to the previous menu.");
			break;
		}

		default -> {
			System.out.println("Invalid selection. Please try again.");
			break;
		}

		}
	}

	public LessonSchedule() {
		this.inputValue = new Scanner(System.in);
		allLessons = new ArrayList<>();
		storeDefaultLessonData();
		this.coachData = new CoachData();
	}

	// store default lessonData
	public void storeDefaultLessonData() {
		allLessons.add(new LessonSchedule("LN01", "Monday", "4:00pm", "5:00pm", "08-Apr-2024", "Treading Water",
				"EW@123", 1, 4));
		allLessons.add(new LessonSchedule("LN02", "Monday", "5:00pm", "6:00pm", "08-Apr-2024", "Treading Water",
				"DJ@123", 1, 4));
		allLessons.add(new LessonSchedule("LN03", "Monday", "6:00pm", "7:00pm", "08-Apr-2024", "Treading Water",
				"SB@123", 5, 4));
		allLessons.add(new LessonSchedule("LN04", "Wednesday", "4:00pm", "5:00pm", "10-Apr-2024", "Kicking Techniques",
				"EW@123", 3, 4));
		allLessons.add(new LessonSchedule("LN05", "Wednesday", "5:00pm", "6:00pm", "10-Apr-2024", "Kicking Techniques",
				"DJ@123", 4, 4));
		allLessons.add(new LessonSchedule("LN06", "Wednesday", "6:00pm", "7:00pm", "10-Apr-2024", "Kicking Techniques",
				"RA@123", 3, 4));
		allLessons.add(
				new LessonSchedule("LN07", "Friday", "4:00pm", "5:00pm", "12-Apr-2024", "Arm Movements", "SB@123", 2, 4));
		allLessons.add(
				new LessonSchedule("LN08", "Friday", "5:00pm", "6:00pm", "12-Apr-2024", "Arm Movements", "DJ@123", 3, 4));
		allLessons.add(
				new LessonSchedule("LN09", "Friday", "6:00pm", "7:00pm", "12-Apr-2024", "Arm Movements", "DJ@123", 2, 4));
		allLessons.add(new LessonSchedule("LN10", "Saturday", "2:00pm", "3:00pm", "13-Apr-2024", "Water Polo", "EW@123",
				4, 4));
		allLessons.add(new LessonSchedule("LN11", "Saturday", "3:00pm", "4:00pm", "13-Apr-2024", "Water Polo", "SB@123",
				4, 4));
		allLessons.add(new LessonSchedule("LN12", "Monday", "4:00pm", "5:00pm", "15-Apr-2024", "Treading Water",
				"DJ@123", 1, 4));
		allLessons.add(new LessonSchedule("LN13", "Monday", "5:00pm", "6:00pm", "15-Apr-2024", "Treading Water",
				"EW@123", 4, 4));
		allLessons.add(new LessonSchedule("LN14", "Monday", "6:00pm", "7:00pm", "15-Apr-2024", "Treading Water",
				"SB@123", 1, 4));
		allLessons.add(new LessonSchedule("LN15", "Wednesday", "4:00pm", "5:00pm", "17-Apr-2024", "Kicking Techniques",
				"DJ@123", 1, 4));
		allLessons.add(new LessonSchedule("LN16", "Wednesday", "5:00pm", "6:00pm", "17-Apr-2024", "Kicking Techniques",
				"RA@123", 5, 4));
		allLessons.add(new LessonSchedule("LN17", "Wednesday", "6:00pm", "7:00pm", "17-Apr-2024", "Kicking Techniques",
				"RA@123", 5, 4));
		allLessons.add(
				new LessonSchedule("LN18", "Friday", "4:00pm", "5:00pm", "19-Apr-2024", "Arm Movements", "RA@123", 4, 4));
		allLessons.add(
				new LessonSchedule("LN19", "Friday", "5:00pm", "6:00pm", "19-Apr-2024", "Arm Movements", "EW@123", 5, 4));
		allLessons.add(
				new LessonSchedule("LN20", "Friday", "6:00pm", "7:00pm", "19-Apr-2024", "Arm Movements", "SB@123", 4, 4));
		allLessons.add(new LessonSchedule("LN21", "Saturday", "2:00pm", "3:00pm", "20-Apr-2024", "Water Polo", "EW@123",
				1, 4));
		allLessons.add(new LessonSchedule("LN22", "Saturday", "3:00pm", "4:00pm", "20-Apr-2024", "Water Polo", "SB@123",
				5, 4));
		allLessons.add(new LessonSchedule("LN23", "Monday", "4:00pm", "5:00pm", "22-Apr-2024", "Treading Water",
				"RA@123", 5, 4));
		allLessons.add(new LessonSchedule("LN24", "Monday", "5:00pm", "6:00pm", "22-Apr-2024", "Treading Water",
				"DJ@123", 2, 4));
		allLessons.add(new LessonSchedule("LN25", "Monday", "6:00pm", "7:00pm", "22-Apr-2024", "Treading Water",
				"DJ@123", 1, 4));
		allLessons.add(new LessonSchedule("LN26", "Wednesday", "4:00pm", "5:00pm", "24-Apr-2024", "Kicking Techniques",
				"LT@123", 3, 4));
		allLessons.add(new LessonSchedule("LN27", "Wednesday", "5:00pm", "6:00pm", "24-Apr-2024", "Kicking Techniques",
				"SB@123", 2, 4));
		allLessons.add(new LessonSchedule("LN28", "Wednesday", "6:00pm", "7:00pm", "24-Apr-2024", "Kicking Techniques",
				"DJ@123", 1, 4));
		allLessons.add(
				new LessonSchedule("LN29", "Friday", "4:00pm", "5:00pm", "26-Apr-2024", "Arm Movements", "LT@123", 1, 4));
		allLessons.add(
				new LessonSchedule("LN30", "Friday", "5:00pm", "6:00pm", "26-Apr-2024", "Arm Movements", "SB@123", 1, 4));
		allLessons.add(
				new LessonSchedule("LN31", "Friday", "6:00pm", "7:00pm", "26-Apr-2024", "Arm Movements", "LT@123", 5, 4));
		allLessons.add(new LessonSchedule("LN32", "Saturday", "2:00pm", "3:00pm", "27-Apr-2024", "Water Polo", "EW@123",
				3, 4));
		allLessons.add(new LessonSchedule("LN33", "Saturday", "3:00pm", "4:00pm", "27-Apr-2024", "Water Polo", "LT@123",
				4, 4));
		allLessons.add(new LessonSchedule("LN34", "Monday", "4:00pm", "5:00pm", "29-Apr-2024", "Treading Water",
				"RA@123", 2, 4));
		allLessons.add(new LessonSchedule("LN35", "Monday", "5:00pm", "6:00pm", "29-Apr-2024", "Treading Water",
				"RA@123", 2, 4));
		allLessons.add(new LessonSchedule("LN36", "Monday", "6:00pm", "7:00pm", "29-Apr-2024", "Treading Water",
				"LT@123", 3, 4));
		allLessons.add(new LessonSchedule("LN37", "Wednesday", "4:00pm", "5:00pm", "01-May-2024", "Kicking Techniques",
				"EW@123", 4, 4));
		allLessons.add(new LessonSchedule("LN38", "Wednesday", "5:00pm", "6:00pm", "01-May-2024", "Kicking Techniques",
				"LT@123", 4, 4));
		allLessons.add(new LessonSchedule("LN39", "Wednesday", "6:00pm", "7:00pm", "01-May-2024", "Kicking Techniques",
				"LT@123", 5, 4));
		allLessons.add(
				new LessonSchedule("LN40", "Friday", "4:00pm", "5:00pm", "03-May-2024", "Arm Movements", "RA@123", 2, 4));
		allLessons.add(
				new LessonSchedule("LN41", "Friday", "5:00pm", "6:00pm", "03-May-2024", "Arm Movements", "LT@123", 3, 4));
		allLessons.add(
				new LessonSchedule("LN42", "Friday", "6:00pm", "7:00pm", "03-May-2024", "Arm Movements", "LT@123", 1, 4));
		allLessons.add(new LessonSchedule("LN43", "Saturday", "2:00pm", "3:00pm", "04-May-2024", "Water Polo", "RA@123",
				2, 4));
		allLessons.add(new LessonSchedule("LN44", "Saturday", "3:00pm", "4:00pm", "04-May-2024", "Water Polo", "LT@123",
				3, 4));
	}

	// show lesson details
	public void lessonDetails(List<LessonSchedule> lessons) {
		// check lesson list is not empty
		if (!lessons.isEmpty()) {
			System.out.println(
					"_____________________________________________________________________________________________________________________________________________________________________________");
			System.out.printf("| %-10s  %-20s  %-6s  %-15s  %-12s  %-12s  %-15s  %-20s  %-12s  %-12s  %-15s |\n",
					"Lesson No", "Lesson Title", "Seat", "Lesson Day", "Start Time", "End Time", "Level",
					"Coach Name", "Coach DOB", "Gender", "Date");
			System.out.println(
					"_____________________________________________________________________________________________________________________________________________________________________________");
			for (LessonSchedule data : lessons) {
				// get lesson details
				// print timetable details in table
				System.out.printf("| %-10s  %-20s  %-6s  %-15s  %-12s  %-12s  %-15s  %-20s  %-12s  %-12s  %-15s |\n",
						data.getLessonNo(), data.getLessonTitle(), data.getSeatAvailability(), data.getLessonDay(),
						data.getLessonStartTime(), data.getLessonEndTime(), "Level " + data.getLessonLevel(),
						coachData.getCoachDetailsById(data.getCoachUserId()).getCoachName(),
						coachData.getCoachDetailsById(data.getCoachUserId()).getCoachDOB(),
						coachData.getCoachDetailsById(data.getCoachUserId()).getCoachGender(),
						data.getLessonDate());
			}

		}
		System.out.println(
				"_____________________________________________________________________________________________________________________________________________________________________________");
	}

	// get lesson data by lessonNo
	public LessonSchedule getLessonDataByLessonNo(String lessonNo) {
		for (LessonSchedule data : allLessons) {
			if (data.getLessonNo().equalsIgnoreCase(lessonNo)) {
				return data;
			}
		}
		return null;
	}

	// validation of lessonNo

	public boolean validLessonNo(String lessonNo) {
		for (LessonSchedule data : allLessons) {
			if (data.getLessonNo().equalsIgnoreCase(lessonNo)) {
				return true;
			}
		}
		return false;
	}

}