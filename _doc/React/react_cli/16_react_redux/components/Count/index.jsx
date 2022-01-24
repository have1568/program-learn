import React, {Component} from 'react'
import store from "../../redux/store";
import {countAdd,countMinus,countAsyncAdd} from "../../redux/count_action"

class Count extends Component {

    componentDidMount() {
        store.subscribe(()=>{
            this.setState({})
        })
    }

    //加法
    increment = () => {
        const {value} = this.selectNumber
        store.dispatch(countAdd(value))
    }
    //减法
    decrement = () => {
        const {value} = this.selectNumber
        store.dispatch(countMinus(value))
    }
    //奇数再加
    incrementIfOdd = () => {
        const {value} = this.selectNumber
        const count = store.getState()
        if (count % 2 !== 0) {
            store.dispatch(countAdd(value))
        }
    }
    //异步加
    incrementAsync = () => {
        const {value} = this.selectNumber
        store.dispatch(countAsyncAdd(value))
    }

    render() {
        return (
            <div>
                <h1>当前求和为：{store.getState()}</h1>
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

export default Count