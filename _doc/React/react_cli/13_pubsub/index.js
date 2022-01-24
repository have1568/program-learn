import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import axios from "axios";
import PubSub  from "pubsub-js";

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    PubSub.publish('RequestStart', 'hello world!');
    PubSub.publishSync('RequestStart', 'hello world!');
    return config;
}, function (error) {
    // Do something with request error
    PubSub.publish('RequestError', 'hello world!');
    PubSub.publishSync('RequestError', 'hello world!');
    return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
    // Do something with response data
    PubSub.publish('ResponseStart', 'hello world!');
    PubSub.publishSync('ResponseStart', 'hello world!');
    return response;
}, function (error) {
    // Do something with response error
    PubSub.publish('ResponseError', 'hello world!');
    PubSub.publishSync('ResponseError', 'hello world!');
    return Promise.reject(error);
});


ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);