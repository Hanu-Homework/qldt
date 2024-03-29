import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Attendance} from 'src/app/model/attendance';
import {UserService} from 'src/app/service/user.service';
import {Router, ActivatedRoute} from '@angular/router';
import {AttendanceService} from 'src/app/service/attendace.service';
import {StudentService} from 'src/app/service/student.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {isStudent, isIdMatches, isTeacher, isAdmin} from 'src/app/shared/roles';
import {Student} from 'src/app/model/student';

@Component({
  selector: 'app-view-attendance',
  templateUrl: './view-attendance.component.html',
  styleUrls: ['./view-attendance.component.scss']
})
export class ViewAttendanceComponent implements OnInit {

  studentID: number;
  student: Student;
  searchText;
  isDataAvailable = false;
  currentUser: any = {};
  attendances: Observable<Attendance[]>;

  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute,
              private attendanceService: AttendanceService, private studentService: StudentService, private _snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.studentID = this.route.snapshot.params.id;
    this.userService.getMyInfo().toPromise().then(data => {
      this.currentUser = data;
      this.attendanceService.getAllByStudent(this.studentID).subscribe(data => {
        this.attendances = data;
        this.studentService.findById(this.studentID).subscribe(data => {
          this.student = data;
          this.isDataAvailable = true;
        });
      });
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  userRole() {
    if (isAdmin(this.currentUser, this.router) || isTeacher(this.currentUser, this.router) ||
      this.currentUser.id === this.student.student.id) {
      return true;
    } else {
      this.router.navigate(['403']);
    }
  }

  delete(attendanceID: number) {
    this.attendanceService.delete(attendanceID).subscribe(() => {
      this.openSnackBar('Attendance deleted.', 'Ok');
    }, error => {
      this.openSnackBar('Failed.', 'Ok');
    });
  }

  goBack() {
    this.studentService.findById(this.studentID).subscribe(data => {
      this.router.navigate(['student/classroom', data.classroom.id]);
    });
  }
}
