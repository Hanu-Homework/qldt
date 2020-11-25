import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { Remark } from "src/app/model/remark";
import { RemarkService } from "src/app/service/remark.service";
import { UserService } from "src/app/service/user.service";
import { Router, ActivatedRoute } from "@angular/router";
import { MatSnackBar } from "@angular/material/snack-bar";
import {
  isStudent,
  isIdMatches,
  isTeacher,
  isAdmin,
} from "src/app/shared/roles";
import { StudentService } from "src/app/service/student.service";
import { Student } from "src/app/model/student";

@Component({
  selector: "app-remark-list",
  templateUrl: "./remark-list.component.html",
  styleUrls: ["./remark-list.component.scss"],
})
export class RemarkListComponent implements OnInit {
  remarks: Observable<Remark[]>;
  isDataAvailable: boolean = false;
  studentId: number;
  student: Student;
  currentUser: any = {};

  constructor(
    private userService: UserService,
    private router: Router,
    private studentService: StudentService,
    private remarkService: RemarkService,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.studentId = this.route.snapshot.params["id"];
    this.userService
      .getMyInfo()
      .toPromise()
      .then((data) => {
        this.currentUser = data;
        this.remarkService.findAll(this.studentId).subscribe((data) => {
          this.remarks = data;
          this.studentService.findByUserId(this.studentId).subscribe((data) => {
            this.student = data;
            console.log(data);
            console.log(this.student);
            console.log("bye");
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
    console.log(this.currentUser);
    console.log(this.studentId);
    if (
      isAdmin(this.currentUser, this.router) ||
      isTeacher(this.currentUser, this.router) ||
      this.currentUser.id == this.studentId ||
      true
    ) {
      return true;
    } else {
      this.router.navigate(["403"]);
    }
  }

  update(remarkId: number) {
    this.remarkService
      .findById(remarkId)
      .subscribe((data) => this.router.navigate(["/remark/update", data.id]));
  }

  delete(remarkId: number) {
    this.remarkService.delete(remarkId).subscribe(
      () => {
        this.refresh();
        this.openSnackBar("Remark deleted.", "Ok");
      },
      (error) => {
        this.openSnackBar("Failed.", "Ok");
      }
    );
  }

  refresh(): void {
    window.location.reload();
  }

  create(): void {
    this.router.navigate(["/remark/create"]);
  }
}
