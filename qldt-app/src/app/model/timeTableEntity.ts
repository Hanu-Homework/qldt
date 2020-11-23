import { Course } from "./course";
import { Classroom } from "./classroom";
import { Room } from "./room";

export class TimeTableEntity {
  id: number;
  day: number;
  lesson_number: number;
  course: Course;
  room: Room;
  classroom: Classroom;
}
