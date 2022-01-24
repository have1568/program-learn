import {ADD_PERSON} from "../constant";

export const addPerson = (data)=>{
    console.log("@Add",data)
    return {type:ADD_PERSON,data:data}
}