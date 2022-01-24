import CountUI from "../../components/Count"
import {countAdd, countAsyncAdd, countMinus} from "../../redux/count_action"
import {connect} from "react-redux"


//将state作为Props传入到UI组件
const mapStateToProps = (state) => {
    return {count: state}
}

//将action传入 到UI组件
const mapDispatchToProps = (dispatch) => {
    return {
        countAdd: number => dispatch(countAdd(number)),
        countMinus: number => dispatch(countMinus(number)),
        countAsyncAdd: (number) => dispatch(countAsyncAdd(number)),
    }
}

//创建并暴漏一个Count的容器组件
export default connect(mapStateToProps, mapDispatchToProps)(CountUI);