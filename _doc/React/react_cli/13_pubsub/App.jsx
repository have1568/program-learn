import React, {Component} from 'react';
import PubSub from "pubsub-js";
import About from "./About";

class App extends Component {
    componentDidMount() {
        PubSub.subscribe('RequestStart', (msg, data)=>{
            console.log(msg,data)
        });
    }

    componentWillUnmount() {
        PubSub.unsubscribe("RequestStart");
    }

    render() {
        return (
            <div>
              <h1>APP</h1>
                <About/>
            </div>
        );
    }
}

export default App;