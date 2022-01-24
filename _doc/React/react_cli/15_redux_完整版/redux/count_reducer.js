
import {INCREMENT,DECREMENT} from "./constant"

export default (preState = 0, action) => {
    console.log(preState,action)
    const {type, data} = action;
    switch (type) {
        case INCREMENT:
            return preState + data;
        case DECREMENT:
            return preState - data;
        default:
            return preState;
    }
}