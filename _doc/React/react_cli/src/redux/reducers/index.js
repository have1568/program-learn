import {combineReducers} from "redux";
import CountReducer from "./count";
import PersonReducer from "./person";

//这里的count: CountReducer, persons: PersonReducer key是组件里state取值的key
export default combineReducers({count: CountReducer, persons: PersonReducer})



