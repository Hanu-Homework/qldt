import { Student } from "./student";

export class Attendance {
  id: number;
  lecture: number;
  date_of_miss: string;
  student: Student;
  verified: boolean;
}
