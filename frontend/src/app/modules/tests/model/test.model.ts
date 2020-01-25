import {User} from "../../../model/user.model";

export class Test {

    id: number;
    name: string;
    description: string;
    updateDate: Date;
    status: string;
    owner: User;
    accepted: boolean;
    specifications: Specs[];
}

export class Specs {
    userAction: string;
    systemReaction: string;
}
