import {UserRole} from './user.role.model';
export class User {

  id: number;
  username: string;
  email: string;
  password: string;
  userRoles: UserRole[]
}
