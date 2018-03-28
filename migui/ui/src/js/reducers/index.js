/**
 * This is where each of the individual reducers are combined into the rootReducer that gets handed to the redux store..
 */
import {combineReducers} from 'redux';
import {appReducer} from "./appReducer";

const rootReducer = combineReducers({
    app: appReducer
});

export default rootReducer;
