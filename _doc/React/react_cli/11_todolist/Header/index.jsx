import React, {Component} from 'react';
import {nanoid} from "nanoid";

class Header extends Component {

    state = {todoNme: ""}
    setTodoName = (event) => {
        this.setState({todoNme: event.target.value})
    }

    handAdd = () => {
        const name = this.state.todoNme;
        let todo = {id: nanoid(), name: name, check: false};
        this.props.appAdd(todo);
    }

    render() {
        return (
            <div className="card-header">
                <div className="d-flex" role="search">
                    <input onChange={this.setTodoName} className="form-control me-2" placeholder="输入任务名称"
                           aria-label="Search"/>
                    <button className="btn btn-outline-success" onClick={this.handAdd} style={{width: 100}}>添加</button>
                </div>
            </div>
        );
    }
}

export default Header;