import { Teacher } from "./../../../model/teacher";
import { Component, OnInit, OnDestroy } from "@angular/core";
import { UserService } from "src/app/service/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { isTeacher, isAdmin } from "src/app/shared/roles";
import { TeacherService } from "src/app/service/teacher.service";

@Component({
  selector: "app-student-details",
  templateUrl: "./teacher-details.component.html",
  styleUrls: ["./teacher-details.component.scss"],
})
export class TeacherDetailsComponent implements OnInit {
  currentUser: any = {};
  id: number = 0;
  teacher = new Teacher();
  isDataAvailable: boolean = false;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private teacherService: TeacherService,
    private router: Router
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params["id"];
    this.userService
      .getMyInfo()
      .toPromise()
      .then((data) => {
        this.currentUser = data;
        this.teacherService.findById(this.id).subscribe((data) => {
          this.teacher = data;
          this.isDataAvailable = true;
        });
      });
  }

  userRole(): boolean {
    if (
      isAdmin(this.currentUser, this.router) ||
      isTeacher(this.currentUser, this.router)
    ) {
      return true;
    } else {
      this.router.navigate(["403"]);
    }
  }
}
