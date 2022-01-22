import React, {Component} from 'react';

class Item extends Component {

    state = {isShowDeleteBtn: false}

    //showDelete应该是高阶函数
    showDelete = (isShow) => {
        return () => {
            this.setState({isShowDeleteBtn: isShow})
        }
    }

    render() {
        const {id, name, check} = this.props
        return (
            <label className="list-group-item list-group-item-action d-grid gap-2 d-flex justify-content-lg-between"
                   onMouseEnter={this.showDelete(true)} onMouseLeave={this.showDelete(false)}>
                <div className="text-nowrap bd-highlight" style={{lineHeight: 36 + 'px'}}>
                    <input className="form-check-input" style={{marginTop: 10, marginRight: 10}} type="checkbox"
                           value={name} checked={check} onChange={event => {this.props.listHandleCheck(id, event.target.checked)}}/>
                    {name}
                </div>
                <button type="button" onClick={this.props.listHandleDelete(id)}
                        style={{width:60, display: this.state.isShowDeleteBtn ? "block" : "none"}}
                        className="btn btn-outline-danger btn-sm">删除
                </button>
            </label>
        );
    }
}

export default Item;