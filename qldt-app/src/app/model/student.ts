import { User } from "./user";
import { Classroom } from "./classroom";

export class Student {
  id: number;
  student: User;
  date_of_birth: string;
  start_year: number;
  address: string;
  gender: string;
  education_id: string;
  healthCare_id: string;
  father_name: string;
  mother_name: string;
  father_phone: string;
  mother_phone: string;
  classroom: Classroom;
}
