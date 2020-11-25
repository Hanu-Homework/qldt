import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { Report } from "src/app/model/report";
import { UserService } from "src/app/service/user.service";
import { StudentService } from "src/app/service/student.service";
import { Router, ActivatedRoute } from "@angular/router";
import { ReportService } from "src/app/service/report.service";
import { Student } from "src/app/model/student";
import { TeacherService } from "src/app/service/teacher.service";
import { Teacher } from "src/app/model/teacher";
import { CourseService } from "src/app/service/course.service";
import { Course } from "src/app/model/course";
import { isTeacher, isIdMatches } from "src/app/shared/roles";

@Component({
  selector: "app-semester-view",
  templateUrl: "./semester-view.component.html",
  styleUrls: ["./semester-view.component.scss"],
})
export class SemesterViewComponent implements OnInit {
  studentId: number;
  currentUser: any = {};
  year: number;
  semester: any = {};
  isDataAvailable: boolean = false;
  selected: boolean = false;
  reports: Observable<Report[]>;
  student = new Student();
  teacher = new Teacher();
  courses: Course[];

  constructor(
    private userService: UserService,
    private studentService: StudentService,
    private teacherService: TeacherService,
    private router: Router,
    private route: ActivatedRoute,
    private reportService: ReportService,
    private courseService: CourseService
  ) {}

  ngOnInit() {
    this.studentId = this.route.snapshot.params["id"];
    this.userService
      .getMyInfo()
      .toPromise()
      .then((data) => {
        this.currentUser = data;
        this.studentService
          .findByUserId(this.currentUser.id)
          .subscribe((data) => {
            this.student = data;
            this.isDataAvailable = true;
          });
      });
  }

  onSubmit() {
    this.reportService
      .getSemesterResultByStudent(
        this.studentId,
        this.year,
        Number(this.semester)
      )
      .subscribe((data) => {
        this.reports = data;
        this.selected = true;
      });
  }

  goBack() {
    this.studentService.findById(this.studentId).subscribe((data) => {
      this.router.navigate(["student/classroom/", data.classroom.id]);
    });
  }

  userRole() {
    if (
      isTeacher(this.currentUser, this.router) ||
      isIdMatches(this.currentUser, this.student)
    ) {
      return true;
    } else {
      this.router.navigate(["403"]);
      return false;
    }
  }

  update(reportId: number) {
    this.router.navigate(["report/update", reportId]);
  }

  delete(reportId: number) {
    this.reportService.delete(reportId).subscribe(() => {
      this.refresh();
    });
  }

  refresh(): void {
    window.location.reload();
  }
}
