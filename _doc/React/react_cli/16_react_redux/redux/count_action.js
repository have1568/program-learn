import {DECREMENT, INCREMENT} from "./constant"


//为 Count组件生成action组件
export const countAdd = (data) => {
    return {type: INCREMENT, data: data * 1}
}
export const countMinus = (data) => {
    return {type: DECREMENT, data: data * 1}
}

//异步 action 返回的是 函数
export const countAsyncAdd = (data) => {
    console.log("countAsyncAdd")
    return (dispatch) => {
        setTimeout(() => {
            dispatch(countAdd(data))
        }, 500)
    }
}