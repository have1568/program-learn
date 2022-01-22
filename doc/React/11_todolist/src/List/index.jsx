import React, {Component} from 'react';
import Item from "../Item";

class List extends Component {
    render() {
        const {todos} = this.props
        return (
            <div className="card-body">
                <div className="list-group">
                    {todos.map(todo => {
                        return <Item key={todo.id} {...todo}
                                     listHandleCheck={(id, checked) => {this.props.appUpdateTodo(id, checked)}}
                                     listHandleDelete={(id) => {return () => {this.props.appDeleteTodo(id)}}}/>
                    })}
                </div>
            </div>
        );
    }
}

export default List;