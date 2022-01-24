import {applyMiddleware, combineReducers, createStore} from "redux";
import countReducer from "./reducers/count";
import personReducer from "./reducers/person";
import thunk from "redux-thunk";
import {composeWithDevTools} from "redux-devtools-extension"

//合并 reducer
const reducers = combineReducers({
    //基本数据类型reducer
    count: countReducer,
    //数组类型reducer 也可以是其他任何类型的，取决与要向 store 里保存什么数据
    persons: personReducer
})


//需要中间件的支持
export default createStore(reducers, composeWithDevTools(applyMiddleware(thunk)))