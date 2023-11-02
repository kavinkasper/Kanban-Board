import { BOARDS } from "./Boards";
export type KANBAN={
    email:string;
    username:string;
    password:string;
    confirmpassword:string;
    mobilenumber:number;
    boards:BOARDS[]
}