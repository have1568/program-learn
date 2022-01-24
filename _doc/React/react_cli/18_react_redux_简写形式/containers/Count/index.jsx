import {countAdd, countAsyncAdd, countMinus} from "../../redux/count_action"
import {connect} from "react-redux"
import React, {Component} from "react";

class Count extends Component {

    //加法
    increment = () => {
        const {value} = this.selectNumber
        this.props.countAdd(value)

    }
    //减法
    decrement = () => {
        const {value} = this.selectNumber
        this.props.countMinus(value)

    }
    //奇数再加
    incrementIfOdd = () => {
        const {value} = this.selectNumber
        const count = this.props.count
        if (count % 2 !== 0) {
            this.props.countAdd(value)
        }
    }
    //异步加
    incrementAsync = () => {
        const {value} = this.selectNumber
        this.props.countAsyncAdd(value)
    }

    render() {
        console.log(this.props)
        return (
            <div>
                <h1>当前求和为：{this.props.count}</h1>
                <select ref={c => this.selectNumber = c}>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </select>&nbsp;
                <button onClick={this.increment}>+</button>
                &nbsp;
                <button onClick={this.decrement}>-</button>
                &nbsp;
                <button onClick={this.incrementIfOdd}>当前求和为奇数再加</button>
                &nbsp;
                <button onClick={this.incrementAsync}>异步加</button>
                &nbsp;
            </div>
        )
    }
}

//简写形式
export default connect(
    (state) => ({count: state}),
    {
        countAdd: countAdd,
        countMinus: countMinus,
        countAsyncAdd: countAsyncAdd
    })(Count);