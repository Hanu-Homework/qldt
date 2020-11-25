import { Component, OnInit } from "@angular/core";
import { Student } from "src/app/model/student";
import { StudentResponseDTO } from "src/app/dto/response/studentResponseDTO";
import { UserService } from "src/app/service/user.service";
import { StudentService } from "src/app/service/student.service";
import { Observable } from "rxjs";
import { Classroom } from "src/app/model/classroom";
import { Router, ActivatedRoute } from "@angular/router";
import { ClassroomService } from "src/app/service/classroom.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import {
  isStudent,
  isIdMatches,
  isTeacher,
  isAdmin,
} from "src/app/shared/roles";

@Component({
  selector: "app-student-update",
  templateUrl: "./student-update.component.html",
  styleUrls: ["./student-update.component.scss"],
})
export class StudentUpdateComponent implements OnInit {
  currentUser: any = {};
  id: number;
  student = new Student();
  response = new StudentResponseDTO();
  isDataAvailable: boolean = false;
  classrooms: Observable<Classroom[]>;
  selectedOption: any = {};
  selectedOptionGender: any = {};
  genders: string[] = ["MALE", "FEMALE", "OTHER"];

  constructor(
    private userService: UserService,
    private studentService: StudentService,
    private router: Router,
    private route: ActivatedRoute,
    private classroomService: ClassroomService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params["id"];
    this.userService
      .getMyInfo()
      .toPromise()
      .then((data) => {
        this.currentUser = data;
        this.studentService.findById(this.id).subscribe((data) => {
          this.student = data;
          this.classroomService
            .findById(data.classroom.id)
            .subscribe((data) => {
              this.classroomService.findAll().subscribe((data) => {
                this.classrooms = data;
                this.isDataAvailable = true;
              });
            });
        });
      });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  isDataChanged() {
    if (
      !this.response.address ||
      !this.response.fatherName ||
      !this.response.motherName ||
      !this.response.fatherPhone ||
      !this.response.motherPhone ||
      !this.response.dateOfBirth ||
      !this.response.educationId ||
      !this.response.startYear ||
      !this.response.healthCareId ||
      !this.response.classroomId ||
      !this.response.gender
    )
      return true;
    return false;
  }

  submit() {
    if (this.isDataChanged) {
      if (!this.selectedOptionGender)
        if (!this.response.address)
          this.response.address = this.student.address;
      if (!this.response.fatherName)
        this.response.fatherName = this.student.fatherName;
      if (!this.response.motherName)
        this.response.motherName = this.student.motherName;
      if (!this.response.fatherPhone)
        this.response.fatherPhone = this.student.fatherPhone;
      if (!this.response.motherPhone)
        this.response.motherPhone = this.student.motherPhone;
      if (!this.response.dateOfBirth)
        this.response.dateOfBirth = this.student.dateOfBirth;
      if (!this.response.educationId)
        this.response.educationId = this.student.educationId;
      if (!this.response.startYear)
        this.response.startYear = this.student.startYear;
      if (!this.response.healthCareId)
        this.response.healthCareId = this.student.healthCareId;
      if (!this.selectedOption)
        this.response.classroomId = this.student.classroom.id;
      else this.response.classroomId = Number(this.selectedOption.id);
      if (!this.selectedOptionGender)
        this.response.gender = this.student.gender;
      else this.response.gender = this.selectedOptionGender;
      this.studentService.update(this.id, this.response).subscribe(
        () => {
          this.openSnackBar("Student updated.", "Ok");
          this.refresh();
        },
        (error) => {
          this.openSnackBar("Failed.", "Ok");
        }
      );
    }
  }

  goBack() {
    if (this.currentUser.authorities[0].authority + "" === "ROLE_ADMIN") {
      this.router.navigate(["/user/all"]);
    } else {
      this.router.navigate(["/home"]);
    }
  }

  userUpdate() {
    this.userService.getById(this.student.student.id).subscribe(
      (data) => {
        this.student = data;
        this.response = new StudentResponseDTO();
        this.selectedOption = {};
      },
      (error) => {
        this.openSnackBar("Failed.", "Ok");
      }
    );
  }

  refresh() {
    this.userService.getById(this.student.student.id).subscribe((data) => {
      this.student = data;
    });
    this.response = new StudentResponseDTO();
    this.selectedOption = {};
  }

  userRole() {
    if (
      isAdmin(this.currentUser, this.router) ||
      isTeacher(this.currentUser, this.router) ||
      isIdMatches(this.currentUser, this.student)
    ) {
      return true;
    } else {
      this.router.navigate(["403"]);
      return false;
    }
  }
}
