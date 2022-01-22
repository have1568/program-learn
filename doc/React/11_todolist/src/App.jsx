import 'bootstrap/dist/css/bootstrap.css';
import React from "react";
import Header from "./Header";
import List from "./List";
import Footer from "./Footer";

export default class App extends React.Component {

    state = {
        todos: [
            {id: 1, name: "吃饭", check: true},
            {id: 2, name: "睡觉", check: false},
            {id: 3, name: "学习", check: false},
            {id: 4, name: "编程", check: false},
        ]
    }
    appAdd = (todo) => {
        let oldTodos = this.state.todos;
        this.setState({todos: [todo, ...oldTodos]})
    }
    appUpdateTodo = (id, checked) => {
        let oldTodos = this.state.todos;
        let newTodos = oldTodos.map(todo => todo.id === id ? {id: id, name: todo.name, check: checked} : todo)
        this.setState({todos: newTodos})
    }

    appDeleteTodo = (id) => {
        let oldTodos = this.state.todos;
        let newTodos = oldTodos.filter(todo => id !== todo.id);
        this.setState({todos: newTodos})
    }

    appCheckAll = (isCheckAll) => {
        let oldTodos = this.state.todos;
        //filter方式
        // let newTodos = oldTodos.filter(todo => {
        //     todo.check = isCheckAll;
        //     return true;
        // });
        //map方式
        let newTodos = oldTodos.map(todo => {
            return {...todo, check: isCheckAll}
        });
        this.setState({todos: newTodos})
    }

    render() {
        const {todos} = this.state
        return (
            <div className="container">
                <div className="card flex justify-content-lg-between mt-5">
                    <Header appAdd={this.appAdd}/>
                    <List todos={todos} appUpdateTodo={this.appUpdateTodo} appDeleteTodo={this.appDeleteTodo}/>
                    <Footer todos={todos} appCheckAll={this.appCheckAll}/>
                </div>
            </div>
        )
    }
}
