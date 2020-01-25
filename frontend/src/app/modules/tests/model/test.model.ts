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
    documents: TestDocument[];
}

export class Specs {
    id: number;
    userAction: string;
    systemReaction: string;
}

export class TestDocument {
    id: number;
    filename: string;
    data: any;
}
