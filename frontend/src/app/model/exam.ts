import { Course } from "./course";
import { Student } from "./student";

export class Exam {
  id: number;
  mark: number;
  written_at: string;
  exam_type: string;
  course: Course;
  student: Student;
}
