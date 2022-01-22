import React, {Component} from 'react';

class Footer extends Component {
    render() {
        const {todos} = this.props
        const doneNum = todos.reduce((pre, todo) => {return todo.check === true ? (pre + 1) : pre;}, 0)
        return (
            <div className="card-footer">
                <div className="row ms-3">
                    <div className="form-check">
                        <input onChange={ event => {return this.props.appCheckAll(event.target.checked)} } checked={doneNum===todos.length} className="form-check-input" type="checkbox" value=""
                               id="flexCheckChecked"/>
                        <label className="form-check-label" htmlFor="flexCheckChecked">
                            已完成{doneNum}/全部{todos.length}
                        </label>
                    </div>
                </div>
            </div>
        );
    }
}

export default Footer;