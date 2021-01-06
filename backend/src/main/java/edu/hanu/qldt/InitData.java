package edu.hanu.qldt;

import edu.hanu.qldt.dto.ExamDTO;
import edu.hanu.qldt.dto.response.*;
import edu.hanu.qldt.model.Course;
import edu.hanu.qldt.model.ExamType;
import edu.hanu.qldt.model.Room;
import edu.hanu.qldt.service.auth.impl.AuthorityServiceImpl;
import edu.hanu.qldt.service.auth.impl.UserServiceImpl;
import edu.hanu.qldt.service.impl.*;
import edu.hanu.qldt.dto.AttendanceDTO;

import edu.hanu.qldt.model.user.UserRoleName;
import edu.hanu.qldt.model.user.group.Student;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InitData {

    private final UserServiceImpl userService;

    private final TeacherServiceImpl teacherService;

    private final ClassroomServiceImpl classroomService;

    private final StudentServiceImp studentService;

    private final CourseServiceImpl courseService;

    private final TimeTableServiceImpl timeTableService;

    private final ExamServiceImpl examService;

    private final ReportServiceImpl reportService;

    private final AttendanceServiceImpl attendanceService;

    private final AuthorityServiceImpl authorityService;

    private final RoomServiceImpl roomService;

    @Autowired
    public InitData(
            UserServiceImpl userService,
            TeacherServiceImpl teacherService,
            ClassroomServiceImpl classroomService,
            StudentServiceImp studentService,
            CourseServiceImpl courseService,
            TimeTableServiceImpl timeTableService,
            ExamServiceImpl examService,
            ReportServiceImpl reportService,
            AttendanceServiceImpl attendanceService,
            AuthorityServiceImpl authorityService,
            RoomServiceImpl roomService
    ) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.classroomService = classroomService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.timeTableService = timeTableService;
        this.examService = examService;
        this.reportService = reportService;
        this.attendanceService = attendanceService;
        this.authorityService = authorityService;
        this.roomService = roomService;
    }

    public void Init() {
        String username = "admin";
        String password = "admin";
        if (userService.findAll().isEmpty()) {
            authorityService.save(UserRoleName.ROLE_ADMIN);
            authorityService.save(UserRoleName.ROLE_STUDENT);
            authorityService.save(UserRoleName.ROLE_TEACHER);
            authorityService.save(UserRoleName.ROLE_HEADTEACHER);
            userService.save(new UserResponseDTO(username, password, "admin", "ROLE_ADMIN"));
            Logger.info("Username: {0}\nPassword: {1}", username, password);
        }
        testData();
    }

    private void testData() {
        testDataTeacher();
        testDataClassroom();
        testDataStudent();
        testDataCourse();
        testDataRoom();
        testDataTimeTable();
        testDataAttendance();
        testDataExam();
        testDataReport();
    }

    private void testDataStudent() {
        List<String> usernames = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    "student" + i,
                    "student",
                    "Student" + i + "'s fullname",
                    "ROLE_STUDENT"
            );
            userService.save(userResponseDTO);
            usernames.add(userResponseDTO.getUsername());
        }

        for (String username : usernames) {
            Random random = new Random();
            int randYear = random.nextInt(2) + 2000;
            int randMonth = random.nextInt(12) + 1;
            int randDay = random.nextInt(28) + 1;
            int randHealthCare = random.nextInt(900) + 100;
            studentService.create(new StudentResponseDTO(
                    username,
                    LocalDate.of(randYear, randMonth, randDay),
                    2018,
                    username + "' address",
                    "FEMALE",
                    random.nextInt(1000) + 1801040000 + "",
                    randHealthCare + "/" + randHealthCare + "/" + randHealthCare,
                    "Father name",
                    "Mother name",
                    "+84 69 696 9696",
                    "+84 69 696 9696",
                    (long) (random.nextInt(2) + 1)
            ));
        }
    }

    private void testDataTeacher() {
        List<String> usernames = new ArrayList<>();

        String[] teacherNames = {"Đặng Ngân", "Lê Hương", "Ngọc Nhàn", "Trung Hiếu", "Thu Thủy"};

        for (int i = 1; i < 11; i++) {
            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    "teacher" + i,
                    "teacher",
                    teacherNames[i % teacherNames.length],
                    "ROLE_TEACHER"
            );
            userService.save(userResponseDTO);
            usernames.add(userResponseDTO.getUsername());
        }

        for (String username : usernames) {
            teacherService.create(new TeacherResponseDTO(
                    username,
                    username + "@gmail.com",
                    "+80 90 858 2084"
            ));
        }
    }

    private void testDataClassroom() {
        classroomService.create(new ClassroomResponseDTO(
                2018,
                2022,
                10,
                'A',
                2L
        ));
        classroomService.create(new ClassroomResponseDTO(
                2018,
                2022,
                12,
                'B',
                3L
        ));
    }

    private void testDataCourse() {
        courseService.create(new CourseResponseDTO(
                "Mathematics",
                10,
                1L
        ));
        courseService.create(new CourseResponseDTO(
                "Literature",
                11,
                2L
        ));
        courseService.create(new CourseResponseDTO(
                "English",
                12,
                3L
        ));
        courseService.create(new CourseResponseDTO(
                "Calculus",
                10,
                4L
        ));
        courseService.create(new CourseResponseDTO(
                "History",
                11,
                5L
        ));
        courseService.create(new CourseResponseDTO(
                "Geography",
                12,
                6L
        ));
        courseService.create(new CourseResponseDTO(
                "Physics",
                10,
                7L
        ));
        courseService.create(new CourseResponseDTO(
                "Chemistry",
                11,
                7L
        ));
        courseService.create(new CourseResponseDTO(
                "Civic Education",
                12,
                8L
        ));
        courseService.create(new CourseResponseDTO(
                "Biology",
                10,
                9L
        ));
        courseService.create(new CourseResponseDTO(
                "Informatics",
                11,
                10L
        ));

        for (Long i = 1L; i < 3L; i++)
            for (long j = 1L; j < 8L; j++)
                classroomService.setCourse(i, j);
    }

    private void testDataExam() {
        List<Course> courses = courseService.findAll();
        List<ExamType> examTypes = examService.getAllExamType();
        Random random = new Random();

        for (int i = 0; i < 40; i++) {
            int randYear = random.nextInt(2) + 2018;
            int randMonth = randYear == 2018 ? random.nextInt(4) + 9
                    : random.nextInt(6) + 1;
            int randDay = random.nextInt(22) + 1;
            Long classroom_id = i % 2 == 0 ? 1L : 2L;
            Course course = courses.get(random.nextInt(courses.size()));
            List<ExamDTO> examDTOS = examService.makeExamsFormToClassroom(
                    classroom_id,
                    LocalDate.of(randYear, randMonth, randDay), ""
            );
            List<ExamResponseDTO> examResponseDTOS = new ArrayList<>();

            for (ExamDTO examDTO : examDTOS) {
                int mark = random.nextInt(5) + 1;
                examResponseDTOS.add(new ExamResponseDTO(
                        mark,
                        examDTO.getWrittenAt(),
                        examTypes.get(random.nextInt(examTypes.size())).toString(),
                        course.getId(),
                        examDTO.getStudent().getId()
                ));
            }
            examService.createExamsFromForm(examResponseDTOS);
        }
    }

    private void testDataReport() {
        List<Course> courses = courseService.findAll();
        List<Student> students = studentService.findAll();
        Random random = new Random();

        for (Student student : students) {
            for (Course course : courses) {
                int mark = random.nextInt(5) + 1;
                reportService.create(new ReportResponseDTO(
                        3,
                        1,
                        mark,
                        student.getId(),
                        course.getId()

                ));
            }
        }
        for (Student student : students) {
            for (Course course : courses) {
                int mark = random.nextInt(5) + 1;
                reportService.create(new ReportResponseDTO(
                        3,
                        2,
                        mark,
                        student.getId(),
                        course.getId()
                ));
            }
        }
    }

    private void testDataAttendance() {
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int randLecture = random.nextInt(12) + 1;
            int randYear = random.nextInt(2) + 2018;
            int randMonth = randYear == 2018 ? random.nextInt(4) + 3
                    : random.nextInt(6) + 1;
            int randDay = random.nextInt(22) + 1;

            Long classroom_id = i % 2 == 0 ? 1L : 2L;
            List<AttendanceDTO> attendanceDTOS = attendanceService.makeAttendanceFormToClassroom(classroom_id);
            List<AttendanceResponseDTO> attendanceResponseDTOS = new ArrayList<>();
            for (AttendanceDTO attendanceDTO : attendanceDTOS) {
                boolean randMiss = random.nextBoolean();
                AttendanceResponseDTO attendance = new AttendanceResponseDTO(
                        attendanceDTO.getStudent().getId(),
                        randMiss,
                        randLecture,
                        LocalDate.of(randYear, randMonth, randDay)
                );
                attendanceResponseDTOS.add(attendance);

            }
            attendanceService.create(attendanceResponseDTOS);
        }
    }

    private void testDataRoom() {
        for (char c = 'A'; c < 'E'; c++) {
            for (int i = 1; i < 3; i++) {
                for (int j = 1; j < 10; j++) {
                    roomService.create(new RoomResponseDTO(i + "0" + j + "" + c));
                }
            }
        }
    }

    private void testDataTimeTable() {
        List<Course> courses = courseService.findAll();
        TimeTableEntityResponseDTO[][] timeTableEntityResponseDTOS =
                new TimeTableEntityResponseDTO[8][5];
        List<Room> rooms = roomService.findAll();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                timeTableEntityResponseDTOS[i][j] = new TimeTableEntityResponseDTO();
                Course course = courses.get(random.nextInt(courses.size()));
                timeTableEntityResponseDTOS[i][j].setClassroomId(1L);
                timeTableEntityResponseDTOS[i][j].setCourseId(course.getId());
                timeTableEntityResponseDTOS[i][j].setRoomId(
                        rooms.get(random.nextInt(rooms.size())).getId()
                );
                timeTableEntityResponseDTOS[i][j].setDay(j);
                timeTableEntityResponseDTOS[i][j].setLessonNumber(i);
                timeTableService.create(timeTableEntityResponseDTOS[i][j]);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                timeTableEntityResponseDTOS[i][j] = new TimeTableEntityResponseDTO();
                Course course = courses.get(random.nextInt(courses.size()));
                timeTableEntityResponseDTOS[i][j].setClassroomId(2L);
                timeTableEntityResponseDTOS[i][j].setCourseId(course.getId());
                timeTableEntityResponseDTOS[i][j].setRoomId(
                        rooms.get(random.nextInt(rooms.size())).getId()
                );
                timeTableEntityResponseDTOS[i][j].setDay(j);
                timeTableEntityResponseDTOS[i][j].setLessonNumber(i);
                timeTableService.create(timeTableEntityResponseDTOS[i][j]);
            }
        }
    }
}
