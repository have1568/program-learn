import React, {Component} from 'react';
import {addPerson} from "../../redux/actions/person";
import {connect} from "react-redux";


class Person extends Component {

    render() {
        console.log("@",this.props)
        return (
            <div>
                <h1>Person@{this.props.count}</h1>
                <button onClick={(event)=>(this.props.addPerson({id:1}))}>addd</button>
            </div>
        );
    }
}

export default connect(
    state => ({persons: state.persons,count: state.count}),
    {
        addPerson: addPerson
    })(Person);