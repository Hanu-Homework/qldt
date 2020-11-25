import { Student } from "../model/student";
export class ExamDTO {
  student: Student;
  mark: number;
  writtenAt: string;
  examType: string;
}
