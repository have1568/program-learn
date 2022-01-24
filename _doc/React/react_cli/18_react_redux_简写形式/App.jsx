import React, { Component } from 'react'
//引入容器组件
import Count from './containers/Count'
import store from "./redux/store";

export default class App extends Component {
  render() {
    return (
        <div>
          <Count store={store} />
        </div>
    )
  }
}
