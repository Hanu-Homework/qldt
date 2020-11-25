import { User } from "./user";
import { Classroom } from "./classroom";

export class Student {
  id: number;
  student: User;
  dateOfBirth: string;
  startYear: number;
  address: string;
  gender: string;
  educationId: string;
  healthCareId: string;
  fatherName: string;
  motherName: string;
  fatherPhone: string;
  motherPhone: string;
  classroom: Classroom;
}
