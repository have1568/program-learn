import {ADD_PERSON} from "../constant";
const PersonReducer = (preState = [], action) => {
    const {type, data} = action;
    switch (type) {
        case ADD_PERSON :
            //向人的数组里添加一个新的人
            //这里可以处理业务逻辑，将处理后的数据返回
            return [data, ...preState]
        default :
            return preState
    }
}
export default PersonReducer