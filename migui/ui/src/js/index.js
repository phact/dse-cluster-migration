import 'babel-core/polyfill';
import 'font-awesome/css/font-awesome';

import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import logger from 'redux-logger';
import {createStore, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';
import rootReducer from './reducers';
import App from './components/App';

const store = createStore(rootReducer, applyMiddleware(thunk, logger));

const app = document.getElementById('app');
ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>,
    app
);
