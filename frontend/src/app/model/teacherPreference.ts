import { Teacher } from "./teacher";

export class TeacherPreference {
  id: number;
  teacher: Teacher;
  test_teight: number;
  topic_test_weight: number;
  repetition_weight: number;
  homework_weight: number;
}
