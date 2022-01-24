import React, {Component} from 'react';
import axios from "axios";

class About extends Component {

    state={"res":"Loading"}

    componentDidMount() {
        axios.get("/robots.txt")
            .then(response => {
                console.log(response)
                this.setState({"res":response.data})
            })
    }

    render() {
        return (
            <div>
                <h1>{this.state.res}</h1>
            </div>
        );
    }
}

export default About;