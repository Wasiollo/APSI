import {User} from "../../../model/user.model";

export class Test {

    id: number;
    name: string;
    description: string;
    updateDate: Date;
    status: string;
    owner: User;
}
