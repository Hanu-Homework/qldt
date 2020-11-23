import { ArchiveReport } from "./archiveReport";

export class Archive {
  id: number;
  username: string;
  student_name: string;
  date_of_birth: string;
  reports: ArchiveReport[];
}
