export interface User {
  id?: number;
  name: string;
  lastName: string;
  email: string;
  role: Role;
  address: string;
}

export enum Role {
  ADMIN = 'ADMIN',
  STUDENT = 'STUDENT',
  SUPERVISOR = 'SUPERVISOR',
  TUTOR = 'TUTOR'
}
