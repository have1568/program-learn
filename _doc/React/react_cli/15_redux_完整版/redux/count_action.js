import {DECREMENT, INCREMENT} from "./constant"


//为 Count组件生成action组件
export const countAdd = (data) => {
    return {type: INCREMENT, data: data * 1}
}
export const countMinus = (data) => {
    return {type: DECREMENT, data: data * 1}
}