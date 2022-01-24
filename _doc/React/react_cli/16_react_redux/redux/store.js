import {createStore,applyMiddleware} from "redux";
import countReducer from "./count_reducer"
import thunk from "redux-thunk"
//需要中间件的支持
export default createStore(countReducer,applyMiddleware(thunk))